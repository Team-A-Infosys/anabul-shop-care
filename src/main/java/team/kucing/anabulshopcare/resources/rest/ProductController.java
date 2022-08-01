package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.service.ProductService;

import javax.validation.Valid;

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
        log.info("success get data product " + getAllProducts);
        return getAllProducts;
    }

    @GetMapping("/products/search/name")
    public ResponseEntity<Object> findByProductName(@RequestParam(value = "productName") String name, Pageable pageable) {
        var getProduct = this.productService.getName(name,pageable);
        return getProduct;
    }

    }
