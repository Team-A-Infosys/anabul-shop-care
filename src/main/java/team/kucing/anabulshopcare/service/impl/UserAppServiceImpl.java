package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.dto.request.SignupRequest;
import team.kucing.anabulshopcare.dto.response.SuccessSignUp;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.AddressRepository;
import team.kucing.anabulshopcare.repository.RoleRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.UserAppService;
import team.kucing.anabulshopcare.service.UserAvatarService;

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


    private ResponseEntity<Object> saveUser(UserApp user, String fileDownloadUri, Role getRole) {

        user.setImageUrl(fileDownloadUri);
        user.setRoles(Collections.singleton(getRole));

        if (this.userRepo.existsByEmail(user.getEmail())){
            throw new ResourceNotFoundException("Email has been taken");
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

        SuccessSignUp response = new SuccessSignUp();
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setHistory(user.getHistory());

        StringBuilder address = new StringBuilder();
        address.append(user.getAddress().getProvinsi().getNama());
        address.append(", ");
        address.append(user.getAddress().getKota().getNama());
        address.append(", ");
        address.append(user.getAddress().getKecamatan().getNama());
        address.append(", ");
        address.append(user.getAddress().getKelurahan().getNama());

        response.setAddress(address.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}