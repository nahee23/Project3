package org.example.project3.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private Long cartId;

    @ManyToOne (cascade = CascadeType.PERSIST)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "goodsId", nullable = false)
    private Goods goods;

    @Column(name = "quantity", nullable = false)
    private int quantity; //수량

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "totalPrice", nullable = false)
    private int totalPrice; // 합계 금액

    // 필드 초기화를 위한 생성자 추가
    public Cart(Goods goods, User user, int quantity, int price, int totalPrice) {
        this.goods = goods;
        this.user = user;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
    }

}
