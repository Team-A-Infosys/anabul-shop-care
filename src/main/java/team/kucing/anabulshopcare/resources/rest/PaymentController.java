package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.kucing.anabulshopcare.dto.request.PaymentRequest;
import team.kucing.anabulshopcare.service.PaymentService;

@RestController
@Slf4j
@AllArgsConstructor
public class PaymentController {

    private PaymentService paymentService;

    @PostMapping("/payment-gateway/create")
    public ResponseEntity<Object> createPayment(@RequestBody PaymentRequest request) {
        return this.paymentService.createPayment(request);
    }

    @GetMapping("/payments")
    public ResponseEntity<Object> getPayments(){
        log.info("success get All Payments");
        return this.paymentService.getAllPayment();
    }
}