package com.breezybnb.controller;

import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoReview;
import com.breezybnb.repository.*;
import com.breezybnb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GuestController {

    @Autowired
    private AccommodationRepository accommodationRepository;
    @Autowired
    private AcmdtypeRepository acmdtypeRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private HostRepository hostRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private HostService hostService;

    @Autowired
    private GuestService guestService;




    @GetMapping("/accommodations")
    public ResponseEntity<List<DtoAccommodation>> showAccommodations() {
        return ResponseEntity.ok(guestService.showAccommodations());
    }

    @GetMapping("/accommodations/{id}")
    public ResponseEntity<DtoAccommodation> getAccommodationById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.getAccommodationById(id));
    }

    @GetMapping("/accommodations/{id}/reviews")
    public ResponseEntity<List<DtoReview>> getReviewsByAccommodationId (@PathVariable Long id) {
        return ResponseEntity.ok(guestService.getReviewsByAccommodationId(id));
    }
}
