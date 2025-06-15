package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoPayment;
import com.breezybnb.dto.DtoReservation;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.breezybnb.filter.LazyFieldsFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Reservation.class)
public class Reservation implements ConvertibleToDTO<DtoReservation> {

    @Id
    @GeneratedValue
    private Long id;

    private Integer numOfGuests;

    private Double price;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private LocalDate payDate;

    private LocalDate cancelDate;

    private LocalDateTime paid;

    private LocalDateTime checkInTimestamp;

    private LocalDateTime confirmed;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "accommodation_id", nullable = false, updatable = false)
    private Accommodation accommodation;

    public void assignAccommodation(Accommodation accommodation) {
        accommodation.getReservations().add(this);
        this.accommodation = accommodation;
    }

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    public void assignCustomer(Customer customer) {
        customer.getReservations().add(this);
        this.customer = customer;
    }

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "reservation", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @OrderBy("timestamp DESC")
    private List<Payment> payments = new LinkedList<>();

    public void addPayment(Payment payment) {
        payment.setReservation(this);
        this.payments.add(payment);
    }

    public void addMultiplePayments(List<Payment> payments) { for (Payment payment:payments) addPayment(payment); }

    public List<DtoPayment> retrievePaymentsDTO() {
        return MyConverter.convertToDTOList(getPayments());
    }

    public LinkedList<Payment> disassociateAllPayments() {
        LinkedList<Payment> disassociatedPayments = new LinkedList<>();

        for (Payment payment : new ArrayList<>(payments)) {
            payment.setReservation(null); // Set parent to null
            disassociatedPayments.add(payment); // Add to the new LinkedList
        }

        payments.clear(); // Clear the original list
        return disassociatedPayments; // Return the new LinkedList with disassociated children
    }




    @Override
    public DtoReservation toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoReservation reservation=new DtoReservation();
        reservation.setId(this.id);
        reservation.setNumOfGuests(this.numOfGuests);
        reservation.setPrice(this.price);
        reservation.setDateFrom(this.dateFrom);
        reservation.setDateTo(this.dateTo);
        reservation.setPayDate(this.payDate);
        reservation.setCancelDate(this.cancelDate);
        reservation.setPaid(this.paid);
        reservation.setCheckInTimestamp(this.checkInTimestamp);
        reservation.setConfirmed(this.confirmed);
        if (this.accommodation!=null) reservation.setAccommodation(this.accommodation.toDTO(entities));
        if (this.customer!=null) reservation.setCustomer(this.customer.toCustomerDTO(entities));
        return reservation;
    }

    DtoReservation toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoReservation reservation=new DtoReservation();
        reservation.setId(this.id);
        reservation.setNumOfGuests(this.numOfGuests);
        reservation.setPrice(this.price);
        reservation.setDateFrom(this.dateFrom);
        reservation.setDateTo(this.dateTo);
        reservation.setPayDate(this.payDate);
        reservation.setCancelDate(this.cancelDate);
        reservation.setPaid(this.paid);
        reservation.setCheckInTimestamp(this.checkInTimestamp);
        reservation.setConfirmed(this.confirmed);
        if (this.accommodation!=null) reservation.setAccommodation(this.accommodation.toDTO(localEntities));
        if (this.customer!=null) reservation.setCustomer(this.customer.toCustomerDTO(localEntities));
        return reservation;
    }
}
