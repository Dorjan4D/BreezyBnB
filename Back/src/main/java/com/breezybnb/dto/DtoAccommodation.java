package com.breezybnb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
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
public class DtoAccommodation {

    private Long id;

    //@NotNull
    //private AccommodationType type;

    private LocalDateTime verified;

    @NotBlank
    private String name;

    private String place;

    @NotBlank
    private String address;

    private Double areaSquareMeters;

    private Double costPerNight;

    private String description;

    private Integer numOfBedrooms;

    private Integer numOfBeds;

    private Integer numOfBathrooms;

    private Integer maxNumOfGuests;

    private DtoAcmdtype acmdtype;

    private DtoHost host;

    private List<DtoPhoto> photos;

    private List<DtoReview> reviews;

    private List<DtoReservation> reservations;

    private List<DtoHistory> histories;
}
