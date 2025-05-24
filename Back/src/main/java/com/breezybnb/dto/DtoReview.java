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
public class DtoReview {

    private Long id;

    @NotNull
    private Integer rating;

    private String comment;

    private LocalDateTime updated;

    private DtoAccommodation accommodation;

    private DtoCustomer customer;
}
