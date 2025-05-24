package com.breezybnb.repository;

import com.breezybnb.entity.Host;
import com.breezybnb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {

    Optional<Host> findByUsername(String username);
}
