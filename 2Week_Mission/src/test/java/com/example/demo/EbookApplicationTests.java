package com.example.demo;

import com.example.demo.post.PostRepository;
import com.example.demo.post.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EbookApplicationTests {
	@Autowired
	private PostRepository postRepository;



	@Test
	void contextLoads() {
	}

	@Test
	void addPostTest() {

		for (Long i = 1L; i <= 100L; i++) {

			String title = Long.toString(i);
			Post post = new Post();
			post.setTitle(title);
			post.setContent("테스트 게시글입니다.");
			post.setUsername("tester");
			postRepository.save(post);

		}
	}
}
