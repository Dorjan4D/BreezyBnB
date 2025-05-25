package com.breezybnb.service;

import com.breezybnb.entity.Acmdtype;
import com.breezybnb.entity.Admin;
import com.breezybnb.repository.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    @Mock  private AccommodationRepository accommodationRepository;
    @Mock  private AcmdtypeRepository      acmdtypeRepository;
    @Mock  private AdminRepository         adminRepository;
    @Mock  private CustomerRepository      customerRepository;
    @Mock  private HostRepository          hostRepository;
    @Mock  private HistoryRepository       historyRepository;
    @Mock  private PaymentRepository       paymentRepository;
    @Mock  private PhotoRepository         photoRepository;
    @Mock  private ReservationRepository   reservationRepository;
    @Mock  private ReviewRepository        reviewRepository;
    @Mock  private UserRepository          userRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("addAcmdtype() persists a new type when caller is a verified admin")
    void addType_saves() {

        long adminId = 1L;
        Admin verifiedAdmin = new Admin();
        verifiedAdmin.setId(adminId);
        verifiedAdmin.setVerified(LocalDateTime.now());
        when(adminRepository.findById(adminId)).thenReturn(Optional.of(verifiedAdmin));

        adminService.addAcmdtype(adminId, "VILLA");

        verify(acmdtypeRepository).save(any(Acmdtype.class));
    }
}
