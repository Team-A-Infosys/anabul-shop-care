package team.kucing.anabulshopcare.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WishlistRequest {
    private UUID productId;

    private String emailUser;
}
