package org.example.project3.DTO;

import lombok.*;

@Getter
@Setter
public class UserDTO {

    //@NotBlank(message = "이름을 작성해주세요")
    private String name;
    //@NotBlank(message = "이메일을 입력해주세요")
    //@Email(message = "이메일 형식이 아닙니다")
    private String email;
    //@NotBlank(message = "비밀번호를 입력해주세요")
    //@Size(min = 4, message = "비밀번호는 4자이상 작성해주세요")
    private String password;

    private String confirmPassword; //DTO에만
}
