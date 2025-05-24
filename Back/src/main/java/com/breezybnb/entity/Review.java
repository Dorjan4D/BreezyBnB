package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoReview;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Review.class)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"accommodation_id", "customer_id"})
})
public class Review implements ConvertibleToDTO<DtoReview> {


    @PrePersist
    @PreUpdate
    private void onCreate() {
        this.updated = LocalDateTime.now();
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotNull
    private Integer rating;

    private String comment;

    @Column(nullable = false)
    private LocalDateTime updated;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "accommodation_id", nullable = false, updatable = false)
    private Accommodation accommodation;

    public void assignAccommodation(Accommodation accommodation) {
        accommodation.getReviews().add(this);
        this.accommodation = accommodation;
    }

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    public void assignCustomer(Customer customer) {
        customer.getReviews().add(this);
        this.customer = customer;
    }





    @Override
    public DtoReview toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoReview review=new DtoReview();
        review.setId(this.id);
        review.setRating(this.rating);
        review.setComment(this.comment);
        review.setUpdated(this.updated);
        if (this.customer!=null) review.setCustomer(this.customer.toCustomerDTO(entities));
        return review;
    }

    DtoReview toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoReview review=new DtoReview();
        review.setId(this.id);
        review.setRating(this.rating);
        review.setComment(this.comment);
        review.setUpdated(this.updated);
        if (this.customer!=null) review.setCustomer(this.customer.toCustomerDTO(localEntities));
        return review;
    }
}
