package com.breezybnb.entity;

import com.breezybnb.dto.DtoAdmin;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        scope = Admin.class)
public class Admin extends User {

    private LocalDateTime verified;




    public DtoAdmin toAdminDTO() {
        if (this.getId()==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.getId()));
        DtoAdmin admin = new DtoAdmin();
        admin.setId(getId());
        admin.setUsername(getUsername());
        admin.setName(getName());
        admin.setSurname(getSurname());
        admin.setEmail(getEmail());
        admin.setRegistered(getRegistered());
        admin.setGender(getGender());
        admin.setDateOfBirth(getDateOfBirth());
        admin.setVerified(this.verified);
        return admin;
    }

    DtoAdmin toAdminDTO(Set<EntityWrapper> entities) {
        if (this.getId()==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.getId());
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoAdmin admin = new DtoAdmin();
        admin.setId(getId());
        admin.setUsername(getUsername());
        admin.setName(getName());
        admin.setSurname(getSurname());
        admin.setEmail(getEmail());
        admin.setRegistered(getRegistered());
        admin.setGender(getGender());
        admin.setDateOfBirth(getDateOfBirth());
        admin.setVerified(this.verified);
        return admin;
    }


    public static List<DtoAdmin> convertToAdminDTOList(List<Admin> entityList) {
        if (entityList == null) {
            return Collections.emptyList();  // Return an empty list if the argument is null
        }

        return entityList.stream()
                .map(Admin::toAdminDTO)
                .collect(Collectors.toList());
    }
}
