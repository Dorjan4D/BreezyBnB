package com.breezybnb.service;

import com.breezybnb.entity.Acmdtype;
import com.breezybnb.entity.Admin;
import com.breezybnb.entity.User;
import com.breezybnb.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AdminService {

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


    public void addAcmdtype(Long usrId, String newType) {
        User user = inferUserTypeById(usrId);

        if (user instanceof Admin admin && admin.getVerified() != null) {
            Acmdtype acmdtype = new Acmdtype(newType);
            acmdtypeRepository.save(acmdtype);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only verified admins can add accommodation types");
    }

    public void updateAcmdtype(Long usrId, String oldType, String newType) {
        User user = inferUserTypeById(usrId);

        if (user instanceof Admin admin && admin.getVerified() != null) {
            Acmdtype acmdtype = acmdtypeRepository.findByType(oldType).orElseThrow();
            acmdtype.setType(newType);
            acmdtypeRepository.save(acmdtype);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only verified admins can update accommodation types");
    }

    public void removeAcmdtype(Long usrId, String type) {
        User user = inferUserTypeById(usrId);

        if (user instanceof Admin admin && admin.getVerified() != null) {
            Acmdtype acmdtype = acmdtypeRepository.findByType(type).orElseThrow();
            acmdtypeRepository.delete(acmdtype);
            return;
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Only verified admins can remove accommodation types and delete associated accommodations");
    }


}
