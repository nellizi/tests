package com.example.demo.HashTag;

import com.example.demo.post.model.Post;
import com.example.demo.HashTag.model.HashTag;
import com.example.demo.Keyword.KeywordService;
import com.example.demo.Keyword.model.Keyword;
import com.example.demo.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HashTagService {

    private final KeywordService keywordService;
    private final HashTagRepository hashTagRepository;

    public void applyHashTags(Post post, String keywordContentsStr) {
        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        keywordContents.forEach(keywordContent -> {
            saveHashTag(post, keywordContent);
        });
    }

    public void applyProductHashTags(Product product, String keywordContentsStr) {
        List<String> keywordContents = Arrays.stream(keywordContentsStr.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .collect(Collectors.toList());

        keywordContents.forEach(keywordContent -> {
            saveProductHashTag(product, keywordContent);
        });
    }

    private HashTag saveHashTag(Post post, String keywordContent) {
        Keyword keyword = keywordService.save(keywordContent);

        Optional<HashTag> opHashTag = hashTagRepository.findByPostIdAndKeywordId(post.getPostId(), keyword.getKeywordId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        HashTag hashTag = new HashTag();
        hashTag.setPost(post);
        hashTag.setPostId(post.getPostId());
        hashTag.setKeyword(keyword);
        hashTag.setKeywordId(keyword.getKeywordId());


        hashTagRepository.save(hashTag);

        return hashTag;
    }
    private HashTag saveProductHashTag(Product product, String keywordContent) {
        Keyword keyword = keywordService.save(keywordContent);

        Optional<HashTag> opHashTag = hashTagRepository.findByProductIdAndKeywordId(product.getProductId(), keyword.getKeywordId());

        if (opHashTag.isPresent()) {
            return opHashTag.get();
        }

        HashTag hashTag = new HashTag();
        hashTag.setProduct(product);
        hashTag.setProductId(product.getProductId());
        hashTag.setKeyword(keyword);
        hashTag.setKeywordId(keyword.getKeywordId());


        hashTagRepository.save(hashTag);

        return hashTag;
    }



    public List<HashTag> getHashTags(Post post) {
        return hashTagRepository.findAllByPostId(post.getPostId());
    }
}