package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.dto.request.ProductRequest;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;

public interface WishlistService {
    ResponseEntity<Object> createWishlist(WishlistRequest wishlistRequest);

    ResponseEntity<Object> deleteWishlist(Long Id);
}
