package com.ll.exam.app10.app.member.controller;

import com.ll.exam.app10.app.member.entity.Member;
import com.ll.exam.app10.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/join")
    public String memberJoin() {
        return "member/memberjoin_form";
    }

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
    public String memberProfile(Principal principal, Model model) {
        Member loginedMember = memberService.getMemberByUserName(principal.getName());

        model.addAttribute("loginedMember", loginedMember);

        return "member/profile";
    }
}
