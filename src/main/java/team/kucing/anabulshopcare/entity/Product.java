package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.ProductResponse;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
//TODO :Remove Data Annotation Lombok

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
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
                .location(this.userApp.getAddress().getKota().getNama()).build();
    }
}