package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SQLDelete(sql = "UPDATE coupon SET is_deleted = true WHERE checkout = totalCoupon")
@Where(clause = "is_deleted = false")
public class Coupon {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String code;

    private double value;

    private boolean isDeleted = Boolean.FALSE;

    private Long totalCoupon;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date CreatedAt;

    @UpdateTimestamp
    private Date updatedAt;

}
