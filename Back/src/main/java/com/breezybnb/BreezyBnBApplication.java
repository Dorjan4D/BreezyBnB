package com.breezybnb;

import com.breezybnb.entity.Admin;
import com.breezybnb.entity.Photo;
import com.breezybnb.entity.User;
import com.breezybnb.enums.Gender;
import com.breezybnb.repository.PhotoRepository;
import com.breezybnb.repository.UserRepository;
import com.breezybnb.service.TestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class BreezyBnBApplication {

	public static void main(String[] args) {
		SpringApplication.run(BreezyBnBApplication.class, args);
	}


	/*
	@Bean
	public CommandLineRunner demo(UserRepository userRepository, PhotoRepository photoRepository, TestService testService) {
		return args -> {

			testService.populateAcmdtypes();
			//testService.populateInitialData();
			//photoRepository.deleteById(2L);
			//testService.testFunction();
			//testService.delFunction();

			//photoRepository.save(p1);
			//testService.deletePhotoById(1L);
			//photo.setPhoto("dnjdsn");
			// Create and save a user
			//User user = new Admin(); // or new Customer(), etc.
			//user.setUsername("testuser");
			//user.setEmail("test@example.com");
			//user.setPassword("secret");
			//user.setGender(Gender.MALE);
			//user.setName("Test");
			//user.setSurname("User");
			//user.setDateOfBirth(LocalDate.of(1990, 1, 1));
			//user.setRegistered(LocalDateTime.now());

			// Assign photo
			//Photo photo = new Photo("test");
			//user.assignPhoto(photo);

			// Save to DB
			//photoRepository.save(photo);

			// Fetch and print
			//System.out.println("Saved user: " + userRepository.findById(user.getId()).orElseThrow());

			// Delete example
			// userRepository.delete(user);
		};
	}
	//*/

}
