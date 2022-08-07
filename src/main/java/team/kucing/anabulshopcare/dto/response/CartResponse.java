package team.kucing.anabulshopcare.dto.response;

import lombok.*;
import team.kucing.anabulshopcare.entity.Product;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {

    private String productName;

    private String imageProduct;

    private String category;

    private String description;

    private int quantity;

    private double subTotal;

}