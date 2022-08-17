package team.kucing.anabulshopcare.resources.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.dto.request.LoginRequest;
import team.kucing.anabulshopcare.service.impl.AuthServiceImpl;

import javax.validation.Valid;

@RestController
public class LoginController {

    @Autowired
    AuthServiceImpl authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@Valid @RequestBody LoginRequest loginRequest)  {
        return authService.authenticateUser(loginRequest);
    }
}