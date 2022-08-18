package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;

import java.security.Principal;
import java.util.UUID;

public interface UserAppService {

    ResponseEntity<Object> signUpSeller(SignupRequest user, MultipartFile file);

    ResponseEntity<Object> signUpBuyer(SignupRequest user, MultipartFile file);

    ResponseEntity<Object> signUpAdmin(SignupRequest newUser, MultipartFile file);

    ResponseEntity<Object> deactivateAccount(Principal principal);

    ResponseEntity<Object> getUser(Principal principal);

    ResponseEntity<Object> updateUser(UpdateUserRequest user, MultipartFile file, Principal principal);

    ResponseEntity<Object> updatePasswordUser(PasswordRequest passwordRequest, Principal principal);
}