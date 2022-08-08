package team.kucing.anabulshopcare.dto.response;

import lombok.*;
import team.kucing.anabulshopcare.entity.Cart;
import team.kucing.anabulshopcare.entity.Wishlist;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {
    private String productName;

    private String description;

    private String category;

    private String firstName;

    private String imageProduct;

    private Integer stock;

    private double price;

    private String location;

    private List<Wishlist> wishlistByUser;

    private String cartByUser;
}