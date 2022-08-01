package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@AllArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/add/product", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addProduct(@RequestPart MultipartFile file, @RequestPart @Valid Product product){
        var createProduct = this.productService.createProduct(product,file);
        log.info("success create product " + createProduct);
        return createProduct;
    }

    @GetMapping("/products")
    public ResponseEntity<Object> getAllProducts(Pageable pageable){
        var getAllProducts = this.productService.listProducts(pageable);
        return getAllProducts;
    }

    @GetMapping("/products/search/location")
    public ResponseEntity<Object> filterProductsByLocation(@RequestParam(value = "location", required = false) String location, Pageable pageable){
        var getProduct = this.productService.filterProductsByLocation(location, pageable);
        return getProduct;
    }

    @GetMapping("/products/search/price")
    public ResponseEntity<Object> filterProductsByPrice(@RequestParam(value = "start", required = false)BigDecimal startPrice, @RequestParam(value = "end", required = false)BigDecimal endPrice, Pageable pageable){
        var getProduct = this.productService.filterProductByPrice(startPrice, endPrice, pageable);
        return getProduct;
    }
}