package team.kucing.anabulshopcare.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.UserApp;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.UUID;

public interface UserAppService {

    ResponseEntity<Object> signUpSeller(UserApp user, MultipartFile file);

    ResponseEntity<Object> signUpBuyer(UserApp user, MultipartFile file);

    UserApp findById(UUID id);

    ResponseEntity<Object> deleteAccount(UUID id);

    ResponseEntity<Object> filterUserByIsDeleted(UUID id);


    ResponseEntity<Object> getAllUsers(Pageable pageable);

}