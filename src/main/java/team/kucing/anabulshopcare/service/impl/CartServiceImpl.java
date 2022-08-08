package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.entity.Cart;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.entity.Wishlist;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.CartRepository;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.CartService;
import team.kucing.anabulshopcare.service.WishlistService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CartServiceImpl implements CartService {

    private WishlistService wishlistService;

    private CartRepository cartRepository;

    private ProductRepository productRepository;

    private UserAppRepository userAppRepository;

    @Override
    public ResponseEntity<Object> addCart(CartRequest cartRequest){
        UserApp userApp = this.userAppRepository.findByEmail(cartRequest.getEmailUser());

        Optional<Product> product = this.productRepository.findById(cartRequest.getProductId());

        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product is not found");
        }

        Product getProduct = product.get();
        if(getProduct.getIsPublished() == Boolean.FALSE){
            throw new ResourceNotFoundException("Product is not found");
        }

        Cart newCart = new Cart();
        newCart.setProduct(Collections.singletonList(getProduct));
        newCart.setUserApp(userApp);
        newCart.setQuantity(cartRequest.getQuantity());
        newCart.setSubTotal(cartRequest.getQuantity() * getProduct.getPrice());
        Cart saveCart = this.cartRepository.save(newCart);

        List<Cart> cartList = userApp.getCart();
        cartList.add(newCart);
        this.userAppRepository.save(userApp);

        this.wishlistService.deleteWishlistCustom(getProduct);
        this.productRepository.save(getProduct);

        List<Cart> cartProduct = getProduct.getCart();
        cartProduct.add(newCart);
        this.productRepository.save(getProduct);

        return ResponseHandler.generateResponse("Success add to cart", HttpStatus.OK, saveCart.convertToResponse());
    }

    @Override
    public void deleteCartCustom(UserApp userApp) {
        List<Cart> cart = this.cartRepository.findByUserApp(userApp);
        this.cartRepository.deleteAll(cart);
    }
}