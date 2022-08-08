package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.dto.request.PaymentRequest;
import team.kucing.anabulshopcare.service.PaymentService;

@RestController
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/payment-gateway/create")
    public ResponseEntity<Object> createPayment(@RequestBody PaymentRequest request) {
        return this.paymentService.createPayment(request);
    }
}
