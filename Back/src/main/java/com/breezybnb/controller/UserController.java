package com.breezybnb.controller;

import com.breezybnb.entity.*;
import com.breezybnb.repository.*;
import com.breezybnb.service.AdminService;
import com.breezybnb.service.CustomerService;
import com.breezybnb.service.HostService;
import com.breezybnb.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

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




    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password, HttpSession session){
        String typeOfUser;
        User usr=adminRepository.findByUsername(username).orElse(null);
        if (usr != null) typeOfUser = "admin";
        else {
            usr = customerRepository.findByUsername(username).orElse(null);
            if (usr != null) typeOfUser = "customer";
            else {
                usr = hostRepository.findByUsername(username).orElse(null);
                if (usr != null) typeOfUser = "host";
                else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
            }
        }

        if (passwordEncoder.matches(password, usr.getPassword())) {
            if (usr.getRegistered() != null) {
                session.setAttribute("id", usr.getId());
                return ResponseEntity.ok(typeOfUser);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please confirm your email!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");

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
        if (userRepository.existsByUsername(argAdmin.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        if (userRepository.existsByEmail(argAdmin.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken");
        }
        Admin admin = new Admin();
        admin.setUsername(argAdmin.getUsername());
        admin.setName(argAdmin.getName());
        admin.setSurname(argAdmin.getSurname());
        admin.setEmail(argAdmin.getEmail());
        admin.setPassword(argAdmin.getPassword());
        admin.assignPhoto(argAdmin.getPhoto());
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
        if (userRepository.existsByUsername(argCustomer.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        if (userRepository.existsByEmail(argCustomer.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken");
        }
        Customer customer = new Customer();
        customer.setUsername(argCustomer.getUsername());
        customer.setName(argCustomer.getName());
        customer.setSurname(argCustomer.getSurname());
        customer.setEmail(argCustomer.getEmail());
        customer.setPassword(argCustomer.getPassword());
        customer.assignPhoto(argCustomer.getPhoto());
        customer.setGender(argCustomer.getGender());
        customer.setDateOfBirth(argCustomer.getDateOfBirth());

        // pretend everybody is already registered and verified
        customer.setRegistered(LocalDateTime.now());

        userService.saveCustomer(customer);

        return ResponseEntity.ok("Success");
    }


    @PostMapping("/register/host")
    public ResponseEntity<String> registerHost(@RequestBody Host argHost) {
        if (userRepository.existsByUsername(argHost.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");
        }
        if (userRepository.existsByEmail(argHost.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already taken");
        }
        Host host = new Host();
        host.setUsername(argHost.getUsername());
        host.setName(argHost.getName());
        host.setSurname(argHost.getSurname());
        host.setEmail(argHost.getEmail());
        host.setPassword(argHost.getPassword());
        host.assignPhoto(argHost.getPhoto());
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
}
