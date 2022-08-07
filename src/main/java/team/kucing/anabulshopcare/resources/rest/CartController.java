package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.service.CartService;

@RestController
@AllArgsConstructor
public class CartController {
    private CartService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<Object> addCart(@RequestBody CartRequest cartRequest){
        return this.cartService.addCart(cartRequest);
    }
}