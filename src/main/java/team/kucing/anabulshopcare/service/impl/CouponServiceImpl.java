package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CouponRequest;
import team.kucing.anabulshopcare.entity.Coupon;
import team.kucing.anabulshopcare.entity.UserApp;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.CouponRepository;
import team.kucing.anabulshopcare.service.CouponService;

import java.util.InputMismatchException;
import java.util.Locale;

@Service
@AllArgsConstructor
@Slf4j
public class CouponServiceImpl implements CouponService {
    private CouponRepository couponRepository;

    @Override
    public ResponseEntity<Object> createCoupon(CouponRequest request) {
        Coupon newCoupon = new Coupon();

        if (this.couponRepository.existsByCode(request.getCouponCode())) {
            throw new BadRequestException("Coupon code Already Exists");
        }

        if (request.getValue() < 1000) {
            throw new BadRequestException("Value of Coupon at least 1000");
        }

        newCoupon.setCode(request.getCouponCode().toUpperCase());
        newCoupon.setValue(request.getValue());
        newCoupon.setTotalCoupon(request.getTotalCoupon());
        this.couponRepository.save(newCoupon);
        log.info("Coupon saved successfully " + newCoupon);
        return ResponseHandler.generateResponse("Success create Coupon", HttpStatus.CREATED, newCoupon);
    }

    @Override
    public ResponseEntity<Object> deleteCoupon(String couponCode) {
        Coupon coupon = this.couponRepository.findByCode(couponCode.toUpperCase());

        if (coupon == null) {
            throw new ResourceNotFoundException("Coupon not found");
        }
        this.couponRepository.delete(coupon);
        log.info("Coupon deleted successfully " + coupon);
        return ResponseHandler.generateResponse("Success delete Coupon", HttpStatus.OK, null);
    }

    @Override
    public ResponseEntity<Object> updateCoupon(CouponRequest request) {
        Coupon quotaCoupon = new Coupon();
        if (request.getTotalCoupon() != 0L){
            quotaCoupon.setTotalCoupon(quotaCoupon.getTotalCoupon() -1);
            return ResponseHandler.generateResponse("Success Apply Coupon", HttpStatus.ACCEPTED, null);
        }
        else if (quotaCoupon == null ){
            throw new ResourceNotFoundException("This Coupon is no longer valid");
        }
        return null;
    }

}
