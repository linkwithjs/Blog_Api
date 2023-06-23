package com.rj.blog.service;


import com.rj.blog.dto.RegisterRequest;
import com.rj.blog.dto.ResponseDTO;
import com.rj.blog.dto.UserResponse;
import com.rj.blog.exceptions.CustomException;
import com.rj.blog.models.Role;
import com.rj.blog.models.User;
import com.rj.blog.repository.RoleRepository;
import com.rj.blog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserManageService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManageService.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    /**
     * Method to create new user
     *
     * @param registerRequest user detail from client
     * @return success message or error message
     */
    public ResponseEntity<?> create(RegisterRequest registerRequest) {
        //Check Username and Email is already present or not
        if (userRepository.existsByUsername(registerRequest.getUsername().trim())) {
            if (userRepository.existsByEmail(registerRequest.getEmail().trim())) {
                return ResponseDTO.errorResponse("Error: Email is already taken!");
            }
            return ResponseDTO.errorResponse("Error: Username is already taken!");
        }
        // Create new user's account
        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()));
        Set<String> strRoles = registerRequest.getRole();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            String roleName = role.trim().toUpperCase();
            Role verifiedRole = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new CustomException("Error: " + roleName + " is not found.", 404));
            roles.add(verifiedRole);
        });
        user.setRoles(roles);
        userRepository.save(user);
        LOGGER.info("{} Created new user {}", readAuthenticatedUserName(), user.getUsername());
        return ResponseDTO.successResponse("User created successfully!", null);
    }

    public String readAuthenticatedUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String user = "";
        if (principal instanceof UserDetails) {
            user = ((UserDetails) principal).getUsername();
        }
        return user;
    }

    public ResponseEntity<?> readAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail(), user.getRoles()))
                .collect(Collectors.toList());
        return ResponseDTO.successResponse("Users fetched successfully!", users);
    }


}
