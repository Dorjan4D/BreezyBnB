package com.breezybnb.controller;

import com.breezybnb.repository.*;
import com.breezybnb.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
public class AdminController {

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

    @PostMapping("/addAcmdtype")
    public ResponseEntity<String> addAcmdtype(HttpSession session, @RequestBody String newType) {
        adminService.addAcmdtype(checkNullSession(session), newType);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/updateAcmdtype")
    public ResponseEntity<String> updateAcmdtype(HttpSession session, @RequestParam String oldType, @RequestParam String newType) {
        adminService.updateAcmdtype(checkNullSession(session), oldType, newType);
        return ResponseEntity.ok("Success");
    }

    @PostMapping("/removeAcmdtype")
    public ResponseEntity<String> removeAcmdtype(HttpSession session, @RequestBody String type) {
        adminService.removeAcmdtype(checkNullSession(session), type);
        return ResponseEntity.ok("Success");
    }

}
