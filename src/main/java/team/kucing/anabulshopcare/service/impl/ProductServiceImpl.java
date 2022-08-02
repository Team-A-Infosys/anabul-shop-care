package team.kucing.anabulshopcare.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import team.kucing.anabulshopcare.entity.Product;
import team.kucing.anabulshopcare.exception.ResourceNotFoundException;
import team.kucing.anabulshopcare.repository.ProductRepository;
import team.kucing.anabulshopcare.service.FileStorageService;
import team.kucing.anabulshopcare.service.ProductService;

import java.util.Optional;
import java.util.UUID;
import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final FileStorageService fileStorageService;

    @Override
    public ResponseEntity<Object> createProduct(Product product, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        product.setImageUrl(fileDownloadUri);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.productRepository.save(product));

    }

    @Override
    public ResponseEntity<Object> listProducts(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productRepository.findByIsPublished(Boolean.TRUE, pageable).toList());
    }

    @Override
    public ResponseEntity<Object> getName(String name, Pageable pageable ) {
        var getProduct = this.productRepository.findByNameContaining(name, pageable);
        if (getProduct.getTotalPages() == 0) {
            throw new ResourceNotFoundException("Uppsss product not found by name : " + name);
        }
        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }
    @Override
        public ResponseEntity<Object> filterProductsByLocation(String location, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByLocation(location, pageable);

        if (getProduct.getTotalPages() == 0){
            throw new ResourceNotFoundException("Sorry, There are no product in " + location + " area...");
        }
        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }

    @Override
    public ResponseEntity<Object> filterProductByPrice(BigDecimal startPrice, BigDecimal endPrice, Pageable pageable) {
        Page<Product> getProduct = this.productRepository.findByPriceBetween(startPrice, endPrice, pageable);

        if (getProduct.getTotalPages() == 0){
            throw new ResourceNotFoundException("Sorry, There are no product in that price range");
        }

        return ResponseEntity.status(HttpStatus.OK).body(getProduct.toList());
    }

    @Override
    public ResponseEntity<Object> filterUnpublishedProduct(Pageable pageable){
        Page<Product> product = this.productRepository.findByIsPublished(Boolean.FALSE, pageable);
        return ResponseEntity.ok(product.toList());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct == null){
            throw new ResourceNotFoundException("Product with ID "+id+" Is Not Found");
        }
        return productRepository.findById(id);
    }

    @Override
    public ResponseEntity<Object> updateProduct(Product product, MultipartFile file, UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if(productOptional == null){
            throw new ResourceNotFoundException("Product not exist with id"+id);
        }

        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileName)
                .toUriString();

        product.setImageUrl(fileDownloadUri);
        return ResponseEntity.status(HttpStatus.OK).body(this.productRepository.save(product));
    }

    @Override
    public ResponseEntity<Object> deleteProduct(UUID id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct == null){
            throw new ResourceNotFoundException("Product not exist with id : "+id);
        }
        Product product = productRepository.getReferenceById(id);
        this.productRepository.delete(product);
        return ResponseEntity.status(HttpStatus.OK).body("Success Delete Product " + product);

    }

    @Override
    public ResponseEntity<Object> updatePublishedStatus(UUID id, Product product) {
        return ResponseEntity.status(HttpStatus.OK).body("Success published " + product.getName() +
                " hope there is buyer take your product");
    }

}