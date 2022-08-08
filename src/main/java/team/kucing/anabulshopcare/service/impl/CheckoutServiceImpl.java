package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.kucing.anabulshopcare.dto.request.CheckoutRequest;
import team.kucing.anabulshopcare.entity.*;
import team.kucing.anabulshopcare.entity.subaddress.Provinsi;
import team.kucing.anabulshopcare.exception.BadRequestException;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.handler.ResponseHandler;
import team.kucing.anabulshopcare.repository.*;
import team.kucing.anabulshopcare.service.CartService;
import team.kucing.anabulshopcare.service.CheckoutService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private CheckoutRepository checkoutRepository;

    private UserAppRepository userAppRepository;

    private ProductRepository productRepository;

    private CouponRepository couponRepository;

    private ShipmentRepository shipmentRepository;

    private PaymentRepository paymentRepository;

    private CartService cartService;

    @Override
    public ResponseEntity<Object> createCheckout(UUID id, CheckoutRequest checkoutRequest) {
        Optional<UserApp> findUser = this.userAppRepository.findById(id);
        Coupon coupon = this.couponRepository.findByCode(checkoutRequest.getCouponCode().toUpperCase());

        if (findUser.isEmpty()){
            throw new ResourceNotFoundException("User is not found");
        }

        UserApp getUserApp = findUser.get();
        if (getUserApp.getCart().size() == 0){
            throw new BadRequestException("Your cart is empty");
        }

        Checkout newCheckout = new Checkout();

        String shipmentAddress = getUserApp.getAddress().getProvinsi().getNama() +
                ", " +
                getUserApp.getAddress().getKota().getNama() +
                ", " +
                getUserApp.getAddress().getKecamatan().getNama() +
                ", " +
                getUserApp.getAddress().getKelurahan().getNama();
        newCheckout.setShipmentAddress(shipmentAddress);

        List<Cart> cartList = getUserApp.getCart();
        for (Cart cart: cartList) {
            cart.getProduct().setStock(cart.getProduct().getStock() - cart.getQuantity());
            this.productRepository.save(cart.getProduct());
            newCheckout.getCart().add(cart);
        }

        Provinsi provinsi = getUserApp.getAddress().getProvinsi();
        Shipment shipment = this.shipmentRepository.findByProvinsi(provinsi);
        newCheckout.setShipmentCost(shipment.getPrice());
        newCheckout.setCourier(shipment.getCompany());

        var obj = newCheckout.getCart().stream().mapToDouble(Cart::getSubTotal).sum();
        if (coupon == null){
            newCheckout.setCheckoutTotal(obj + shipment.getPrice());
            newCheckout.setCouponCode("NO COUPON");
            newCheckout.setValueCoupon("0");
        } else {
            newCheckout.setCheckoutTotal((obj + shipment.getPrice()) - coupon.getValue());
            newCheckout.setCouponCode(coupon.getCode());
            newCheckout.setValueCoupon("-" + coupon.getValue());
        }

        Payment payment = this.paymentRepository.findByBankName(checkoutRequest.getBankName());
        newCheckout.setPaymentGateway(payment);

        this.checkoutRepository.save(newCheckout);

        this.cartService.deleteCartCustom(getUserApp);
        this.userAppRepository.save(getUserApp);

        getUserApp.getHistory().add(newCheckout);
        this.userAppRepository.save(getUserApp);

        return ResponseHandler.generateResponse("Success checkout ", HttpStatus.OK, newCheckout.convertToResponse());
    }

    @Override
    public ResponseEntity<Object> cancelCheckout(UUID id) {
        Optional<Checkout> checkoutDelete = this.checkoutRepository.findById(id);
        if (checkoutDelete.isEmpty()){
            throw new ResourceNotFoundException("Checkout cart is not found");
        }
        Checkout checkout = checkoutDelete.get();

        List<Cart> cartList = checkout.getCart();
        for (Cart cart: cartList) {
            cart.getProduct().setStock(cart.getProduct().getStock() + cart.getQuantity());
            cart.setCheckout(Boolean.FALSE);
            this.productRepository.save(cart.getProduct());
        }

        this.checkoutRepository.delete(checkout);


        return ResponseHandler.generateResponse("Success delete checkout", HttpStatus.OK, null);
    }
}