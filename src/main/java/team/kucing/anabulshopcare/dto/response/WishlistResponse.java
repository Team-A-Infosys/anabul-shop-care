package team.kucing.anabulshopcare.dto.response;

import lombok.*;
import team.kucing.anabulshopcare.entity.Category;
import team.kucing.anabulshopcare.entity.UserApp;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WishlistResponse {

    private String productName;

    private CategoryResponse category;

    private String userApp;

    private String description;

    private double price;

    private Integer stock;

    private String location;

}
