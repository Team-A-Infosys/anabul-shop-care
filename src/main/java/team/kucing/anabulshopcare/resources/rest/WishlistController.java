package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.service.WishlistService;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Slf4j
public class WishlistController {
    private final WishlistService wishlistService;

    @PostMapping(value = "/wishlist/add")
    public ResponseEntity<Object> addWishlist(@RequestPart @Valid WishlistRequest wishlist){
        var addItemWishlist = this.wishlistService.createWishlist(wishlist);
        return addItemWishlist;
    }
}
