package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.kucing.anabulshopcare.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;

    private String description;

    private CategoryRequest category;

    private String location;

    private Integer stock;

    private double price;

    private String createdBy;

}