package com.ll.exam.fileExam.app.member.controller;

import com.ll.exam.fileExam.app.member.entity.Member;
import com.ll.exam.fileExam.app.member.service.MemberService;
import com.ll.exam.fileExam.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @PreAuthorize("isAnonymous()")
    @GetMapping("/join")
    public String memberJoin() {
        return "member/memberjoin_form";
    }

    // 로그인
    @PreAuthorize("isAnonymous()")
    @GetMapping("/login")
    public String showLogin() {
        return "member/login_form";
    }

    @PreAuthorize("isAnonymous()")
    @PostMapping("/join")
    public String memberJoin(HttpServletRequest req, String username, String password, String email, MultipartFile profileImg) {
        Member oldMember = memberService.getMemberByUserName(username);

        String passwordClearText = password;
        password = passwordEncoder.encode(password);

        if (oldMember != null) {
            return "redirect:/?errorMsg=Already done.";
        }

        Member member = memberService.join(username, password, email, profileImg);

        try {
            req.login(username, passwordClearText);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        return "redirect:/member/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public String showProfile() {
        return "member/profile";
    }

//    @GetMapping("/profile/img/{id}")
//    public String showProfileImg(@PathVariable Long id) {
//        return "redirect:" + memberService.getMemberById(id).getProfileImgUrl();
//    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String showModify() {
        return "member/modify";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modify(@AuthenticationPrincipal MemberContext context, String email, MultipartFile profileImg, String profileImg__delete) {
        Member member = memberService.getMemberById(context.getId());

        if ( profileImg__delete != null && profileImg__delete.equals("Y") ) {
            memberService.removeProfileImg(member);
        }

        memberService.modify(member, email, profileImg);

        // 기존에 세션에 저장된 MemberContext 객체의 내용을 수정하는 코드
        context.setModifyDate(member.getModifyDate());
        Authentication authentication = new UsernamePasswordAuthenticationToken(context, member.getPassword(), context.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return "redirect:/member/profile";
    }

    @GetMapping("/profile/img/{id}")
    public ResponseEntity<Object> showProfileImg(@PathVariable Long id) throws URISyntaxException {
        String profileImgUrl = memberService.getMemberById(id).getProfileImgUrl();

        if ( profileImgUrl == null ) {
            profileImgUrl = "https://via.placeholder.com/100x100.png?text=U_U";
        }

        URI redirectUri = new URI(profileImgUrl);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(redirectUri);
        httpHeaders.setCacheControl(CacheControl.maxAge(60 * 60 * 1, TimeUnit.SECONDS));
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }
}
