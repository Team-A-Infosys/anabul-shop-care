package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.RoleRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.FileStorageService;
import team.kucing.anabulshopcare.service.UserAppService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserAppServiceImpl implements UserAppService {

    private UserAppRepository userRepo;

    private RoleRepository roleRepo;

    private final FileStorageService fileStorageService;

    @Override
    public ResponseEntity<Object> signUpSeller(UserApp user, MultipartFile file) {

        return createUserApp(user, file);
    }

    @Override
    public ResponseEntity<Object> signUpBuyer(UserApp user, MultipartFile file) {
        return createUserApp(user, file);
    }

    public ResponseEntity<Object> deactiveAccount(UUID id){
        Optional<UserApp> optional = this.userRepo.findById(id);

        if (optional.isEmpty()){
            throw new ResourceNotFoundException("User is not found");
        }

        UserApp deactiveAccount = optional.get();
        this.userRepo.delete(deactiveAccount);

        return ResponseEntity.ok().body("Account " + deactiveAccount.getEmail() + " deactivated, Sad to see you go...");
    }

    private ResponseEntity<Object> createUserApp(UserApp user, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        user.setImageUrl(fileDownloadUri);

        Role getRole = this.roleRepo.findByName("ROLE_SELLER");
        user.setRoles(Collections.singleton(getRole));

        if (this.userRepo.existsByEmail(user.getEmail())){
            throw new ResourceNotFoundException("Email has been taken");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(this.userRepo.save(user));
    }
}