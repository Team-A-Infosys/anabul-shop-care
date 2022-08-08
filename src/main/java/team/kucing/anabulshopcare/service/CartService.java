package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.entity.UserApp;

public interface CartService {
    ResponseEntity<Object> addCart(CartRequest cartRequest);

    void deleteCartCustom(UserApp userApp);
}