package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.kucing.anabulshopcare.entity.UserApp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;

    private String description;

    private CategoryRequest category;

    private UserApp userApp;

    private Integer stock;

    private double price;

    private String location;

}