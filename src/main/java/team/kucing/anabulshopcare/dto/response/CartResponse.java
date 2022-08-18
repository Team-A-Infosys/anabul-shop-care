package team.kucing.anabulshopcare.dto.response;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private UUID cartId;

    private String productName;

    private String imageProduct;

    private String category;

    private String description;

    private int quantity;

    private double subTotal;

}