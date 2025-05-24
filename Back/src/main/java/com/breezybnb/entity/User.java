package com.breezybnb.entity;

import com.breezybnb.converter.ConvertibleToDTO;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoUser;
import com.breezybnb.entityWrapper.EntityWrapper;
import com.breezybnb.enums.Gender;
import com.breezybnb.filter.LazyFieldsFilter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = User.class)
public abstract class User implements ConvertibleToDTO<DtoUser> {




        private static final String NAME_REGEX = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$";
        private static final String SURNAME_REGEX = "^[a-zA-ZàáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð ,.'-]+$";
        private static final String USERNAME_REGEX = "^\\w+$";


        @PrePersist
        @PreUpdate
        private void beforeSaveOrUpdate() {
            validateName();
            validateSurname();
            validateUsername();
        }


        private void validateName() {
            if (!name.matches(NAME_REGEX)) {
                throw new IllegalStateException("Invalid name format");
            }
        }

        private void validateSurname() {
            if (!surname.matches(SURNAME_REGEX)) {
                throw new IllegalStateException("Invalid surname format");
            }
        }

        private void validateUsername() {
            if (!username.matches(USERNAME_REGEX)) {
                throw new IllegalStateException("Invalid username format");
            }
        }

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column(nullable = false)
    @NotBlank
    private String surname;

    @Column(nullable = false, unique = true)
    @NotBlank
    @Email
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    private LocalDateTime registered;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private Gender gender;

    @Column(nullable = false)
    @NotNull
    private LocalDate dateOfBirth;

    /*
    @JsonIgnore
    @Transient
    private transient boolean includePhoto = false;

    @JsonIgnore
    @Transient
    private transient boolean includePassword = false;
    */


    @JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "photo_id", nullable = false, unique = true)
    @JsonIgnore
    private Photo photo;

    public void assignPhoto(Photo photo) {  // always use new Photo(byte[] photo) constructor for photo
        photo.setUser(this);
        this.photo = photo;
    }





    @Override
    public DtoUser toDTO() {
        if (this.id==null) return null;
        Set<EntityWrapper> entities = new HashSet<>();
        entities.add(new EntityWrapper(this.getClass(), this.id));
        DtoUser user=new DtoUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        user.setRegistered(this.registered);
        user.setGender(this.gender);
        user.setDateOfBirth(this.dateOfBirth);
        return user;
    }

    DtoUser toDTO(Set<EntityWrapper> entities) {
        if (this.id==null) return null;
        Set<EntityWrapper> localEntities = new HashSet<>(entities);
        EntityWrapper thEntity = new EntityWrapper(this.getClass(), this.id);
        if (localEntities.contains(thEntity)) return null;
        localEntities.add(thEntity);
        DtoUser user=new DtoUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setName(this.name);
        user.setSurname(this.surname);
        user.setEmail(this.email);
        user.setRegistered(this.registered);
        user.setGender(this.gender);
        user.setDateOfBirth(this.dateOfBirth);
        return user;
    }
}
