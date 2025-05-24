package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoHistory;
import com.breezybnb.dto.DtoPayment;
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
        scope = History.class)
public class History implements ConvertibleToDTO<DtoHistory> {

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

    private LocalDateTime checkOutTimestamp;

    private LocalDateTime canceledTimestamp;



    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "accommodation_id", nullable = false, updatable = false)
    private Accommodation accommodation;

    public void assignAccommodation(Accommodation accommodation) {
        accommodation.getHistories().add(this);
        this.accommodation = accommodation;
    }

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;

    public void assignCustomer(Customer customer) {
        customer.getHistories().add(this);
        this.customer = customer;
    }



    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "history", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @OrderBy("timestamp DESC")
    private List<Payment> payments = new LinkedList<>();

    public void addPayment(Payment payment) {
        payment.setHistory(this);
        this.payments.add(payment);
    }

    public void addMultiplePayments(List<Payment> payments) { for (Payment payment:payments) addPayment(payment); }

    public List<DtoPayment> retrievePaymentsDTO() {
        return MyConverter.convertToDTOList(getPayments());
    }

    public LinkedList<Payment> disassociateAllPayments() {
        LinkedList<Payment> disassociatedPayments = new LinkedList<>();

        for (Payment payment : new ArrayList<>(payments)) {
            payment.setHistory(null); // Set parent to null
            disassociatedPayments.add(payment); // Add to the new LinkedList
        }

        payments.clear(); // Clear the original list
        return disassociatedPayments; // Return the new LinkedList with disassociated children
    }




    @Override
    public DtoHistory toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoHistory history=new DtoHistory();
        history.setId(this.id);
        history.setNumOfGuests(this.numOfGuests);
        history.setPrice(this.price);
        history.setDateFrom(this.dateFrom);
        history.setDateTo(this.dateTo);
        history.setPayDate(this.payDate);
        history.setCancelDate(this.cancelDate);
        history.setPaid(this.paid);
        history.setCheckInTimestamp(this.checkInTimestamp);
        history.setCheckOutTimestamp(this.checkOutTimestamp);
        history.setCanceledTimestamp(this.canceledTimestamp);
        if (this.accommodation!=null) history.setAccommodation(this.accommodation.toDTO(entities));
        if (this.customer!=null) history.setCustomer(this.customer.toCustomerDTO(entities));
        return history;
    }

    DtoHistory toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoHistory history=new DtoHistory();
        history.setId(this.id);
        history.setNumOfGuests(this.numOfGuests);
        history.setPrice(this.price);
        history.setDateFrom(this.dateFrom);
        history.setDateTo(this.dateTo);
        history.setPayDate(this.payDate);
        history.setCancelDate(this.cancelDate);
        history.setPaid(this.paid);
        history.setCheckInTimestamp(this.checkInTimestamp);
        history.setCheckOutTimestamp(this.checkOutTimestamp);
        history.setCanceledTimestamp(this.canceledTimestamp);
        if (this.accommodation!=null) history.setAccommodation(this.accommodation.toDTO(localEntities));
        if (this.customer!=null) history.setCustomer(this.customer.toCustomerDTO(localEntities));
        return history;
    }
}
