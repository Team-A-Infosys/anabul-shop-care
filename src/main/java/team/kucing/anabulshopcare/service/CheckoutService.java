package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;

import java.security.Principal;
import java.util.UUID;

public interface CheckoutService {
    ResponseEntity<Object> createCheckout(CheckoutRequest checkoutRequest, Principal principal);

    ResponseEntity<Object> cancelCheckout(UUID id);

    ResponseEntity<Object> confirmPayment(UUID id);
}