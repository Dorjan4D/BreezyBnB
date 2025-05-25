package com.breezybnb.controller;

import com.breezybnb.entity.*;
import com.breezybnb.repository.*;
import com.breezybnb.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class UserController {

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


    @PostMapping("/login")
    public ResponseEntity<String> login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session) {

        // if login fails, an exception is thrown and handled globally
        User user = userService.login(username, password);
        session.setAttribute("id", user.getId());

        // return type of user as plain string
        return ResponseEntity.ok(user instanceof Admin ? "admin"
                : user instanceof Customer ? "customer"
                : "host");
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
            return ResponseEntity.ok("Logout successful");
        } else return ResponseEntity.ok("No active session to logout from");
    }



    @PostMapping("/register/admin")
    public ResponseEntity<String> registerAdmin(@RequestBody Admin argAdmin) {

        Admin admin = new Admin();
        admin.setUsername(argAdmin.getUsername());
        admin.setName(argAdmin.getName());
        admin.setSurname(argAdmin.getSurname());
        admin.setEmail(argAdmin.getEmail());
        admin.setPassword(argAdmin.getPassword());
        admin.assignPhoto(new Photo(argAdmin.getPhoto().getPhoto()));
        admin.setGender(argAdmin.getGender());
        admin.setDateOfBirth(argAdmin.getDateOfBirth());

        // pretend everybody is already registered and verified
        admin.setRegistered(LocalDateTime.now());
        admin.setVerified(LocalDateTime.now());

        userService.saveAdmin(admin);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/register/customer")
    public ResponseEntity<String> registerCustomer(@RequestBody Customer argCustomer) {

        Customer customer = new Customer();
        customer.setUsername(argCustomer.getUsername());
        customer.setName(argCustomer.getName());
        customer.setSurname(argCustomer.getSurname());
        customer.setEmail(argCustomer.getEmail());
        customer.setPassword(argCustomer.getPassword());
        customer.assignPhoto(new Photo(argCustomer.getPhoto().getPhoto()));
        customer.setGender(argCustomer.getGender());
        customer.setDateOfBirth(argCustomer.getDateOfBirth());

        // pretend everybody is already registered and verified
        customer.setRegistered(LocalDateTime.now());

        userService.saveCustomer(customer);

        return ResponseEntity.ok("Success");
    }


    @PostMapping("/register/host")
    public ResponseEntity<String> registerHost(@RequestBody Host argHost) {

        Host host = new Host();
        host.setUsername(argHost.getUsername());
        host.setName(argHost.getName());
        host.setSurname(argHost.getSurname());
        host.setEmail(argHost.getEmail());
        host.setPassword(argHost.getPassword());
        host.assignPhoto(new Photo(argHost.getPhoto().getPhoto()));
        host.setGender(argHost.getGender());
        host.setDateOfBirth(argHost.getDateOfBirth());

        // pretend everybody is already registered and verified
        host.setRegistered(LocalDateTime.now());
        host.setVerified(LocalDateTime.now());

        host.setBio(argHost.getBio());
        host.setContactPhone(argHost.getContactPhone());

        userService.saveHost(host);

        return ResponseEntity.ok("Success");
    }


    @GetMapping("/acmdtypes")
    public ResponseEntity<List<String>> showAcmdtypes(HttpSession session) {
        return ResponseEntity.ok(userService.showAcmdtypes(checkNullSession(session)));
    }


    @PostMapping("/removeAccommodation/{accommodationId}")
    public ResponseEntity<String> removeAccommodation(HttpSession session, @PathVariable Long accommodationId) {
        userService.removeAccommodation(checkNullSession(session), accommodationId);
        return ResponseEntity.ok("Success");
    }

}
