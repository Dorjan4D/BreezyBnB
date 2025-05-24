package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoAcmdtype;
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
        scope = Acmdtype.class)
public class Acmdtype implements ConvertibleToDTO<DtoAcmdtype> {

    public Acmdtype(String type) {this.type = type;}

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, updatable = false, unique = true)
    @NotBlank
    private String type;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "acmdtype", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("verified DESC")
    private List<Accommodation> accommodations = new LinkedList<>();

    public List<DtoAccommodation> retrieveAccommodationsDTO() {
        return MyConverter.convertToDTOList(getAccommodations());
    }


    @Override
    public DtoAcmdtype toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoAcmdtype acmdtype=new DtoAcmdtype();
        acmdtype.setId(this.id);
        acmdtype.setType(this.type);
        return acmdtype;
    }

    DtoAcmdtype toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoAcmdtype acmdtype=new DtoAcmdtype();
        acmdtype.setId(this.id);
        acmdtype.setType(this.type);
        return acmdtype;
    }
}
