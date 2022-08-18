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
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "01. User Controller")
@OpenAPIDefinition(info = @Info(title = "Anabul Shop & Care Documentation",
        description = "API Documentation of e-Commerce Anabul Shop & Care", version = "v1", license = @License(name ="Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")))
public class UserController {
    private final UserAppServiceImpl userAppService;
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(@ParameterObject Pageable pageable){
        return this.userAppService.getAllUsers(pageable);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get User By User Id [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> getUser(@PathVariable("id") UUID id){
        return this.userAppService.getUser(id);
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
    @Operation(summary = "Update User [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id, @RequestPart MultipartFile file, @RequestPart @Valid UpdateUserRequest user) {
        return this.userAppService.updateUser(user, file, id);
    }

    @PutMapping("/user/{id}/changePassword")
    @Operation(summary = "Change Password [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> changePassword(@PathVariable(value = "id") UUID id, @RequestBody PasswordRequest passwordRequest){
        return this.userAppService.updatePasswordUser(passwordRequest, id);
    }

    @DeleteMapping("/user/{id}/deactivate")
    @Operation(summary = "Deactivate Accout [SELLER | BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER') or hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deactivateAccount(@PathVariable UUID id) {
        return userAppService.deactivateAccount(id);
    }
}