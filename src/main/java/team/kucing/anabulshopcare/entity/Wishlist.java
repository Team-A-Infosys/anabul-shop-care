package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import team.kucing.anabulshopcare.dto.response.WishlistResponse;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wishlistId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserApp userApp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public WishlistResponse convertToResponse(){
        return WishlistResponse.builder()
                .productName(this.product.getName())
                .description(this.product.getDescription())
                .category(this.product.getCategory().convertResponseCategory())
                .location(this.product.getLocation())
                .price(this.product.getPrice())
                .stock(this.product.getStock())
                .userApp(this.product.getUserApp().getFirstName() + this.product.getUserApp().getLastName())
        .build();
    }
}
