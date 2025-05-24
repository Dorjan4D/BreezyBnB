package com.breezybnb.entity;

import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.*;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.breezybnb.filter.LazyFieldsFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Customer.class)
public class Customer extends User {



    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updated DESC")
    private List<Review> reviews = new LinkedList<>();

    public void addReview(Review review) {
        review.setCustomer(this);
        this.reviews.add(review);
    }

    public void addMultipleReviews(List<Review> reviews) { for (Review review:reviews) addReview(review); }

    public List<DtoReview> retrieveReviewsDTO() {
        return MyConverter.convertToDTOList(getReviews());
    }

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateFrom DESC")
    private List<Reservation> reservations = new LinkedList<>();

    public List<DtoReservation> retrieveReservationsDTO() {
        return MyConverter.convertToDTOList(getReservations());
    }

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateFrom DESC")
    private List<History> histories = new LinkedList<>();

    public List<DtoHistory> retrieveHistoriesDTO() {
        return MyConverter.convertToDTOList(getHistories());
    }


    public DtoCustomer toCustomerDTO() {
        if (this.getId()==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.getId()));
        DtoCustomer customer = new DtoCustomer();
        customer.setId(getId());
        customer.setUsername(getUsername());
        customer.setName(getName());
        customer.setSurname(getSurname());
        customer.setEmail(getEmail());
        customer.setRegistered(getRegistered());
        customer.setGender(getGender());
        customer.setDateOfBirth(getDateOfBirth());
        return customer;
    }

    DtoCustomer toCustomerDTO(Set<EntityWrapper> entities) {
        if (this.getId()==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.getId());
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoCustomer customer = new DtoCustomer();
        customer.setId(getId());
        customer.setUsername(getUsername());
        customer.setName(getName());
        customer.setSurname(getSurname());
        customer.setEmail(getEmail());
        customer.setRegistered(getRegistered());
        customer.setGender(getGender());
        customer.setDateOfBirth(getDateOfBirth());
        return customer;
    }



    public static List<DtoCustomer> convertToCustomerDTOList(List<Customer> entityList) {
        if (entityList == null) {
            return Collections.emptyList();  // Return an empty list if the argument is null
        }

        return entityList.stream()
                .map(Customer::toCustomerDTO)
                .collect(Collectors.toList());
    }
}
