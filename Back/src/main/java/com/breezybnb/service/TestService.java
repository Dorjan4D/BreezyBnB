package com.breezybnb.service;

import com.breezybnb.entity.Acmdtype;
import com.breezybnb.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class TestService {
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

    public void populateAcmdtypes() {
        List<Acmdtype> l = new LinkedList<>();
        List<String> s = new ArrayList<>(List.of("Apartment", "House", "Studio", "Cabin", "Villa", "Hostel", "Hotel"));
        for (String type:s) {
            l.add(new Acmdtype(type));
        }
        acmdtypeRepository.saveAll(l);
    }

    /*
    public void deletePhotoById(Long id) {
        Optional<Photo> photo = photoRepository.findById(id);
        photo.ifPresent(photoRepository::delete);
    }

    public void delFunction() {
        Photo p1 = photoRepository.findById(1L).orElseThrow();
        User u1 = p1.getUser();
        p1.getUser().setUsername("rty");
        System.out.println(u1.getUsername());
        //photoRepository.save(p1);
        User u1 = userRepository.findById(1L).orElseThrow();
        u1.setPhoto(null);
    }

    public void testFunction() {
        Photo p1 = new Photo("p1");
        Photo p2 = new Photo("p2");
        Photo p3 = new Photo("p3");
        Photo p4 = new Photo("p4");
        Photo p5 = new Photo("p5");
        Photo p6 = new Photo("p6");
        User u1 = new Admin();
        u1.setUsername("u1");
        User u2 = new Admin();
        u2.setUsername("u2");
        User u3 = new Admin();
        u3.setUsername("u3");
        User u4 = new Admin();
        u4.setUsername("u4");
        User u5 = new Admin();
        u5.setUsername("u5");
        User u6 = new Admin();
        u6.setUsername("u6");

        u1.assignPhoto(p1);
        userRepository.save(u1);
        u2.assignPhoto(p2);
        photoRepository.save(p2);
        u3.setPhoto(p3);
        userRepository.save(u3);

        u4.setPhoto(p4);
        photoRepository.save(p4);
        p5.setUser(u5);
        userRepository.save(u5);
        p6.setUser(u6);
        photoRepository.save(p6);
    }



    public void populateInitialData() {
        // 1) Create an Admin
        Admin admin = new Admin();
        admin.setUsername("admin1");
        admin.setName("Alice");
        admin.setSurname("Adminson");
        admin.setEmail("alice.admin@example.com");
        admin.setPassword("adminPass");
        admin.setGender(Gender.FEMALE);
        admin.setDateOfBirth(LocalDate.of(1990, 1, 15));
        admin.setRegistered(LocalDateTime.now());
        // Each user must have a Photo
        Photo adminPhoto = new Photo("https://example.com/images/admin1.jpg");
        admin.assignPhoto(adminPhoto);

        // 2) Create a Host
        Host host = new Host();
        host.setUsername("host1");
        host.setName("Bob");
        host.setSurname("Hoster");
        host.setEmail("bob.hoster@example.com");
        host.setPassword("hostPass");
        host.setGender(Gender.MALE);
        host.setDateOfBirth(LocalDate.of(1985, 5, 10));
        host.setRegistered(LocalDateTime.now());
        host.setVerified(LocalDateTime.now()); // "verified" field
        // Host's photo
        Photo hostPhoto = new Photo("https://example.com/images/host1.jpg");
        host.assignPhoto(hostPhoto);

        // 3) Create a Customer
        Customer customer = new Customer();
        customer.setUsername("cust1");
        customer.setName("Charlie");
        customer.setSurname("Customerson");
        customer.setEmail("charlie.customer@example.com");
        customer.setPassword("custPass");
        customer.setGender(Gender.OTHER);
        customer.setDateOfBirth(LocalDate.of(2000, 3, 20));
        customer.setRegistered(LocalDateTime.now());
        // Customer's photo
        Photo custPhoto = new Photo("https://example.com/images/customer1.jpg");
        customer.assignPhoto(custPhoto);

        // 4) Create an Accommodation with Photos
        Accommodation accom = new Accommodation();
        accom.setType(AccommodationType.APARTMENT);
        accom.setName("Sunny Apartment");
        accom.setAddress("123 Sunshine Ave, NiceCity");
        accom.setAreaSquareMeters(85.0);
        accom.setCostPerNight(120.0);
        accom.setDescription("A lovely sunny apartment close to the beach.");
        accom.setNumOfBedrooms(2);
        accom.setNumOfBeds(3);
        accom.setNumOfBathrooms(1);
        accom.setMaxNumOfGuests(4);
        accom.setVerified(LocalDateTime.now());
        // Link it to the Host
        accom.setHost(host);

        // Add at least one Photo
        Photo accomPhoto1 = new Photo("https://example.com/images/apartment1_main.jpg");
        Photo accomPhoto2 = new Photo("https://example.com/images/apartment1_livingroom.jpg");
        accom.addPhoto(accomPhoto1);
        accom.addPhoto(accomPhoto2);

        // 5) Save main entities so they have IDs
        adminRepository.save(admin);
        hostRepository.save(host);
        customerRepository.save(customer);
        accommodationRepository.save(accom);

        // 6) Create a Reservation
        Reservation reservation = new Reservation();
        reservation.setDateFrom(LocalDate.now().plusDays(7));
        reservation.setDateTo(LocalDate.now().plusDays(14));
        reservation.setNumOfGuests(3);
        reservation.setPrice(accom.getCostPerNight() * 7);
        reservation.assignAccommodation(accom);
        reservation.assignCustomer(customer);
        reservationRepository.save(reservation);

        // 7) Create a Review
        Review review = new Review();
        review.setRating(5);
        review.setComment("Awesome place, highly recommended!");
        // 'updated' is set by onCreate()
        review.assignAccommodation(accom);
        review.assignCustomer(customer);
        reviewRepository.save(review);

        // 8) Optionally, create a Payment
        Payment payment = new Payment();
        payment.setAmount(reservation.getPrice());
        payment.setRefundedTimestamp(null); // not refunded
        // set the reservation
        payment.setReservation(reservation);
        // Or put it in a History object (up to your business logic)...

        paymentRepository.save(payment);

        // 9) If you want to create a "History" entry
        History history = new History();
        history.setAccommodation(accom);
        history.setCustomer(customer);
        history.setNumOfGuests(3);
        history.setPrice(reservation.getPrice());
        history.setDateFrom(reservation.getDateFrom());
        history.setDateTo(reservation.getDateTo());
        history.setPaid(LocalDateTime.now()); // maybe user paid
        // Link the Payment to the History
        history.addPayment(payment);
        historyRepository.save(history);

        // Everything is saved now in the DB with relationships intact
    }
    */

}
