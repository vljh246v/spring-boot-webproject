package com.jaehyun.webproject.web;

import com.jaehyun.webproject.domain.posts.Posts;
import com.jaehyun.webproject.domain.posts.PostsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void index_page_loading() throws Exception {
        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        assertThat(body).contains("테스트용 게시판~~")
                .contains("게시글번호")
                .contains("제목")
                .contains("작성자")
                .contains("최종수정일");
    }

    @Test
    public void post_save_page_loading() throws Exception {
        // when
        String body = this.restTemplate.getForObject("/posts/save", String.class);

        // then
        assertThat(body).contains("게시글 등록");
    }

    @Test
    public void post_update_page_loading() throws Exception {
        // given
        Posts savePosts = postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("demo")
                .build());


        String url = "http://localhost:" + port + "/posts/update/" + savePosts.getId();

        String updatePage = this.restTemplate.getForObject(url, String.class);

        assertThat(updatePage)
                .contains("title")
                .contains("content")
                .contains("demo")
                .contains("게시글 수정");
    }
}