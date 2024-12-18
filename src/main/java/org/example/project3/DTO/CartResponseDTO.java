package org.example.project3.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponseDTO {
    private Long cartId;       // 장바구니 ID
    private Long goodsId;      // 상품 ID
    private String goodsTitle;  // 상품 이름 (Goods 엔티티에서 가져옴)
    private int price;         // 단가
    private int quantity;      // 수량
    private int totalPrice;    // 합계 금액
    private String imageFileName;
    private String userName;

    public CartResponseDTO(Long cartId, Long goodsId, String goodsTitle, int price, int quantity, int totalPrice, String userName, String imageFileName) {
        this.cartId = cartId;
        this.goodsId = goodsId;
        this.goodsTitle = goodsTitle;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.userName = userName;
        this.imageFileName = imageFileName; // 이미지 파일 이름 설정
    }

    public CartResponseDTO(Long cartId, Long id, String title, int quantity, int totalPrice) {
    }

    public CartResponseDTO(Long cartId, Long id, String title, int quantity, int totalPrice, int price, String imageFileName) {
    }
}