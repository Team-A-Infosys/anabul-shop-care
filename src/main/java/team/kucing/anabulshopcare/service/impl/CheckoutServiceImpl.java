package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.entity.Cart;
import team.kucing.anabulshopcare.entity.Checkout;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.CartRepository;
import team.kucing.anabulshopcare.repository.CheckoutRepository;
import team.kucing.anabulshopcare.repository.UserAppRepository;
import team.kucing.anabulshopcare.service.CheckoutService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private CheckoutRepository checkoutRepository;

    private UserAppRepository userAppRepository;

    private CartRepository cartRepository;

    @Override
    public ResponseEntity<Object> createCheckout(UUID id) {
        Optional<UserApp> findUser = this.userAppRepository.findById(id);

        if (findUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found");
        }

        UserApp getUserApp = findUser.get();

        Checkout newCheckout = new Checkout();

        List<Cart> cartList = getUserApp.getCart();
        for (Cart cart: cartList) {
            newCheckout.getCart().add(cart);
        }

        var obj = newCheckout.getCart().stream().mapToDouble(Cart::getSubTotal).sum();

        newCheckout.setCheckoutTotal(obj);
        this.checkoutRepository.save(newCheckout);

        return ResponseHandler.generateResponse("Success checkout", HttpStatus.OK, "OK");
    }
}