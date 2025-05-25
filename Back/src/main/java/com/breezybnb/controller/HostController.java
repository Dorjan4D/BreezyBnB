package com.breezybnb.controller;

import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.entity.Accommodation;
import com.breezybnb.repository.*;
import com.breezybnb.service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/host")
public class HostController {

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


    private Long checkNullSession(HttpSession session) {
        Long id = (Long) session.getAttribute("id");
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not logged in");
        }
        return id;
    }


    @GetMapping("/myAccommodations")
    public ResponseEntity<List<DtoAccommodation>> showMyAccommodations(HttpSession session) {
        return ResponseEntity.ok(hostService.getAccommodationsByHostId(checkNullSession(session)));
    }

    @PostMapping("/addAccommodation")
    public ResponseEntity<String> addAccommodation(HttpSession session, @RequestBody @Valid Accommodation argAccommodation) {
        hostService.addAccommodation(checkNullSession(session), argAccommodation);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/editAccommodation")
    public ResponseEntity<String> editAccommodation(HttpSession session, @RequestBody @Valid Accommodation argAccommodation) {
        hostService.editAccommodation(checkNullSession(session), argAccommodation);
        return ResponseEntity.ok("Success");
    }


}
