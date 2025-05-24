package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoPhoto;
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
@NoArgsConstructor
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Photo.class)
public class Photo implements ConvertibleToDTO<DtoPhoto> {

    public Photo(byte[] photo) {
        this.photo = photo;
    }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @NotNull
    private byte[] photo; // changed (later) to byte[]

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploaded = LocalDateTime.now();

    @OneToOne(mappedBy = "photo", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private User user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;




    @Override
    public DtoPhoto toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoPhoto photo1=new DtoPhoto();
        photo1.setId(this.id);
        photo1.setPhoto(this.photo);
        photo1.setUploaded(this.uploaded);
        return photo1;
    }

    DtoPhoto toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoPhoto photo1=new DtoPhoto();
        photo1.setId(this.id);
        photo1.setPhoto(this.photo);
        photo1.setUploaded(this.uploaded);
        return photo1;
    }
}
