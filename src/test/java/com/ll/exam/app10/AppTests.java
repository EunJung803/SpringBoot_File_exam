package com.ll.exam.app10;

import com.ll.exam.app10.app.home.controller.HomeController;
import com.ll.exam.app10.app.member.controller.MemberController;
import com.ll.exam.app10.app.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional  // 이 테스트를 수행하며 생긴 DB 변동사항은 반영되지 않는다.
@AutoConfigureMockMvc
@ActiveProfiles({"base-addi", "test"})
public class AppTests {

    @Autowired
    private MockMvc mvc;    // MockMvc == 호출기, 브라우저라고 생각하면 된다.
    // 특정 요청을 발생시킴

    @Autowired
    private MemberService memberService;

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

    @Test
    @DisplayName("회원의 수")
    void t2() throws Exception {
        long count = memberService.count();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    @DisplayName("user1로 로그인 후 프로필페이지에 접속하면 user1의 이메일이 보여야 한다.")
    void t3() throws Exception {
        // mockMvc로 로그인 처리
        // WHEN
        // GET /member/profile
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile")
                                .with(user("user1").password("1234").roles("user"))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("memberProfile"))
                .andExpect(content().string(containsString("user1@test.com")));
    }

    @Test
    @DisplayName("user4로 로그인 후 프로필페이지에 접속하면 user4의 이메일이 보여야 한다.")
    void t4() throws Exception {
        // mockMvc로 로그인 처리
        // WHEN
        // GET /member/profile
        ResultActions resultActions = mvc
                .perform(
                        get("/member/profile")
                                .with(user("user4").password("1234").roles("user"))
                )
                .andDo(print());

        // THEN
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(handler().handlerType(MemberController.class))
                .andExpect(handler().methodName("memberProfile"))
                .andExpect(content().string(containsString("user4@test.com")));
    }
}