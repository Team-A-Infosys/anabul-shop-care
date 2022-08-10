package team.kucing.anabulshopcare.service;

import org.springframework.data.repository.config.ResourceReaderRepositoryPopulatorBeanDefinitionParser;
import org.springframework.http.ResponseEntity;
import team.kucing.anabulshopcare.dto.request.CheckCoupon;
import team.kucing.anabulshopcare.dto.request.CouponRequest;

public interface CouponService {
    ResponseEntity<Object> createCoupon(CouponRequest request);

    ResponseEntity<Object> deleteCoupon(String couponCode);

    ResponseEntity<Object> checkCoupon(CheckCoupon request);
}
