package com.breezybnb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoHost extends DtoUser {

    private LocalDateTime verified;

    private String bio;

    private String contactPhone;

    private List<DtoAccommodation> accommodations;
}
