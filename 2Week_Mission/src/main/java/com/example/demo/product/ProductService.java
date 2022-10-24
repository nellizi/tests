package com.example.demo.product;

import com.example.demo.HashTag.HashTagService;
import com.example.demo.auth.PrincipalDetails;
import com.example.demo.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final HashTagService hashTagService;

    public void create(PrincipalDetails principalDetails, ProductForm productForm) {
       Product product = new Product();

        product.setSubject(productForm.getSubject());
        product.setPrice(productForm.getPrice());
        product.setKeyword(productForm.getKeyword());
        product.setMemberId(principalDetails.getMember().getMemberId());


        hashTagService.applyProductHashTags(product, productForm.getKeyword());

        productRepository.save(product);
    }

    public Product getProduct(long productId) {
        return productRepository.findByProductId(productId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));
    }

    public void modify(long productId, ProductForm productForm) {
        Product product = productRepository.findByProductId(productId)
                      .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));
        System.out.println(productId);

        product.setSubject(productForm.getSubject());
        product.setPrice(productForm.getPrice());

        productRepository.save(product);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #postDto.user.userId == authentication.principal.username")
    public void delete(long productId) {
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));

        productRepository.delete(product);
    }

    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }


}
