package team.kucing.anabulshopcare.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import team.kucing.anabulshopcare.dto.response.CartResponse;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserApp userApp;

    @ManyToMany
    @JsonIgnore
    private List<Product> product;

    private int quantity;

    private double subTotal;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public CartResponse convertToResponse(){
        return CartResponse.builder()
                .productName(this.product.stream().map(Product::getName).collect(Collectors.joining("")))
                .imageProduct(this.product.stream().map(Product::getImageUrl).collect(Collectors.joining("")))
                .description(this.product.stream().map(Product::getDescription).collect(Collectors.joining("")))
                .category(this.product.stream().map(Product::getCategory).map(Category::getCategoryName).collect(Collectors.joining("")))
                .quantity(this.quantity)
                .subTotal(this.subTotal).build();
    }
}