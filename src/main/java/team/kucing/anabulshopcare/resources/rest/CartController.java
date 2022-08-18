package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.dto.request.UpdateQtyCart;
import team.kucing.anabulshopcare.service.CartService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "05. Cart Controller")
public class CartController {

    private final CartService cartService;

    @PostMapping("/cart/addtocart")
    @Operation(summary = "Add To Cart [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> addCart(@RequestBody CartRequest cartRequest){
        var addToCart = this.cartService.createCart(cartRequest);
        log.info("Add new Item to Cart");
        return addToCart;
    }

    @DeleteMapping("/cart/{id}/delete")
    @Operation(summary = "Delete Cart [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deleteCart(@PathVariable(value = "id") UUID id){
        log.info("Successfully removed cart");
        return this.cartService.deleteCart(id);
    }

    @PutMapping("/cart/{id}/updateqty")
    @Operation(summary = "Change Qty Item Cart [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> changeQtyItemCart(@PathVariable(value = "id") UUID id, UpdateQtyCart updateQtyCart){
        return this.cartService.updateQtyCart(updateQtyCart,id);
    }

}