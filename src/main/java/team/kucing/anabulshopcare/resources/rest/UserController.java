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

import javax.validation.Valid;
import java.util.UUID;

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

    @PutMapping(value = "/signup/update/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid UserApp userApp) {
        return this.userAppService.updateUser(userApp, file, id);
    }
}