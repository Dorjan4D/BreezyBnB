package com.breezybnb.service;

import com.breezybnb.converter.MyConverter;
import com.breezybnb.dto.DtoAccommodation;
import com.breezybnb.entity.Accommodation;
import com.breezybnb.entity.Host;
import com.breezybnb.entity.Photo;
import com.breezybnb.entity.User;
import com.breezybnb.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class HostService {

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


    @Transactional(readOnly = true)
    public List<DtoAccommodation> getAccommodationsByHostId(Long usrid) {
        List<Accommodation> acs = accommodationRepository.findByHostId(usrid);
        List<DtoAccommodation> acsdto = MyConverter.convertToDTOList(acs);

        for (int i = 0; i < acs.size(); i++) {
            Accommodation a = acs.get(i);
            DtoAccommodation ad = acsdto.get(i);

            ad.setPhotos(a.retrievePhotosDTO());
            ad.setHost(null);
        }

        return acsdto;
    }




    public void addAccommodation(Long usrid, Accommodation argAccommodation) {
        User user = inferUserTypeById(usrid);
        if (!(user instanceof Host host)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Must be a host");
        Accommodation accommodation = new Accommodation();

        // pretend accommodation is verified right away
        accommodation.setVerified(LocalDateTime.now());
        List<Photo> newPhotos = argAccommodation.getPhotos().stream()
                .map(p -> new Photo(p.getPhoto()))
                .toList();
        accommodation.addMultiplePhotos(newPhotos);


        accommodation.assignAcmdtype(acmdtypeRepository.findByType(argAccommodation.getAcmdtype().getType()).orElseThrow());

        accommodation.setName(argAccommodation.getName());
        accommodation.setPlace(argAccommodation.getPlace());
        accommodation.setAddress(argAccommodation.getAddress());
        accommodation.setAreaSquareMeters(argAccommodation.getAreaSquareMeters());
        accommodation.setCostPerNight(argAccommodation.getCostPerNight());
        accommodation.setDescription(argAccommodation.getDescription());
        accommodation.setNumOfBedrooms(argAccommodation.getNumOfBedrooms());
        accommodation.setNumOfBeds(argAccommodation.getNumOfBeds());
        accommodation.setNumOfBathrooms(argAccommodation.getNumOfBathrooms());
        accommodation.setMaxNumOfGuests(argAccommodation.getMaxNumOfGuests());

        host.addAccommodation(accommodation);

        hostRepository.save(host);

    }
}
