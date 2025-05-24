package com.breezybnb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoHistory {

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

    private DtoAccommodation accommodation;

    private DtoCustomer customer;

    private List<DtoPayment> payments;
}
