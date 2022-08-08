package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CheckoutService {
    ResponseEntity<Object> createCheckout(UUID id);
}