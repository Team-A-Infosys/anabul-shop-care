package team.kucing.anabulshopcare.dto.response;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String accessToken;

    private String type = "Bearer";

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    public JwtResponse(String accessToken,UUID userId, String firstName, String lastName, String email, List<String> roles) {
        this.accessToken = accessToken;
        this.userId=userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}