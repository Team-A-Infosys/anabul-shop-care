package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.service.WishlistService;

import java.util.UUID;

@RestController
@AllArgsConstructor
@Slf4j
@Tag(name = "04. Wishlist Controller")
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/wishlist/add")
    @Operation(summary = "Add Wishlist By Product Id [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> addWishlist(@RequestBody WishlistRequest wishlistRequest){
        var addItemWishlist = this.wishlistService.createWishlist(wishlistRequest);
        log.info("New Item Added To Wishlist");
        return addItemWishlist;
    }

    @DeleteMapping("/wishlist/{id}/delete")
    @Operation(summary = "Delete Wishlist By Wishlist Id [BUYER]")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deleteWishlist(@PathVariable(value = "id") Long id){
        log.info("successfully removed the product from wishlist");
        return this.wishlistService.deleteWishlist(id);
    }

}