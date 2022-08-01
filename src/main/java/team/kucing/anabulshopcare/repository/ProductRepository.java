package team.kucing.anabulshopcare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.Product;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findByNameContaining(String name, Pageable pageable);

    Page<Product> findByLocation(String location, Pageable pageable);

    Page<Product> findByPriceBetween(BigDecimal startPrice, BigDecimal endPrice, Pageable pageable);
}