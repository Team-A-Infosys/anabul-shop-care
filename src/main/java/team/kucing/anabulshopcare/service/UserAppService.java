package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupNonSellerRequest;
import team.kucing.anabulshopcare.dto.request.SignupSellerRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;

import java.security.Principal;

public interface UserAppService {

    ResponseEntity<Object> signUpSeller(SignupSellerRequest user, MultipartFile file);

    ResponseEntity<Object> signUpBuyer(SignupNonSellerRequest user, MultipartFile file);

    ResponseEntity<Object> signUpAdmin(SignupNonSellerRequest newUser, MultipartFile file);

    ResponseEntity<Object> deactivateAccount(Principal principal);

    ResponseEntity<Object> getUser(Principal principal);

    ResponseEntity<Object> updateUser(UpdateUserRequest user, MultipartFile file, Principal principal);

    ResponseEntity<Object> updatePasswordUser(PasswordRequest passwordRequest, Principal principal);
}