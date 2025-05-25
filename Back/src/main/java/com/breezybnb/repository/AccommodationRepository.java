package com.breezybnb.repository;

import com.breezybnb.entity.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    List<Accommodation> findByVerifiedIsNotNull();

    List<Accommodation> findByHostId(Long id);

    Optional<Accommodation> findByIdAndHostId(Long accommodationId, Long hostId);
}
