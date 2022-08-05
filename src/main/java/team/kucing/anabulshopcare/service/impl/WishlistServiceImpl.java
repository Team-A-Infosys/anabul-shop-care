package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.WishlistRequest;
import team.kucing.anabulshopcare.dto.response.WishlistResponse;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.Wishlist;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.repository.WishlistRepository;
import team.kucing.anabulshopcare.service.WishlistService;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    private final UserAppRepository userAppRepository;

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<Object> createWishlist(WishlistRequest wishlistRequest) {
        UserApp userApp = this.userAppRepository.findByEmail(wishlistRequest.getEmailUser());
        Optional<Product> product = this.productRepository.findById(wishlistRequest.getProductId());

        Wishlist wishlist = new Wishlist();
        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product is not found");
        }

        if(userApp.getEmail() == null){
            throw new ResourceNotFoundException("User is not Registered");
        }

        wishlist.setProduct(product.get());
        wishlist.setUserApp(userApp);

        Wishlist saveWishlist = this.wishlistRepository.save(wishlist);
        WishlistResponse response = saveWishlist.convertToResponse();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Object> deleteWishlist(Long id){
        Optional<Wishlist> wishlist = wishlistRepository.findById(id);

        if(wishlist.isEmpty()){
            throw new ResourceNotFoundException("Wishlist Not Found");
        }

        Wishlist wish = wishlistRepository.getReferenceById(id);
        this.wishlistRepository.delete(wish);
        return ResponseEntity.ok().body("Your Wishlist is Deleted");
    }
}
