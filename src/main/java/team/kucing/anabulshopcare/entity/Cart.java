package team.kucing.anabulshopcare.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.dto.response.CartResponse;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE cart SET is_deleted = true WHERE cart_id=?")
@Where(clause = "is_deleted = false")
public class Cart {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private UserApp userAppC;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Product productC;

    private Integer quantity;

    private Double sub_total;

    private boolean isDeleted;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public CartResponse convertToResponse(){
        return CartResponse.builder()
                .emailUser(this.userAppC.getEmail())
                .product(this.productC.responseCart())
                .quantity(this.getQuantity())
                .subTotal(this.getSub_total())
        .build();
    }
}
