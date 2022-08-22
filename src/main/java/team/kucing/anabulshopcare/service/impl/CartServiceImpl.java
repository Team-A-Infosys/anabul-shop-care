package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CartRequest;
import team.kucing.anabulshopcare.dto.request.UpdateQtyCart;
import team.kucing.anabulshopcare.dto.response.CartResponse;
import team.kucing.anabulshopcare.entity.Cart;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.CartRepository;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.CartService;
import team.kucing.anabulshopcare.service.WishlistService;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public ResponseEntity<Object> createCart(CartRequest cartRequest, Principal principal){
        Optional<Product> product = this.productRepository.findById(cartRequest.getProductId());

        UserApp userApp = this.userAppRepository.findByEmail(principal.getName());

        if (product.isEmpty()) {
            throw new ResourceNotFoundException("Product Not Found");
        }

        Product getProduct = product.get();
        if (getProduct.getIsPublished() == Boolean.FALSE) {
            throw new ResourceNotFoundException("Product Not Found");
        }

        Cart checkCart = this.cartRepository.findByProductAndUserAppAndIsDeleted(getProduct, userApp, false);

        Cart cart = new Cart();

        if (checkCart != null) {
            var newQty = cartRequest.getQuantity() + checkCart.getQuantity();
            checkCart.setQuantity(newQty);
            checkCart.setSubTotal(checkCart.getProduct().getPrice() * newQty);
            Cart saveCart = this.cartRepository.save(checkCart);
            CartResponse response = saveCart.convertToResponse();

            log.info(userApp.getEmail() + "success update cart" + getProduct.getId() + " to their cart");
            return ResponseHandler.generateResponse("Success update product to cart", HttpStatus.OK, response);
        } else {
            cart.setProduct(getProduct);
            cart.setUserApp(userApp);
            cart.setQuantity(cartRequest.getQuantity());
            cart.setSubTotal(cartRequest.getQuantity() * getProduct.getPrice());
            Cart saveCart = this.cartRepository.save(cart);
            CartResponse response = saveCart.convertToResponse();

            userApp.getCart().add(cart);
            this.userAppRepository.save(userApp);

            this.wishlistService.deleteWishlistCustom(getProduct, userApp);
            this.productRepository.save(getProduct);

            getProduct.getCart().add(cart);
            this.productRepository.save(getProduct);

            log.info(userApp.getEmail() + "success add product: " + getProduct.getId() + " to their cart");
            return ResponseHandler.generateResponse("Success add product to cart", HttpStatus.OK, response);
        }
    }

    @Override
    public void deleteCartCustom(UserApp userApp) {
        List<Cart> cart = this.cartRepository.findByUserApp(userApp);
        for (Cart c : cart) {
            c.setCheckout(Boolean.TRUE);
            c.setDeleted(Boolean.TRUE);
            this.cartRepository.save(c);
        }
    }

    @Override
    public ResponseEntity<Object> deleteCart(UUID id) {
        Optional<Cart> cart = cartRepository.findById(id);

        if(cart.isEmpty()){
            log.error("Cart Not Found");
            throw new ResourceNotFoundException("Cart Not Found");
        }

        this.cartRepository.delete(cart.get());
        log.info("Your Cart is Deleted");
        return ResponseHandler.generateResponse("Success delete cart with id", HttpStatus.OK, null);
    }

    private Cart findById(UUID id){
        Optional<Cart> optionalCart = cartRepository.findById(id);

        if(optionalCart.isEmpty()){
            throw new ResourceNotFoundException("Cart with id "+id+" No Found");
        }
        log.info("Success find cart with id " + id);
        return optionalCart.get();
    }

    @Override
    public ResponseEntity<Object> updateQtyCart(UpdateQtyCart updateQtyCart, UUID id){
        Cart cart = this.findById(id);

        cart.setQuantity(updateQtyCart.getQtyItem());
        cart.setSubTotal(cart.getProduct().getPrice() * updateQtyCart.getQtyItem());
        this.cartRepository.save(cart);

        log.info("Success change quantity of cart ID : "+id);
        return ResponseHandler.generateResponse("Success change qty of the product", HttpStatus.OK, cart.convertToResponse());
    }
}