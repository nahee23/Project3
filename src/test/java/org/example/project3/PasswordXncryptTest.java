package org.example.project3;

import org.example.project3.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PasswordXncryptTest {
    @Autowired
    private UserService userService;

    @Test
    public void encryptSingleUserPassword() {
        Long userId = 1L;
        String newPassword = "1234";

        userService.updateSingleUserPassword(userId, newPassword);
        System.out.println("비밀번호가 성공적으로 암호화되었습니다.");
    }
}
