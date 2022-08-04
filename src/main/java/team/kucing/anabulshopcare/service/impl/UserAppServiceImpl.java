package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.AddressRepository;
import team.kucing.anabulshopcare.repository.RoleRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.UserAppService;
import team.kucing.anabulshopcare.service.uploadimg.UserAvatarService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserAppServiceImpl implements UserAppService {

    private UserAppRepository userRepo;

    private AddressRepository addressRepository;

    private RoleRepository roleRepo;

    private final UserAvatarService fileStorageService;

    @Override
    public ResponseEntity<Object> signUpSeller(UserApp newUser, MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_SELLER");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public ResponseEntity<Object> signUpBuyer(UserApp newUser, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_BUYER");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public ResponseEntity<Object> deleteAccount(UUID id) {
        Optional<UserApp> optionalUserApp = userRepo.findById(id);

        if(optionalUserApp.isEmpty()){
            log.error("process failed, account not registered");
            throw new ResourceNotFoundException("process failed, account not registered");
        }

        UserApp userApp = userRepo.getReferenceById(id);
        userRepo.delete(userApp);

        log.info("account deletion process has been successful");
        return ResponseEntity.ok().body("account deletion process has been successful");
    }
    public ResponseEntity<Object> getAllUsers(Pageable pageable){
        Page<UserApp> userApp = this.userRepo.findAll(pageable);
        List<UserResponse> response = userApp.stream().map(UserApp::convertToResponse).toList();

        return ResponseEntity.ok().body(response);
    }

    private ResponseEntity<Object> saveUser(UserApp user, String fileDownloadUri, Role getRole) {

        user.setImageUrl(fileDownloadUri);
        user.setRoles(Collections.singleton(getRole));

        if (this.userRepo.existsByEmail(user.getEmail())){
            throw new BadRequestException("Email already registered, try login");
        }

        if (this.userRepo.existsByPhoneNumber(user.getPhoneNumber())){
            throw new BadRequestException("Phone number already registered");
        }

        if (user.getAddress().getId() == null){
            Address newAddress = new Address();
            newAddress.setId(1L);
            newAddress.setProvinsi(user.getAddress().getProvinsi());
            newAddress.setKota(user.getAddress().getKota());
            newAddress.setKecamatan(user.getAddress().getKecamatan());
            newAddress.setKelurahan(user.getAddress().getKelurahan());
            Address saveAddress = this.addressRepository.save(newAddress);

            user.setAddress(saveAddress);
            UserApp savedUser = this.userRepo.save(user);

            UserApp searchUser = this.userRepo.findByEmail(user.getEmail());
            searchUser.getAddress().setUserApp(savedUser);
            this.userRepo.save(searchUser);
        }

        UserResponse response = user.convertToResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Override
    public UserApp findById(UUID id) {
        Optional<UserApp> optionalUserApp = userRepo.findById(id);

        if(optionalUserApp.isEmpty()){
            throw new ResourceNotFoundException("User with ID "+id+" Is Not Found");
        }
        return optionalUserApp.get();
    }

    @Override
    public ResponseEntity<Object> updateUser(UserApp user, MultipartFile file, UUID id) {
        UserApp userUpdate = this.findById(user.getId());
        if (user.getFirstName() != null) {
            userUpdate.setFirstName(user.getFirstName());
            log.info("Success to Update First Name of User with ID : " + userUpdate.getId()+ " into " + user.getFirstName());
        }
        if (user.getLastName()!= null) {
            userUpdate.setLastName(user.getLastName());
            log.info("Success to Update Last Name of User with ID : " + userUpdate.getId()+ " into " + user.getLastName());
        }
        if (user.getEmail()!= null) {
            if (this.userRepo.existsByEmail(user.getEmail())) {
                log.error("Cannot use this Email " + user.getEmail() + " try another Email");
                throw new BadRequestException("Cannot use this Email " + user.getEmail()+ " try another Email");
            }
            userUpdate.setEmail(user.getEmail());
            log.info("Success to Update Email of User with ID : " + userUpdate.getId()+ " into " + user.getEmail());
        }
        if (user.getPhoneNumber()!= null) {
            if (this.userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
                log.error("Cannot use this Phone Number " + user.getPhoneNumber() + " try another Phone Number");
                throw new BadRequestException("Cannot use this Phone Number " + user.getPhoneNumber()+ " try another Phone Number");
            }
            userUpdate.setPhoneNumber(user.getPhoneNumber());
            log.info("Success to Update Phone Number of User with ID : " + userUpdate.getId()+ " into " + user.getPhoneNumber());
        }
        if (user.getAddress()!= null){
           Optional<Address> address = this.addressRepository.findById(userUpdate.getAddress().getId());
           if (address.isEmpty()) {
                log.error("Address with ID : " + userUpdate.getAddress().getId() + " is not found");
               throw new ResourceNotFoundException("Address with ID "+userUpdate.getAddress().getId()+" is not found");
           } else {
               Address getAddress = address.get();
               getAddress.setProvinsi(user.getAddress().getProvinsi());
               getAddress.setKota(user.getAddress().getKota());
               getAddress.setKecamatan(user.getAddress().getKecamatan());
               getAddress.setKelurahan(user.getAddress().getKelurahan());
               this.addressRepository.save(getAddress);
               log.info("Success to Update Address of User with ID : " + userUpdate.getId()+ " into " + getAddress);
           }

        }
        if (!(file.isEmpty())){
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            userUpdate.setImageUrl(fileDownloadUri);
            log.info("Success to Update Image of User with ID : " + userUpdate.getId() + " into " + fileDownloadUri);
        } else {
            userUpdate.setImageUrl(user.getImageUrl());
        }
        if (user.getPassword()!= null){
            userUpdate.setPassword(user.getPassword());
            log.info("Success to Update Password of User with ID : " + userUpdate.getId());
        }
        UserResponse response = userUpdate.convertToResponse();
        log.info("Success update User " + response.toString());
        return ResponseEntity.ok().body(response);
    }

}