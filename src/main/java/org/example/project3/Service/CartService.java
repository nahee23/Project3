package org.example.project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.project3.DTO.CartRequestDTO;
import org.example.project3.DTO.CartResponseDTO;
import org.example.project3.Entity.Cart;
import org.example.project3.Entity.Goods;
import org.example.project3.Entity.User;
import org.example.project3.Repository.CartRepository;
import org.example.project3.Repository.GoodsRepository;
import org.example.project3.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final GoodsRepository goodsRepository;
    private final UserRepository userRepository;



    // 장바구니에 상품 추가 또는 업데이트
    public void addOrUpdateCart(Long userId, Long goodsId, int quantity, int price) {

        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        // 상품 확인
        Goods goods = goodsRepository.findById(Math.toIntExact(goodsId))
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        System.out.println("goods: " + goods); // goods 객체 확인
        System.out.println("goodsId: " + goods.getId()); // goods ID 확인

        // 기존 장바구니 항목 조회
        Cart cart = cartRepository.findByUserIdAndGoodsId(userId, goodsId)
                .orElse(new Cart(goods, user, 0, goods.getPrice(), 0));

        System.out.println("cart: " + cart); // Cart 객체 확인

        // 수량 및 총 금액 업데이트
        cart.setQuantity(cart.getQuantity() + quantity); // 기존 수량 + 새로운 수량
        cart.setTotalPrice(cart.getQuantity() * price); // 합계 금액 = 수량 * 단가

        // 저장
        cartRepository.save(cart);
    }

    // 장바구니 항목 가져오기
    public List<CartResponseDTO> getCartItems(Long userId) {
        List<CartResponseDTO> cartItems = cartRepository.findAllByUserId(userId).stream()
                .map(cart -> new CartResponseDTO(
                        cart.getCartId(),
                        cart.getGoods().getId(),
                        cart.getGoods().getTitle(),
                        cart.getQuantity(),
                        cart.getTotalPrice(),
                        cart.getPrice(),
                        cart.getGoods().getImageFileName()
                ))
                .collect(Collectors.toList());

        if (cartItems.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
        }

        return cartItems;
    }

    // 장바구니 총 금액 계산
    public int getCartTotalPrice(Long userId) {
        return cartRepository.findAllByUserId(userId).stream()
                .mapToInt(Cart::getTotalPrice)
                .sum();
    }
//
//    // 장바구니 조회
//    public List<CartResponseDTO> getCartItems(Long userId) {
//        return cartRepository.findAllByUserId(userId).stream()
//                .map(cart -> new CartResponseDTO(
//                        cart.getCartId(),
//                        cart.getGoods().getId(),
//                        cart.getGoods().getTitle(),
//                        cart.getPrice(),
//                        cart.getQuantity(),
//                        cart.getTotalPrice()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    // 장바구니 항목 삭제
//    public void removeCartItem(Long userId, Long cartId) {
//        Cart cart = cartRepository.findById(cartId)
//                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다."));
//
//        if (!cart.getUser().getId().equals(userId)) {
//            throw new IllegalStateException("다른 사용자의 장바구니 항목입니다.");
//        }
//
//        cartRepository.delete(cart);
//    }

//public Long addCart(CartRequestDTO request, String email) { // 0408 수정 productId -> productMgtId
//
//    // 회원 정보
//    User user = userRepository.findByEmail(email)
//            .orElseThrow(() -> new NoSuchElementException("USER_NOT_FOUND"));
//    // 상품 정보
//    Goods goods = goodsRepository.findById(Math.toIntExact(request.getGoodsId()))
//            .orElseThrow(() -> new NoSuchElementException("PRODUCT_NOT_FOUND" + " goodsId: " + request.getGoodsId()));
//
//    // 회원, 상품 정보로 장바구니 객체 가져오기 - 없다면 null 반환
//    Cart cart = cartRepository.findByUserIdAndGoodsId(goods.getId(), user.getId()).orElse(null);
//    if (cart == null){
//        cart = Cart.createCart(user);
//        cartRepository.save(cart);
//    }
//
//    Cart savedCartItem =
//            cartRepository.findByCartIdAndGoodsId(cart.getCartId(),goods.getId());
//
//    if (savedCartItem != null){ //장바구니에 기존 상품이 있을 경우
//        savedCartItem.setQuantity(savedCartItem.getQuantity() + request.getQuantity());
//        savedCartItem.setPrice(savedCartItem.getPrice()+goods.getPrice()*request.getQuantity());
//
//        cartRepository.save(savedCartItem);
//
//        return  savedCartItem.getCartId();
//    }else {
//        int price = goods.getPrice()*request.getQuantity();
//
//        Cart cartItem= new Cart(user, goods, request.getQuantity(), price);
//
//        cartRepository.save(cartItem);
//
//        return  cartItem.getCartId();
//    }
//}
//
//public List<CartResponseDTO> getCartList(String email) {
//    List<CartResponseDTO> cartList = new ArrayList<>();
//    Optional<User> user = userRepository.findByEmail(email);
//    Cart cart = cartRepository.findByUserId(user.get().getId());
//    if (cart == null){
//        return cartList;
//    }
//
//    cartList = cartRepository.findCartResponseDTOList(cart.getCartId());
//
//    return cartList;
//}

}
