package com.breezybnb;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccommodationFlowTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    @DisplayName("Guest endpoint reachable in full context")
    void guestEndpointReachable() {
        var resp = rest.getForEntity("/accommodations", String.class);
        assertThat(resp.getStatusCode().is2xxSuccessful()).isTrue();
    }
}
