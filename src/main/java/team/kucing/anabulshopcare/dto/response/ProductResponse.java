package team.kucing.anabulshopcare.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}