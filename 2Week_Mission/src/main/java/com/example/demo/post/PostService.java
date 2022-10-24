package com.example.demo.post;

import com.example.demo.HashTag.model.HashTag;
import com.example.demo.auth.PrincipalDetails;
import com.example.demo.post.model.Post;
import com.example.demo.HashTag.HashTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final HashTagService hashTagService;

    public void write(PrincipalDetails principalDetails, PostForm postForm) {
       Post post = new Post();

       String hashTagsStr = postForm.getKeyword();

       post.setTitle(postForm.getTitle());
       post.setContent(postForm.getContent());
       post.setKeyword(postForm.getKeyword());
       post.setUsername(principalDetails.getUsername());

        hashTagService.applyHashTags(post, hashTagsStr);

        postRepository.save(post);
    }



    public Post getPost(long postId) {
        return postRepository.findByPostId(postId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));
    }

    public void modify(long postId, PostForm postForm) {
        Post post = postRepository.findByPostId(postId)
                      .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));
        System.out.println(postId);

        post.setTitle(postForm.getTitle());
        post.setContent(postForm.getContent());

        postRepository.save(post);
    }

    public void delete(long postId) {
        Post post = postRepository.findByPostId(postId)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 게시글을 찾을 수 없습니다."));

        postRepository.delete(post);
    }

    public List<Post> findAllPost() {
        return postRepository.findAll();
    }

}
