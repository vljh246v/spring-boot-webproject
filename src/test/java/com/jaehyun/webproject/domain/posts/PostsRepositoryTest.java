package com.jaehyun.webproject.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void get_all_posts(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("vljh246v@gmail.com")
                        .build());

        String title2 = "테스트 게시글2";
        String content2 = "테스트 본문2";

        postsRepository.save(
                Posts.builder()
                        .title(title2)
                        .content(content2)
                        .author("vljh246v@gmail.com")
                        .build());

        // when
        List<Posts> posts = postsRepository.findAllDesc();

        // then
        Posts post = posts.get(1);

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);

        Posts post2 = posts.get(0);

        assertThat(post2.getTitle()).isEqualTo(title2);
        assertThat(post2.getContent()).isEqualTo(content2);
    }

    @Test
    public void get_posts(){
        // given
        String title = "테스트 게시글";
        String content = "테스트 본문";

        postsRepository.save(
                Posts.builder()
                        .title(title)
                        .content(content)
                        .author("vljh246v@gmail.com")
                        .build());

        // when
        List<Posts> posts = postsRepository.findAll();

        // then
        Posts post = posts.get(0);

        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }
}