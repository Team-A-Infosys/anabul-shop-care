package team.kucing.anabulshopcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessSignUp {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String history;
}