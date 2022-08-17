package team.kucing.anabulshopcare.dto.response;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    public JwtResponse(String accessToken, String firstName, String lastName, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}