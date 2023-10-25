package org.teamseven.hms.backend.user.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.teamseven.hms.backend.shared.exception.ResourceNotFoundException;
import org.teamseven.hms.backend.user.UserRequest;
import org.teamseven.hms.backend.user.User;
import org.teamseven.hms.backend.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.teamseven.hms.backend.user.entity.Patient;
import org.teamseven.hms.backend.user.entity.PatientRepository;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private PatientRepository patientRepository;

    public User getUserProfile(HttpServletRequest request) {
        var email = request.getAttribute("email");
        User user = userRepository.findByEmail((String) email);
        if(user == null) {
            throw new ResourceNotFoundException("User not found!");
        }
        return user;
    }

    public Patient updateUserProfile(
            HttpServletRequest request,
            UserRequest userRequest
    ) {
        User user = this.getUserProfile(request);
        Patient patient = patientRepository.findByUser(user);

        if (userRequest.getFirstName() != null && !userRequest.getFirstName().isEmpty()) {
            user.setFirstName(userRequest.getFirstName());
        }

        if (userRequest.getLastName() != null && !userRequest.getLastName().isEmpty()) {
            user.setLastName(userRequest.getLastName());
        }

        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getGender() != null && !userRequest.getGender().isEmpty()) {
            user.setGender(userRequest.getGender());
        }

        if (userRequest.getEmail() != null && !userRequest.getEmail().isEmpty()) {
            user.setEmail(userRequest.getEmail());
        }

        if (userRequest.getNric() != null && !userRequest.getNric().isEmpty()) {
            user.setNric(userRequest.getNric());
        }

        if (userRequest.getAddress() != null && !userRequest.getAddress().isEmpty()) {
            user.setAddress(userRequest.getAddress());
        }

        if (userRequest.getDateOfBirth() != null && !userRequest.getDateOfBirth().isEmpty()) {
            user.setDateOfBirth(userRequest.getDateOfBirth());
        }

        if (userRequest.getPhone() != null && !userRequest.getPhone().isEmpty()) {
            user.setPhone(userRequest.getPhone());
        }

        if (userRequest.getBloodGroup() != null && !userRequest.getBloodGroup().isEmpty()) {
            patient.setBloodGroup(userRequest.getBloodGroup());
        }

        if (userRequest.getMedicalConditions() != null && !userRequest.getMedicalConditions().isEmpty()) {
            patient.setMedicalCondition(userRequest.getMedicalConditions());
        }

        userRepository.save(user);
        patientRepository.save(patient);
        return patient;
    }
}
