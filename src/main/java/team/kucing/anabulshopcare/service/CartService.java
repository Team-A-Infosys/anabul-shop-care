package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CartRequest;

import java.util.UUID;

public interface CartService {
    ResponseEntity<Object> createCart(CartRequest cartRequest);

    ResponseEntity<Object> deleteCart(UUID id);
}
