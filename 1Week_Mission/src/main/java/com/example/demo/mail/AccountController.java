package com.example.demo.mail;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class AccountController {
    /* view에서 사용자가 메일 인증 버튼을 누르는 순간
    ajax를 통해 mailConfirm() 이 실행되고 , 넘어온 메일로 인증코드가 발송된다.
    메일로 보내졌던 인증코드는 다시 return 되어서 view로 돌아간다.
    * */


    private final EmailService emailService;

    @PostMapping("mailConfirm")
    @ResponseBody
    public String mailConfirm( String email )throws Exception {
        String code = emailService.sendSimpleMessage(email);
        log.info("인증코드 : " + code);
        return code;
    }
}