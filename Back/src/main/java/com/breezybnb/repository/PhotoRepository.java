package com.breezybnb.repository;

import com.breezybnb.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {

    // Example: delete by user ID if FK exists in Photo (owning side):
    // @Transactional
    // @Modifying
    // void deleteByUserId(Long userId);
}
