package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import team.kucing.anabulshopcare.service.impl.UserAppServiceImpl;

import javax.validation.Valid;

import java.io.File;
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

    @PostMapping(value = "/signup/seller", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupSeller(@RequestPart MultipartFile file, @RequestPart SignupRequest userApp){
        return this.userAppService.signUpSeller(userApp, file);
    }

    @PostMapping(value = "/signup/buyer", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupBuyer(@RequestPart MultipartFile file, @RequestPart SignupRequest userApp){
        return this.userAppService.signUpBuyer(userApp, file);
    }

    @PutMapping(value = "/user/{id}/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid UpdateUserRequest user) {
        return this.userAppService.updateUser(user, file, id);
    }

    @PutMapping("/user/{id}/changePassword")
    public ResponseEntity<Object> changePassword(@PathVariable(value = "id") UUID id, PasswordRequest passwordRequest){
        return this.userAppService.updatePasswordUser(passwordRequest, id);
    }

    @DeleteMapping("/user/{id}/deactivate")
    public ResponseEntity<Object> deactivateAccount(@PathVariable UUID id) {
        return userAppService.deactivateAccount(id);
    }
}