package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CouponRequest;
import team.kucing.anabulshopcare.service.CouponService;

@RestController
@AllArgsConstructor
@Tag(name = "8. Coupon Controller")
public class CouponController {

    private CouponService couponService;

    @PostMapping("/coupon/create")
    public ResponseEntity<Object> createCoupon(@RequestBody CouponRequest request) {
        return this.couponService.createCoupon(request);
    }

    @DeleteMapping("/coupon/delete/{couponCode}")
    public ResponseEntity<Object> deleteCoupon(@PathVariable(value = "couponCode") String couponCode) {
        return this.couponService.deleteCoupon(couponCode);
    }
}

