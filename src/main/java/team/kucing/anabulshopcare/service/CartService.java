package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CartRequest;

public interface CartService {
    ResponseEntity<Object> addCart(CartRequest cartRequest);
}