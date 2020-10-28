package com.jaehyun.webproject.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void index_page_loading() throws Exception {
        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        assertThat(body).contains("테스트용 게시판~~");
    }

    @Test
    public void post_save_page_loading() throws Exception {
        // when
        String body = this.restTemplate.getForObject("/posts/save", String.class);

        // then
        assertThat(body).contains("게시글 등록");
    }


}