package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.impl.UserAppServiceImpl;

import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserAppServiceImpl userAppService;
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(Pageable pageable){
        return this.userAppService.getAllUsers(pageable);
    }

    @PostMapping(value = "/signup/seller", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> signupSeller(@RequestPart MultipartFile file, @RequestPart UserApp userApp){
        return this.userAppService.signUpSeller(userApp, file);
    }

    @PostMapping(value = "/signup/buyer", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupBuyer(@RequestPart MultipartFile file, @RequestPart UserApp userApp){
        return this.userAppService.signUpBuyer(userApp, file);
    }


    @DeleteMapping("/userAccount/delete/{id}")
    public ResponseEntity<Object> deleteAccount(@PathVariable UUID id) {
        UserApp userApp = this.userAppService.findById(id);

        if (userApp == null) {
            log.info("account with id : " + "deletion process has been successful");
        } else {
            log.info("process failed, account not registered with id : " + id + "!!!");
        }
        return userAppService.deleteAccount(id);

    }


}