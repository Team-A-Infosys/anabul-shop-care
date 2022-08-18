package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.dto.request.AddressRequest;
import team.kucing.anabulshopcare.dto.request.PasswordRequest;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.request.UpdateUserRequest;
import team.kucing.anabulshopcare.dto.response.UserResponse;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.subaddress.*;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.*;
import team.kucing.anabulshopcare.repository.subrepo.*;
import team.kucing.anabulshopcare.service.UserAppService;
import team.kucing.anabulshopcare.service.uploadimg.UserAvatarService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;

@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class UserAppServiceImpl implements UserAppService {

    private UserAppRepository userRepo;

    private AddressRepository addressRepository;

    private ProvinsiRepository provinsiRepository;

    private KotaRepository kotaRepository;

    private KecamatanRepository kecamatanRepository;

    private KelurahanRepository kelurahanRepository;

    private RoleRepository roleRepo;

    private PasswordEncoder passwordEncoder;

    private final UserAvatarService fileStorageService;

    @Override
    public ResponseEntity<Object> signUpSeller(SignupRequest newUser, MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_SELLER");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public ResponseEntity<Object> signUpBuyer(SignupRequest newUser, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_BUYER");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public ResponseEntity<Object> signUpAdmin(SignupRequest newUser, MultipartFile file) {

        String fileName = fileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
        Role getRole = this.roleRepo.findByName("ROLE_ADMIN");

        return saveUser(newUser, fileDownloadUri, getRole);
    }

    @Override
    public ResponseEntity<Object> deactivateAccount(Principal principal) {
        UserApp userApp = this.userRepo.findByEmail(principal.getName());
        userRepo.delete(userApp);

        log.info("Deactivate account process has been successful");
        return ResponseHandler.generateResponse("Success deactivate account", HttpStatus.OK, null);
    }

    @Override
    public ResponseEntity<Object> getUser(Principal principal){
       UserApp getUserApp = this.userRepo.findByEmail(principal.getName());

        Role seller = this.roleRepo.findByName("ROLE_SELLER");
        Collection<Role> roles = this.roleRepo.findAll();

        if (getUserApp.getRoles().containsAll(roles)) {
            log.info("Success get user " + getUserApp.getEmail());
            return ResponseHandler.generateResponse("Success get user", HttpStatus.OK, getUserApp.convertToBuyerResponse());
        }

        if (getUserApp.getRoles().contains(seller)){
            log.info("Success get user " + getUserApp.getEmail());
            return ResponseHandler.generateResponse("Success get user", HttpStatus.OK, getUserApp.convertToSellerResponse());
        }

        log.info("Success get user " + getUserApp.getEmail());
        return ResponseHandler.generateResponse("Success get user", HttpStatus.OK, getUserApp.convertToResponse());
    }

    private ResponseEntity<Object> saveUser(SignupRequest request, String fileDownloadUri, Role getRole) {
        UserApp newUser = new UserApp();
        newUser.setImageUrl(fileDownloadUri);
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setRoles(Collections.singleton(getRole));

        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())){
            throw new BadRequestException("Password is not match, try again");
        }
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (this.userRepo.existsByEmail(request.getEmail())){
            log.error("Email already registered, try login. Error: " + HttpStatus.BAD_REQUEST);
            throw new BadRequestException("Email already registered, try login");
        }
        newUser.setEmail(request.getEmail());

        if (this.userRepo.existsByPhoneNumber(request.getPhoneNumber())){
            log.error("Phone number already registered, try login. Error: " + HttpStatus.BAD_REQUEST);
            throw new BadRequestException("Phone number already registered");
        }
        newUser.setPhoneNumber(request.getPhoneNumber());
        this.userRepo.save(newUser);

        if (newUser.getAddress() == null){
            Address newAddress = new Address();
            newAddress.setId(1L);
            newAddress.setProvinsi(request.getAddress().getProvinsi());
            newAddress.setKota(request.getAddress().getKota());
            newAddress.setKecamatan(request.getAddress().getKecamatan());
            newAddress.setKelurahan(request.getAddress().getKelurahan());
            Address saveAddress = this.addressRepository.save(newAddress);

            newUser.setAddress(saveAddress);
            UserApp savedUser = this.userRepo.save(newUser);

            UserApp searchUser = this.userRepo.findByEmail(request.getEmail());
            searchUser.getAddress().setUserApp(savedUser);
            this.userRepo.save(searchUser);
        }

        UserResponse response = newUser.convertToResponse();
        log.info("Success create user: " + response.toString());
        return ResponseHandler.generateResponse("Success Create User", HttpStatus.CREATED, response);
    }

    @Override
    public ResponseEntity<Object> updateUser(UpdateUserRequest user, MultipartFile file, Principal principal) {
        UserApp userUpdate = this.userRepo.findByEmail(principal.getName());
        if (user.getFirstName() != null) {
            userUpdate.setFirstName(user.getFirstName());
            this.userRepo.save(userUpdate);
            log.info("Success to Update First Name of User with ID : " + userUpdate.getId()+ " into " + user.getFirstName());
        }
        if (user.getLastName()!= null) {
            userUpdate.setLastName(user.getLastName());
            this.userRepo.save(userUpdate);
            log.info("Success to Update Last Name of User with ID : " + userUpdate.getId()+ " into " + user.getLastName());
        }
        if (user.getEmail()!= null) {
            if (this.userRepo.existsByEmail(user.getEmail())) {
                log.error("Cannot use this Email " + user.getEmail() + " try another Email");
                throw new BadRequestException("Cannot use this Email " + user.getEmail()+ " try another Email");
            }
            userUpdate.setEmail(user.getEmail());
            this.userRepo.save(userUpdate);
            log.info("Success to Update Email of User with ID : " + userUpdate.getId()+ " into " + user.getEmail());
        }
        if (user.getAddress() != null){
            this.updateAddressUser(user.getAddress(), principal);
        }
        if (user.getPhoneNumber()!= null) {
            if (this.userRepo.existsByPhoneNumber(user.getPhoneNumber())) {
                log.error("Cannot use this Phone Number " + user.getPhoneNumber() + " try another Phone Number");
                throw new BadRequestException("Cannot use this Phone Number " + user.getPhoneNumber()+ " try another Phone Number");
            }
            userUpdate.setPhoneNumber(user.getPhoneNumber());
            this.userRepo.save(userUpdate);
            log.info("Success to Update Phone Number of User with ID : " + userUpdate.getId()+ " into " + user.getPhoneNumber());
        }
        if (!(file.isEmpty())){
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(fileName).toUriString();
            userUpdate.setImageUrl(fileDownloadUri);
            this.userRepo.save(userUpdate);
            log.info("Success to Update Image of User with ID : " + userUpdate.getId() + " into " + fileDownloadUri);
        }

        UserResponse response = userUpdate.convertToResponse();
        log.info("Success update User " + response.toString());
        return ResponseHandler.generateResponse("Success Update The User", HttpStatus.OK, response);
    }

    @Override
    public ResponseEntity<Object> updatePasswordUser(PasswordRequest passwordRequest, Principal principal){
        UserApp userApp = this.userRepo.findByEmail(principal.getName());

        if (!Objects.equals(passwordRequest.getPassword(), passwordRequest.getConfirmPassword())){
            throw new BadRequestException("Password is not match, try again");
        }

        if (passwordEncoder.matches(passwordRequest.getPassword(), userApp.getPassword())){
            throw new BadRequestException("Password cannot be the same as the previous password");
        }

        userApp.setPassword(passwordEncoder.encode(passwordRequest.getPassword()));
        this.userRepo.save(userApp);

        log.info("Success change the password of user: " + userApp.convertToResponse());
        return ResponseHandler.generateResponse("Success change the password", HttpStatus.OK, userApp.convertToResponse());
    }

    private void updateAddressUser(AddressRequest addressRequest, Principal principal){
        UserApp updateAddressUser = this.userRepo.findByEmail(principal.getName());
        Optional<Provinsi> findProvinsi = this.provinsiRepository.findById(addressRequest.getProvinsi().getId());
        if (findProvinsi.isEmpty()){
            throw new ResourceNotFoundException("Provinsi is not found");
        }
        Provinsi getProvinsi = findProvinsi.get();
        updateAddressUser.getAddress().setProvinsi(getProvinsi);

        Optional<Kota> findKota = this.kotaRepository.findById(addressRequest.getKota().getId());
        if (findKota.isEmpty()){
            throw new ResourceNotFoundException("Kota is not found");
        }
        Kota getKota = findKota.get();
        updateAddressUser.getAddress().setKota(getKota);

        Optional<Kecamatan> findKecamatan = this.kecamatanRepository.findById(addressRequest.getKecamatan().getId());
        if (findKecamatan.isEmpty()){
            throw new ResourceNotFoundException("Kecamatan is not found");
        }
        Kecamatan getKecamatan = findKecamatan.get();
        updateAddressUser.getAddress().setKecamatan(getKecamatan);

        Optional<Kelurahan> findKelurahan = this.kelurahanRepository.findById(addressRequest.getKelurahan().getId());
        if (findKelurahan.isEmpty()){
            throw new ResourceNotFoundException("Kelurahan is not found");
        }
        Kelurahan getKelurahan = findKelurahan.get();
        updateAddressUser.getAddress().setKelurahan(getKelurahan);

        this.userRepo.save(updateAddressUser);
    }
}