package com.dream.study01.controller;
import com.dream.study01.config.JwtAccessDeniedHandler;
import com.dream.study01.config.JwtAuthenticationEntryPoint;
import com.dream.study01.domain.entity.Post;
import com.dream.study01.dto.PostDto;
import com.dream.study01.jwt.TokenProvider;
import com.dream.study01.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ExtendWith(SpringExtension.class)
@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;
    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Test
    @DisplayName("POST 업로드 테스트")
    public void postUploadTest() throws Exception {
        PostDto postDto = PostDto.builder()
                .content("hello")
                .title("title")
                .hit(1)
                .writer("Jaehyuk")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("hello")
                .title("title")
                .hit(1)
                .writer("Jaehyuk")
                .build();

        String content = new Gson().toJson(postDto);

        given(postService.createPost(any())).willReturn(post);
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                        .andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andDo(print());
    }

    @Test
    @DisplayName("POST READ 테스트")
    public void postReadTest() throws Exception {

        List<PostDto> postList = new ArrayList<>();

        PostDto postDto = PostDto.builder()
                .id(1L)
                .content("content")
                .writer("JH")
                .hit(1)
                .title("title")
                .build();

        PostDto postDto1 = PostDto.builder()
                .id(2L)
                .content("content")
                .writer("JH")
                .hit(1)
                .title("title")
                .build();

        postList.add(postDto);
        postList.add(postDto1);

        given(postService.getPostList()).willReturn(postList);
        mockMvc.perform(get("/api/v1/post"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].title").value("title"))
                .andDo(print());
    }
}