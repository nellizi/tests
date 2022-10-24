package com.example.demo.HashTag.model;

import com.example.demo.base.Base;
import com.example.demo.post.model.Post;
import com.example.demo.Keyword.model.Keyword;
import com.example.demo.product.model.Product;
import lombok.*;
import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class HashTag extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long hashtagId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "keyword_id")
    private Long keywordId;

    //  @JoinColumn(name = "post_post_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Post post;

    // @JoinColumn(name = "postkeyword_keyword_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Keyword keyword;

    @ManyToOne(cascade = CascadeType.ALL)
    @ToString.Exclude
    private Product product;


}
