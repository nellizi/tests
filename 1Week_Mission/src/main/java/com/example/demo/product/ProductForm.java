package com.example.demo.product;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductForm {

    private String subject;
    private Long price;
    private String keyword;
    private Long memberId;

}