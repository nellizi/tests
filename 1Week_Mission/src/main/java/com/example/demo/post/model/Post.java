package com.example.demo.post.model;

import com.example.demo.HashTag.model.HashTag;
import com.example.demo.base.Base;
import lombok.*;
import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "posts")
public class Post extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "title")
    private String title;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    @Column(name = "keyword")
    private String keyword;


}
