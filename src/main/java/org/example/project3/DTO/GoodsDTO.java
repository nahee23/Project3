package org.example.project3.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.example.project3.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;


@Getter
@Setter
public class GoodsDTO {

    private Long id;

    @NotEmpty(message = "제품이름을 입력하세요")
    private String title;
    @Size(min = 10, message = "제품설명은 10자 이상이여야 합니다")
    @Size(max = 1000,message = "제품설명은 1000자 이하여야 합니다")
    private String description;
    //@NotEmpty
    private String category;
    @Min(0)
    private int price;

    private MultipartFile imageFile;

    private User user;

    private String imageFileName;

    private Date createAt;

}
