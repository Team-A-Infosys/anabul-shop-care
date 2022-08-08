package team.kucing.anabulshopcare.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.ProductCartResponse;
import team.kucing.anabulshopcare.dto.response.ProductResponse;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE product SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Product {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(min=5, message = "Product name must be atleast 5 character")
    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="category_id")
    private Category category;

    private String location;

    private Integer stock;

    private double price;

    private String imageUrl;

    @OneToOne
    private UserApp userApp;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Wishlist> wishlist;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Cart> cart;

    private Boolean isPublished = Boolean.FALSE;

    private Boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public ProductResponse convertToResponse(){
        return ProductResponse.builder()
                .productName(this.name)
                .firstName(this.userApp.getFirstName())
                .description(this.description)
                .category(this.category.getCategoryName())
                .stock(this.stock)
                .price(this.price)
                .imageProduct(this.imageUrl)
                .location(this.userApp.getAddress().getKota().getNama())
                .wishlistByUser(this.wishlist)
                .cartByUser(String.valueOf(this.cart.stream().map(Cart::getUserAppC).map(UserApp::getEmail).count()))
                .build();
    }

    public ProductCartResponse responseCart(){
        return ProductCartResponse.builder()
                .productName(this.name)
                .price(this.price).build();
        }
}