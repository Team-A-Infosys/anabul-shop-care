package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.LoginRequest;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;

public interface AuthService {

    ResponseEntity<?> authenticateUser(LoginRequest loginRequest);
}