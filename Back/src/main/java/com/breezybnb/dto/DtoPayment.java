package com.breezybnb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoPayment {

    private Long id;

    @NotNull
    private Double amount;

    private LocalDateTime timestamp;

    private LocalDateTime refundedTimestamp;

    private DtoReservation reservation;

    private DtoHistory history;
}
