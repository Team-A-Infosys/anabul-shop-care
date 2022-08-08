package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.PaymentRequest;
import team.kucing.anabulshopcare.entity.Payment;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.PaymentRepository;
import team.kucing.anabulshopcare.service.PaymentService;

@Service
@Slf4j
@AllArgsConstructor
public class PaymentServiceImpl  implements PaymentService {

    private PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<Object> createPayment(PaymentRequest request) {
        Payment newPayment = new Payment();
        newPayment.setBankName(request.getBankName().toUpperCase());
        newPayment.setBankAccount(request.getBankAccount());
        newPayment.setAccountName(request.getAccountName().toUpperCase());
        this.paymentRepository.save(newPayment);
        log.info("Added new Payment Gateway " + newPayment);
        return ResponseHandler.generateResponse("Success Create Payment Gateway", HttpStatus.OK, newPayment);
    }
}