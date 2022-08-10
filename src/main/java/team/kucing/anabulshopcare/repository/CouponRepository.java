package team.kucing.anabulshopcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.Coupon;
import team.kucing.anabulshopcare.entity.UserApp;

import java.text.DateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;
import java.util.UUID;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    boolean existsByCode(String code);

    Coupon findByCode(String code);


}