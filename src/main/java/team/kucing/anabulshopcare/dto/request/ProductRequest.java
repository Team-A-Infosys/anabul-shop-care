package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.kucing.anabulshopcare.entity.Product;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;

    private String description;

    private Integer stock;

    private BigDecimal price;

    public Product convertToEntity(){
        return Product.builder()
                .name(this.productName)
                .description(this.description)
                .stock(this.stock)
                .price(this.price)
                .build();
    }
}