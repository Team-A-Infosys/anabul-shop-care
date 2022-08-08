package team.kucing.anabulshopcare.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse {

    private String emailUser;

    private ProductCartResponse product;

    private Integer quantity;

    private Double subTotal;

}
