package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.entity.Address;
import team.kucing.anabulshopcare.service.impl.AddressServiceImpl;

@RestController
@AllArgsConstructor
public class AddressController {

    private AddressServiceImpl addressService;

    @PostMapping("/address/create")
    public ResponseEntity<Object> createAddress(@RequestBody Address address){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.addressService.createNewAddress(address));
    }
}