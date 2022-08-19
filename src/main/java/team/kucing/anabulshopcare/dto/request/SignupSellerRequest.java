package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupSellerRequest {

    private String sellerName;

    private String storeName;

    private String email;

    private String phoneNumber;

    private String password;

    private String confirmPassword;

    private AddressRequest address;
}