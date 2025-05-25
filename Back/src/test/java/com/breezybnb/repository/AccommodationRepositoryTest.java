package com.breezybnb.repository;

import com.breezybnb.entity.Accommodation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class AccommodationRepositoryTest {

    @Autowired
    private AccommodationRepository repo;

    @Test
    @DisplayName("findByVerifiedIsNotNull returns only verified accommodations")
    void verifiedOnly() {
        List<Accommodation> list = repo.findByVerifiedIsNotNull();
        assertThat(list).isEmpty();
    }
}
