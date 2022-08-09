package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;

import java.util.UUID;

public interface CheckoutService {
    ResponseEntity<Object> createCheckout(UUID id, CheckoutRequest checkoutRequest);

    ResponseEntity<Object> cancelCheckout(UUID id);

    ResponseEntity<Object> confirmPayment(UUID id);
}