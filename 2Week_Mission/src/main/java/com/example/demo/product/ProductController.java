package com.example.demo.product;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/create")
    public String productCreate (@AuthenticationPrincipal PrincipalDetails principalDetails,HttpServletResponse response
                                ,ProductForm productForm, Model model) throws IOException {
        if(principalDetails == null){
            alert(response,"로그인 후 이용해주세요.");
        }
        return "/product/productForm";
    }

    @PostMapping("/create")
    public String productCreatePost (@AuthenticationPrincipal PrincipalDetails principalDetails, ProductForm productForm) {
        productService.create(principalDetails,productForm );

        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String list( Model model) {
        List<Product> products =  productService.findAllProduct();

        model.addAttribute("products",products);
        return "product/productList";
    }

    // @PreAuthorize("isAuthenticated()") securityConfig에서 미리 설정 해둠
    @GetMapping("/{id}/modify")
    public String productModify(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long id,
                                HttpServletResponse response, ProductForm productForm, Model model) throws IOException {

        Product product = productService.getProduct(id);

        if(!principalDetails.getMember().getMemberId().equals(product.getMemberId())){
            alert(response,"작성자만 이용 가능합니다.");
        }

        model.addAttribute("product",product);
        model.addAttribute("id",id);

        return "product/productModify";
    }

    @PostMapping("/{id}/modify")
    public String productModifyPost(@PathVariable long id,ProductForm productForm) {

        productService.modify(id, productForm);

        return "redirect:/product/{id}";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}/delete")
    public String delete(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable long id,
                         HttpServletResponse response) throws IOException {
        Product product = productService.getProduct(id);

        if(!principalDetails.getMember().getMemberId().equals(product.getMemberId())){
            alert(response,"작성자만 삭제 가능합니다.");
        }
        productService.delete(id);

        return "redirect:/product/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable long id, Model model) {

        model.addAttribute("product", productService.getProduct(id));
        return "product/productDetail";
    }

    public void alert(HttpServletResponse response, String msg) throws IOException {

        response.setContentType("text/html; charset=utf-8");
        response.getWriter().print("<script>alert('" + msg + "');history.back();</script>");
    }

}
