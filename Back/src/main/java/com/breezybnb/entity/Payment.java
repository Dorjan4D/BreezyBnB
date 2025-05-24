package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoPayment;
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
        scope = Payment.class)
public class Payment implements ConvertibleToDTO<DtoPayment> {


    @PrePersist
    private void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = LocalDateTime.now();
        }
    }


    public Payment(Double amount, LocalDateTime timestamp, LocalDateTime refundedTimestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.refundedTimestamp = refundedTimestamp;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false)
    @NotNull
    private Double amount;

    @Column(nullable = false, updatable = false)
    private LocalDateTime timestamp;

    private LocalDateTime refundedTimestamp;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "history_id")
    private History history;




    @Override
    public DtoPayment toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoPayment payment=new DtoPayment();
        payment.setId(this.id);
        payment.setAmount(this.amount);
        payment.setTimestamp(this.timestamp);
        payment.setRefundedTimestamp(this.refundedTimestamp);
        return payment;
    }

    DtoPayment toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoPayment payment=new DtoPayment();
        payment.setId(this.id);
        payment.setAmount(this.amount);
        payment.setTimestamp(this.timestamp);
        payment.setRefundedTimestamp(this.refundedTimestamp);
        return payment;
    }
}
