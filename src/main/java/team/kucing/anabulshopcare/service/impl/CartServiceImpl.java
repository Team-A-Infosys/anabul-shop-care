package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CartRequest;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;

    private UserAppRepository userAppRepository;

    private final ProductRepository productRepository;

    @Override
    public ResponseEntity<Object> createCart(CartRequest cartRequest) {
        Optional<Product> product = this.productRepository.findById(cartRequest.getProductId());

        UserApp userApp = this.userAppRepository.findByEmail(cartRequest.getEmailUser());

        if(product.isEmpty()){
            throw new ResourceNotFoundException("Product Not Found");
        }

        Product getProduct = product.get();
        if(getProduct.getIsPublished() == Boolean.FALSE){
            throw new ResourceNotFoundException("Product Not Found");
        }

        Cart cart = new Cart();
        cart.setProductC(getProduct);
        cart.setUserAppC(userApp);
        cart.setQuantity(cartRequest.getQuantity());
        cart.setSub_total(cartRequest.getQuantity() * getProduct.getPrice());

        Cart saveCart =  this.cartRepository.save(cart);
        CartResponse response = saveCart.convertToResponse();

        List<Cart> carts = new ArrayList<>();
        carts.add(cart);

        userApp.setCart(carts);
        this.userAppRepository.save(userApp);

        getProduct.setCart(carts);
        this.productRepository.save(getProduct);

        log.info(userApp.getEmail() + "success add to cart" + getProduct.getId() + " to their cart");
        return ResponseHandler.generateResponse("Success add product to cart", HttpStatus.OK, response);
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
}
