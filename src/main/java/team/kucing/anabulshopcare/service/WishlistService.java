package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;

import java.util.UUID;

public interface WishlistService {
    ResponseEntity<Object> createWishlist(WishlistRequest wishlistRequest);

    ResponseEntity<Object> deleteWishlist(Long Id);
}