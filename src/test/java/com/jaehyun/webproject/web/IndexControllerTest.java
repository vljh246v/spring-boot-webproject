package com.jaehyun.webproject.web;

import com.jaehyun.webproject.domain.posts.Posts;
import com.jaehyun.webproject.domain.posts.PostsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IndexControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void index_page_loading() throws Exception {
        // when
        String body = this.restTemplate.getForObject("/", String.class);

        // then
        final String pageTitle = "테스트용 게시판~~";
        final String idHeader = "게시글번호";
        final String titleHeader = "제목";
        final String authorHeader = "작성자";
        final String modifiedDateHeader = "최종수정일";

        assertThat(body).contains(pageTitle)
                .contains(idHeader)
                .contains(titleHeader)
                .contains(authorHeader)
                .contains(modifiedDateHeader);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void post_save_page_loading() throws Exception {
        // given
        final String titleHeader = "제목";
        final String authorHeader = "작성자";
        final String contentHeader = "내용";

        final String url = "http://localhost:" + port + "/posts/save/";

        mvc.perform(get(url))
                .andExpect(content().string(containsString(titleHeader)))
                .andExpect(content().string(containsString(authorHeader)))
                .andExpect(content().string(containsString(contentHeader)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "USER")
    public void post_update_page_loading() throws Exception {
        // given
        final String title = "title";
        final String content = "content";
        final String demo = "demo";
        final String pageTitle = "게시글 수정";
        Posts savePosts = postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author(demo)
                .build());

        final String url = "http://localhost:" + port + "/posts/update/" + savePosts.getId();

        mvc.perform(get(url))
                .andExpect(content().string(containsString(title)))
                .andExpect(content().string(containsString(content)))
                .andExpect(content().string(containsString(demo)))
                .andExpect(content().string(containsString(pageTitle)))
                .andExpect(status().isOk());
    }
}