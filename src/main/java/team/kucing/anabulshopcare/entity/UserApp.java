package team.kucing.anabulshopcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.*;
import team.kucing.anabulshopcare.entity.image.ImageProduct;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.FetchType.EAGER;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user_app SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Entity
public class UserApp extends ImageProduct {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

    private String imageUrl;

    private String password;

    @ManyToMany(fetch = EAGER, cascade = CascadeType.PERSIST)
    private Collection<Role> roles = new ArrayList<>();

    private String history;

    private boolean isDeleted = Boolean.FALSE;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}