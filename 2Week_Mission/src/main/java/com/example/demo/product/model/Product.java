package com.example.demo.product.model;

import com.example.demo.base.Base;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "product")
public class Product extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "member_id")
    private Long memberId; //authorId

    @Column(name = "postkeyword_id")
    private Long postKeywordId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "price")
    private Long price;

    @Column(name = "keyword")
    private String keyword;

}
