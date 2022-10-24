package com.example.demo.member;

import com.example.demo.auth.PrincipalDetails;
import com.example.demo.mail.EmailService;
import com.example.demo.member.model.Member;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/member")
@AllArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;

    @PostMapping("/emailConfirm")
    public String emailConfirm(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }
    @GetMapping("/denied")
    public String accessDenied(@RequestParam(value = "exception",required = false) String exception,
                               Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Member member = (Member)authentication.getPrincipal();
        model.addAttribute("username",member.getUsername());
        model.addAttribute("exception",exception);
        return "member/denied";
    }

    @GetMapping("/login")
    public String login(){
        return "member/loginform";
    }

    @GetMapping("/join")
    public String join(){
        return "member/joinForm2";
    }

    @PostMapping("/join")
    public String joinPost( JoinForm joinForm) {
        String rawPassword = joinForm.getPassword();
        memberService.join(joinForm);

        return  "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify")
    public String modify(@AuthenticationPrincipal PrincipalDetails principalDetails){
         return "member/modify";
    }

    @PostMapping("/modify")
    public String modifyPost(@AuthenticationPrincipal PrincipalDetails principalDetails, ModifyForm modifyForm){
        memberService.modify(principalDetails,modifyForm);
        return  "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("modifyPassword")
    public String modifyPassword(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        String password = principalDetails.getPassword();

        model.addAttribute("password",password);
        System.out.println(password);
        return "member/modifyPassword";
    }

    @PostMapping("modifyPassword")
    public String modifyPasswordPost(@AuthenticationPrincipal PrincipalDetails principalDetails,@RequestParam String new_password){
        memberService.modifyPassword(principalDetails,new_password);
        return  "redirect:/";
    }

    @GetMapping("/findUsername")
    public String findUserName(){
        return "member/findUsername";
    }

    @PostMapping("/findUsername")
    public String foundUsername(@RequestParam String email, Model model){
        String username = memberService.findUsernameByEmail(email);
        model.addAttribute("msg", "회원님의 아이디는 " + username + "입니다.");

        return "/alert";
    }

    @GetMapping("/findPassword")
    public String findPassword(){
        return "member/findPassword";
    }

    @PostMapping("/findPassword")
    public String foundPassword(@RequestParam String username,@RequestParam String email, Model model){
        String password = "1234a";
        memberService.setTemporaryPassword(username,email,password);

        model.addAttribute("msg",
                "회원님의 임시 비밀번호는 " + password + "입니다. 로그인 후 비밀번호를 변경해주세요");

        return "member/alert";
    }

//    @ExceptionHandler(Exception.class)
//    public void catcher(Exception ex, HttpServletResponse response) throws IOException {
//        response.setContentType("text/html; charset=utf-8");
//        response.getWriter().print("<script>alert('로그인 후 이용해주세요');  location.href = \"/member/login\";</script>");
//
//    }


}