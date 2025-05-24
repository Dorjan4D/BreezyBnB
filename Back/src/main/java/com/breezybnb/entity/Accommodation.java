package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.*;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.breezybnb.filter.LazyFieldsFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
        scope = Accommodation.class)
public class Accommodation implements ConvertibleToDTO<DtoAccommodation> {

    @PrePersist
    @PreUpdate
    private void validate() {
        if (this.photos == null || this.photos.isEmpty()) {
            throw new IllegalStateException("An accommodation must have at least one photo");
        }
    }


    @Id
    @GeneratedValue
    private Long id;

    //@Enumerated(EnumType.STRING)
    //@Column(nullable = false)
    //@NotNull
    //private AccommodationType type;

    private LocalDateTime verified;

    @Column(nullable = false)
    @NotBlank
    private String name;

    private String place;

    @Column(nullable = false)
    @NotBlank
    private String address;

    private Double areaSquareMeters;

    private Double costPerNight;

    private String description;

    private Integer numOfBedrooms;

    private Integer numOfBeds;

    private Integer numOfBathrooms;

    private Integer maxNumOfGuests;

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "acmdtype_id", nullable = false)
    private Acmdtype acmdtype;

    public void assignAcmdtype(Acmdtype acmdtype) {
        acmdtype.getAccommodations().add(this);
        this.acmdtype = acmdtype;
    }

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "host_id", nullable = false)
    private Host host;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("uploaded DESC")
    private List<Photo> photos = new LinkedList<>();

    public void addPhoto(Photo photo) {
        photo.setAccommodation(this);
        this.photos.add(photo);
    }

    public void addMultiplePhotos(List<Photo> photos) { for (Photo photo:photos) addPhoto(photo); }

    public List<DtoPhoto> retrievePhotosDTO() {
        return MyConverter.convertToDTOList(getPhotos());
    }

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updated DESC")
    private List<Review> reviews = new LinkedList<>();

    public void addReview(Review review) {
        review.setAccommodation(this);
        this.reviews.add(review);
    }

    public void addMultipleReviews(List<Review> reviews) { for (Review review:reviews) addReview(review); }

    public List<DtoReview> retrieveReviewsDTO() {
        return MyConverter.convertToDTOList(getReviews());
    }

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateFrom DESC")
    private List<Reservation> reservations = new LinkedList<>();

    public List<DtoReservation> retrieveReservationsDTO() {
        return MyConverter.convertToDTOList(getReservations());
    }

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("dateFrom DESC")
    private List<History> histories = new LinkedList<>();

    public List<DtoHistory> retrieveHistoriesDTO() {
        return MyConverter.convertToDTOList(getHistories());
    }


    @Override
    public DtoAccommodation toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoAccommodation accommodation=new DtoAccommodation();
        accommodation.setId(this.id);
        //accommodation.setType(this.type);
        accommodation.setVerified(this.verified);
        accommodation.setName(this.name);
        accommodation.setPlace(this.place);
        accommodation.setAddress(this.address);
        accommodation.setAreaSquareMeters(this.areaSquareMeters);
        accommodation.setCostPerNight(this.costPerNight);
        accommodation.setDescription(this.description);
        accommodation.setNumOfBedrooms(this.numOfBedrooms);
        accommodation.setNumOfBeds(this.numOfBeds);
        accommodation.setNumOfBathrooms(this.numOfBathrooms);
        accommodation.setMaxNumOfGuests(this.maxNumOfGuests);
        if (this.acmdtype!=null) accommodation.setAcmdtype(this.acmdtype.toDTO(entities));
        if (this.host!=null) accommodation.setHost(this.host.toHostDTO(entities));
        return accommodation;
    }

    DtoAccommodation toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoAccommodation accommodation=new DtoAccommodation();
        accommodation.setId(this.id);
        //accommodation.setType(this.type);
        accommodation.setVerified(this.verified);
        accommodation.setName(this.name);
        accommodation.setPlace(this.place);
        accommodation.setAddress(this.address);
        accommodation.setAreaSquareMeters(this.areaSquareMeters);
        accommodation.setCostPerNight(this.costPerNight);
        accommodation.setDescription(this.description);
        accommodation.setNumOfBedrooms(this.numOfBedrooms);
        accommodation.setNumOfBeds(this.numOfBeds);
        accommodation.setNumOfBathrooms(this.numOfBathrooms);
        accommodation.setMaxNumOfGuests(this.maxNumOfGuests);
        if (this.acmdtype!=null) accommodation.setAcmdtype(this.acmdtype.toDTO(localEntities));
        if (this.host!=null) accommodation.setHost(this.host.toHostDTO(localEntities));
        return accommodation;
    }
}
