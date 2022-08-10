package team.kucing.anabulshopcare.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponRequest {

    private String couponCode;

    private Long value;

    private Long totalCoupon;



}
