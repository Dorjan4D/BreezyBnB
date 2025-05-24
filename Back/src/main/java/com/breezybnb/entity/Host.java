package com.breezybnb.entity;

import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoCustomer;
import com.breezybnb.dto.DtoHistory;
import com.breezybnb.dto.DtoHost;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.breezybnb.filter.LazyFieldsFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Host.class)
public class Host extends User {

    private LocalDateTime verified;

    private String bio;

    private String contactPhone;

    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("verified DESC")
    private List<Accommodation> accommodations = new LinkedList<>();

    public void addAccommodation(Accommodation accommodation) {
        accommodation.setHost(this);
        this.accommodations.add(accommodation);
    }

    public void addMultipleAccommodations(List<Accommodation> accommodations) { for (Accommodation accommodation:accommodations) addAccommodation(accommodation); }

    public List<DtoAccommodation> retrieveAccommodationsDTO() {
        return MyConverter.convertToDTOList(getAccommodations());
    }




    public DtoHost toHostDTO() {
        if (this.getId()==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.getId()));
        DtoHost host = new DtoHost();
        host.setId(getId());
        host.setUsername(getUsername());
        host.setName(getName());
        host.setSurname(getSurname());
        host.setEmail(getEmail());
        host.setRegistered(getRegistered());
        host.setGender(getGender());
        host.setDateOfBirth(getDateOfBirth());
        host.setVerified(this.verified);
        host.setBio(this.bio);
        host.setContactPhone(this.contactPhone);
        return host;
    }

    DtoHost toHostDTO(Set<EntityWrapper> entities) {
        if (this.getId()==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.getId());
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoHost host = new DtoHost();
        host.setId(getId());
        host.setUsername(getUsername());
        host.setName(getName());
        host.setSurname(getSurname());
        host.setEmail(getEmail());
        host.setRegistered(getRegistered());
        host.setGender(getGender());
        host.setDateOfBirth(getDateOfBirth());
        host.setVerified(this.verified);
        host.setBio(this.bio);
        host.setContactPhone(this.contactPhone);
        return host;
    }




    public static List<DtoHost> convertToHostDTOList(List<Host> entityList) {
        if (entityList == null) {
            return Collections.emptyList();  // Return an empty list if the argument is null
        }

        return entityList.stream()
                .map(Host::toHostDTO)
                .collect(Collectors.toList());
    }
}
