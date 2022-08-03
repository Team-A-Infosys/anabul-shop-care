package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.service.impl.UserAppServiceImpl;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class UserController {
    private final UserAppServiceImpl userAppService;

    @PostMapping(value = "/signup/seller", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupSeller(@RequestPart MultipartFile file, @RequestPart UserApp userApp){
        return this.userAppService.signUpSeller(userApp, file);
    }

    @PostMapping(value = "/signup/buyer", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupBuyer(@RequestPart MultipartFile file, @RequestPart UserApp userApp){
        return this.userAppService.signUpBuyer(userApp, file);
    }

    @DeleteMapping("/deactive/account/{id}")
    public ResponseEntity<Object> deactivateAccount(@PathVariable(value = "id") UUID id){
        return this.userAppService.deactiveAccount(id);
    }
}