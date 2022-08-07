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

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
    @JsonBackReference
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
}