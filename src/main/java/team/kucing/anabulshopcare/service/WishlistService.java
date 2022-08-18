package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;

import java.security.Principal;
import java.util.UUID;

public interface WishlistService {
    ResponseEntity<Object> createWishlist(WishlistRequest wishlistRequest, Principal principal);

    ResponseEntity<Object> deleteWishlist(Long Id);

    void deleteWishlistCustom(Product product, UserApp userApp);

}