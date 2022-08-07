package team.kucing.anabulshopcare.dto.response;

import lombok.*;
import team.kucing.anabulshopcare.entity.Role;
import team.kucing.anabulshopcare.entity.Wishlist;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String phoneNumber;

    private String address;

    private String history;

    private List<Wishlist> wishlistProduct;

    private Collection<Role> roles;

}