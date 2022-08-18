package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.dto.request.UpdateQtyCart;
import team.kucing.anabulshopcare.entity.UserApp;

import java.security.Principal;
import java.util.UUID;

public interface CartService {
    ResponseEntity<Object> createCart(CartRequest cartRequest, Principal principal);

    ResponseEntity<Object> deleteCart(UUID id);

    ResponseEntity<Object> updateQtyCart(UpdateQtyCart updateQtyCart, UUID id);

    void deleteCartCustom(UserApp userApp);
}