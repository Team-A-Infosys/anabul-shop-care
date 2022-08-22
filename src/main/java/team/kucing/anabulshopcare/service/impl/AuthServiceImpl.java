package team.kucing.anabulshopcare.service.impl;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import team.kucing.anabulshopcare.dto.request.LoginRequest;
import team.kucing.anabulshopcare.dto.response.JwtResponse;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.RoleRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.security.UserAppDetailsImpl;
import team.kucing.anabulshopcare.security.config.JwtUtils;
import team.kucing.anabulshopcare.service.AuthService;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserAppRepository userAppRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Value("${com.app.domain}")
    String domain;

    @Value("${server.port}")
    String port;

    @Value("${com.app.name}")
    String projectName;

    @Value("${com.app.team}")
    String projectTeam;

    @Override
    public ResponseEntity<Object> authenticateUser(LoginRequest loginRequest) {
        UserApp user = this.userAppRepository.findByEmail(loginRequest.getEmail());

        if(user == null){
            log.error("Username or password is wrong!");
            throw new ResourceNotFoundException("Username or password is wrong!");
        }

        if (Boolean.FALSE.equals(userAppRepository.existsByEmail(loginRequest.getEmail()))) {
            log.error("Username or password is wrong!");
            throw new ResourceNotFoundException("Username or password is wrong!");
        }

        Boolean isPasswordCorrect = encoder.matches(loginRequest.getPassword(), user.getPassword());
        if (Boolean.FALSE.equals(isPasswordCorrect)) {
            log.error("Username or password is wrong!");
            throw new ResourceNotFoundException("Username or password is wrong!");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserAppDetailsImpl userDetails = (UserAppDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseHandler.generateResponse("you are logged in", HttpStatus.OK, new JwtResponse(jwt, userDetails.getUserid(), userDetails.getFirstname(), userDetails.getLastname(),userDetails.getUsername(), roles));
    }
}