package team.kucing.anabulshopcare.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {

    private List<CartResponse> cart;

    private String shipmentAddress;

    private double checkoutTotal;

}