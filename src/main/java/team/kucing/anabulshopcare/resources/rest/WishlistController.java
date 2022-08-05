package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.service.WishlistService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping("/wishlist/add")
    public ResponseEntity<Object> addWishlist(@RequestBody WishlistRequest wishlistRequest){
        var addItemWishlist = this.wishlistService.createWishlist(wishlistRequest);
        log.info("New Item Added To Wishlist");
        return addItemWishlist;
    }

    @DeleteMapping("/wishlist/delete/{id}")
    public ResponseEntity<Object> deleteWishlist(@PathVariable(value = "id") Long id){
        return this.wishlistService.deleteWishlist(id);
    }
}
