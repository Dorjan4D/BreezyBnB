package com.breezybnb.service;

import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.dto.DtoReview;
import com.breezybnb.entity.Accommodation;
import com.breezybnb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GuestService {

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


    @Transactional(readOnly = true)
    public List<DtoAccommodation> showAccommodations() {
        List<Accommodation> acs = accommodationRepository.findByVerifiedIsNotNull();
        List<DtoAccommodation> acsdto = MyConverter.convertToDTOList(acs);

        for (int i = 0; i < acs.size(); i++) {
            Accommodation a = acs.get(i);
            DtoAccommodation ad = acsdto.get(i);

            ad.setPhotos(a.retrievePhotosDTO());
            ad.setHost(null);
        }

        return acsdto;
    }

    @Transactional(readOnly = true)
    public DtoAccommodation getAccommodationById(Long id) {
        Accommodation a = accommodationRepository.findById(id).orElseThrow();
        DtoAccommodation ad = a.toDTO();
        ad.setPhotos(a.retrievePhotosDTO());
        return ad;
    }

    @Transactional(readOnly = true)
    public List<DtoReview> getReviewsByAccommodationId(Long id) {
        return MyConverter.convertToDTOList(reviewRepository.findByAccommodationId(id));
    }

}
