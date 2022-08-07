package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import java.util.UUID;

public interface UserAppService {

    ResponseEntity<Object> signUpSeller(SignupRequest user, MultipartFile file);

    ResponseEntity<Object> signUpBuyer(SignupRequest user, MultipartFile file);

    ResponseEntity<Object> deactivateAccount(UUID id);

    ResponseEntity<Object> getAllUsers(Pageable pageable);

    ResponseEntity<Object> updateUser(UpdateUserRequest user, MultipartFile file, UUID id);

    ResponseEntity<Object> updatePasswordUser(PasswordRequest passwordRequest, UUID id);
}