package com.breezybnb.repository;

import com.breezybnb.entity.Acmdtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcmdtypeRepository extends JpaRepository<Acmdtype, Long> {

    boolean existsByType(String type);

    Optional<Acmdtype> findByType(String type);
}
