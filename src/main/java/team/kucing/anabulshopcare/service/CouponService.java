package team.kucing.anabulshopcare.service;

import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CouponRequest;

public interface CouponService {
    ResponseEntity<Object> createCoupon(CouponRequest request);

    ResponseEntity<Object> deleteCoupon(String couponCode);
}
