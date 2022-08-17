package team.kucing.anabulshopcare.resources.rest;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kucing.anabulshopcare.dto.request.CheckCoupon;
import team.kucing.anabulshopcare.dto.request.CouponRequest;
import team.kucing.anabulshopcare.service.CouponService;

@RestController
@AllArgsConstructor
@Tag(name = "08. Coupon Controller")
public class CouponController {

    private CouponService couponService;

    @PostMapping("/coupon/create")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> createCoupon(@RequestBody CouponRequest request) {
        return this.couponService.createCoupon(request);
    }

    @DeleteMapping("/coupon/delete/{couponCode}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> deleteCoupon(@PathVariable(value = "couponCode") String couponCode) {
        return this.couponService.deleteCoupon(couponCode);
    }

    @GetMapping("/coupon/check/{couponCode}")
    @PreAuthorize("hasAuthority('ROLE_BUYER')")
    @SecurityRequirement(name = "bearer-key")
    public ResponseEntity<Object> validateCoupon(@RequestParam(value = "couponCode", required = false) CheckCoupon request){
        return this.couponService.checkCoupon(request);
    }
}

