package team.kucing.anabulshopcare.resources.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.service.ProductService;

@RestController
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "/add/product", consumes = {MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addProduct(@RequestPart MultipartFile file, @RequestPart Product product){
       return this.productService.createProduct(product, file);
    }
}