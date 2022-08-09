package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.PaymentRequest;

public interface PaymentService {
    ResponseEntity<Object> createPayment(PaymentRequest request);

    ResponseEntity<Object> getAllPayment();
}
