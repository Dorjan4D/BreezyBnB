package com.breezybnb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoCustomer extends DtoUser {

    private List<DtoReview> reviews;

    private List<DtoReservation> reservations;

    private List<DtoHistory> histories;
}
