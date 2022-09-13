package com.ll.exam.app10;

import com.ll.exam.app10.app.home.controller.HomeController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional  // 이 테스트를 수행하며 생긴 DB 변동사항은 반영되지 않는다.
@AutoConfigureMockMvc
public class AppTests {

    @Autowired
    private MockMvc mvc;    // MockMvc == 호출기, 브라우저라고 생각하면 된다.
    // 특정 요청을 발생시킴

    @Test
    @DisplayName("메인화면에서는 안녕이 나와야 한다.")
    void t1() throws Exception {
        // when (GET /)
        ResultActions resultActions = mvc
                .perform(get("/"))
                .andDo(print());    // 콘솔에 출력됨

        // then (안녕)
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(HomeController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(content().string(containsString("안녕")));
    }
}