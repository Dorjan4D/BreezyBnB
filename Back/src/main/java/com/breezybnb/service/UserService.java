package com.breezybnb.service;

import com.breezybnb.entity.Admin;
import com.breezybnb.entity.Customer;
import com.breezybnb.entity.Host;
import com.breezybnb.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

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


    PasswordEncoder passwordEncoder;




    private void validatePassword(String password) {
        if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[\\w!@#$%^&*]{8,16}$")) {
            throw new IllegalStateException("Invalid password format");
        }
    }



    public Admin saveAdmin(Admin admin) {
        validatePassword(admin.getPassword());
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }


    public Customer saveCustomer(Customer customer) {
        validatePassword(customer.getPassword());
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }

    public Host saveHost(Host host) {
        validatePassword(host.getPassword());
        host.setPassword(passwordEncoder.encode(host.getPassword()));
        return hostRepository.save(host);
    }





}
