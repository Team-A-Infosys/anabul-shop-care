package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.LoginRequest;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import team.kucing.anabulshopcare.service.impl.AuthServiceImpl;
import team.kucing.anabulshopcare.service.impl.UserAppServiceImpl;

import javax.validation.Valid;

import java.io.File;
import java.security.Principal;
import java.util.UUID;


@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "01. User Controller")
@OpenAPIDefinition(info = @Info(title = "Anabul Shop & Care Documentation",
        description = "API Documentation of e-Commerce Anabul Shop & Care", version = "v1", license = @License(name ="Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
public class UserController {
    @Autowired
    AuthServiceImpl authService;
    private final UserAppServiceImpl userAppService;

    @GetMapping("/user")
    @Operation(summary = "Get User By User Id [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> getUser(Principal principal){
        return this.userAppService.getUser(principal);
    }

    @PostMapping(value = "/signup/seller", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupSeller(@RequestPart MultipartFile file, @RequestPart SignupRequest userApp){
        return this.userAppService.signUpSeller(userApp, file);
    }

    @PostMapping(value = "/signup/buyer", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> signupBuyer(@RequestPart MultipartFile file, @RequestPart SignupRequest userApp){
        return this.userAppService.signUpBuyer(userApp, file);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticate(@Valid @RequestBody LoginRequest loginRequest)  {
        return authService.authenticateUser(loginRequest);
    }

    @PutMapping(value = "/user/update", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update User [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> updateUser(@RequestPart MultipartFile file, @RequestPart @Valid UpdateUserRequest user, Principal principal) {
        return this.userAppService.updateUser(user, file, principal);
    }

    @PutMapping("/user/changePassword")
    @Operation(summary = "Change Password [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> changePassword(@RequestBody PasswordRequest passwordRequest, Principal principal){
        return this.userAppService.updatePasswordUser(passwordRequest, principal);
    }

    @DeleteMapping("/user/deactivate")
    @Operation(summary = "Deactivate Accout [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deactivateAccount(Principal principal) {
        return userAppService.deactivateAccount(principal);
    }
}