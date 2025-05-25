package com.breezybnb.service;

import com.breezybnb.entity.*;
import com.breezybnb.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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

    @Autowired
    PasswordEncoder passwordEncoder;




    private void validatePassword(String password) {
        if (!password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[\\w!@#$%^&*]{8,16}$")) {
            throw new IllegalStateException("Invalid password format");
        }
    }


    private User inferUserTypeById(Long id) {
        User user = adminRepository.findById(id).orElse(null);
        if (user == null) {
            user = customerRepository.findById(id).orElse(null);
            if (user == null) {
                user = hostRepository.findById(id).orElse(null);
            }
        }
        return user;
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




    @Transactional(readOnly = true)
    public User login(String username, String password) {
        User user = adminRepository.findByUsername(username).orElse(null);
        if (user == null) {
            user = customerRepository.findByUsername(username).orElse(null);
            if (user == null) {
                user = hostRepository.findByUsername(username).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password"));
            }
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect username or password");
        }

        if (user.getRegistered() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please confirm your email!");
        }

        return user;
    }



    @Transactional(readOnly = true)
    public List<String> showAcmdtypes(Long usrId) {
        User user = inferUserTypeById(usrId);
        if (user instanceof Admin admin && admin.getVerified() != null
                || user instanceof Host host && host.getVerified() != null) return acmdtypeRepository.findAllDistinctTypes();
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only verified admins or verified hosts can view accommodation types");
    }



    public void removeAccommodation(Long usrId, Long accommodationId) {
        User user = inferUserTypeById(usrId);
        if (user instanceof Host) {
            Accommodation accommodation = accommodationRepository.findByIdAndHostId(accommodationId, usrId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found or does not belong to host"));
            accommodationRepository.delete(accommodation);
            return;
        }
        if (user instanceof Admin admin && admin.getVerified() != null) {
            Accommodation accommodation = accommodationRepository.findById(accommodationId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accommodation not found"));
            accommodationRepository.delete(accommodation);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only verified admins or owner hosts can remove accommodation");
    }
}
