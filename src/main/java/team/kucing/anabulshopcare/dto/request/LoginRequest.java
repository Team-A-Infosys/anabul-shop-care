package team.kucing.anabulshopcare.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}