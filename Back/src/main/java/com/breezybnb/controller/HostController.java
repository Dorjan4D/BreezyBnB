package com.breezybnb.controller;

import com.breezybnb.repository.*;
import com.breezybnb.service.AdminService;
import com.breezybnb.service.CustomerService;
import com.breezybnb.service.HostService;
import com.breezybnb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
