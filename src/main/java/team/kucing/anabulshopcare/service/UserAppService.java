package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.entity.UserApp;

public interface UserAppService {

    ResponseEntity<Object> signUpSeller(UserApp user, MultipartFile file);

    ResponseEntity<Object> signUpBuyer(UserApp user, MultipartFile file);

}