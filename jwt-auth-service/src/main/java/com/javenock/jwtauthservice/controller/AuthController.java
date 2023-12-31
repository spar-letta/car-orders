package com.javenock.jwtauthservice.controller;

import com.javenock.jwtauthservice.model.ERole;
import com.javenock.jwtauthservice.model.Role;
import com.javenock.jwtauthservice.model.User;
import com.javenock.jwtauthservice.repository.RoleRepository;
import com.javenock.jwtauthservice.repository.UserRepository;
import com.javenock.jwtauthservice.request.LoginRequest;
import com.javenock.jwtauthservice.request.SignupRequest;
import com.javenock.jwtauthservice.response.JwtResponse;
import com.javenock.jwtauthservice.response.MessageResponse;
import com.javenock.jwtauthservice.security.JwtUtils;
import com.javenock.jwtauthservice.service.UserDetailsImpl;
import com.javenock.jwtauthservice.service.UserDetailsServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    AuthenticationManager authenticationManager;


    UserRepository userRepository;


    RoleRepository roleRepository;


    PasswordEncoder encoder;


    JwtUtils jwtUtils;

    UserDetailsServiceImpl userDetailsServiceImpl;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils,
                          UserDetailsServiceImpl userDetailsServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(userDetails.getId(),userDetails.getUsername(),jwt,roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .build();
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userCustomerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userCustomerRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "sale_vp":
                        Role salevpRole = roleRepository.findByName(ERole.ROLE_VP_SALES)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(salevpRole);

                        break;
                    case "vp_marketing":
                        Role vpmarketingRole = roleRepository.findByName(ERole.ROLE_VP_MARKETING)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(vpmarketingRole);

                        break;

                    case "sales_rep":
                        Role salesrepRole = roleRepository.findByName(ERole.ROLE_SALES_REP)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(salesrepRole);

                        break;

                    default:
                        Role customerRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found." + strRoles));
                        roles.add(customerRole);

                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("registered successfully!"));
    }

    @GetMapping("/validate")
    public boolean detValidate(@RequestParam String token){
        return jwtUtils.validateJwtToken(token);
    }

}
