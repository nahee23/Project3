package org.example.project3.Controller;

import jakarta.validation.Valid;
import org.example.project3.DTO.CartRequestDTO;
import org.example.project3.DTO.CartResponseDTO;
import org.example.project3.Entity.Goods;
import org.example.project3.Repository.GoodsRepository;
import org.example.project3.Service.CartService;
import org.example.project3.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final GoodsRepository gRepo;

    public CartController(CartService cartService, UserService userService, GoodsRepository gRepo) {
        this.cartService = cartService;
        this.userService = userService;
        this.gRepo = gRepo;
    }

//    @PostMapping("/add")
//    public ResponseEntity<String> addCart(@Valid @RequestBody CartRequestDTO request,
//                                          @RequestParam int id,Principal principal) {
//        Goods goods = gRepo.findById(id).get();
//        String email = principal.getName();
//        Long createdId = cartService.addCart(request, email);
//
//        return ResponseEntity.ok("장바구니에 등록되었습니다. cart_id : " + createdId);
//    }
//
//    @GetMapping("/{memberId}")
//    public List<CartResponseDTO> getMyCarts(@PathVariable Long memberId) {
//        return cartService.getCartList(String.valueOf(memberId));
//    }

    // 장바구니에 상품 추가
    @PostMapping("/add")
    public String addCartItem(
            @RequestParam Long goodsId,
            @RequestParam int quantity,
            @RequestParam int price,
            Principal principal,
            Model model) {

        System.out.println("goodsId: " + goodsId); // 디버깅 출력
        System.out.println("quantity: " + quantity);
        System.out.println("price: " + price);

        // 1. 로그인한 사용자 이메일 및 ID 조회
        if (principal == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        String email = principal.getName(); // 사용자 이메일 가져오기
        Long userId = userService.getUserIdByEmail(email); // 이메일로 User ID 조회

        // 2. 장바구니에 상품 추가
        cartService.addOrUpdateCart(userId, goodsId, quantity, price);

        // 3. 장바구니 페이지 데이터 준비
        model.addAttribute("cartItems", cartService.getCartItems(userId)); // 장바구니 항목
        model.addAttribute("cartTotalPrice", cartService.getCartTotalPrice(userId)); // 총 금액

        // 4. 장바구니 페이지로 이동
        return "cart"; // templates/cart.html 렌더링
    }

    // 장바구니 페이지 렌더링
    @GetMapping
    public String getCartPage(Principal principal, Model model) {
        if (principal == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        String email = principal.getName();
        Long userId = userService.getUserIdByEmail(email);

        // userId가 잘못된 값이 아닌지 확인
        System.out.println("로그인한 사용자 ID: " + userId);

        List<CartResponseDTO> cartItems = cartService.getCartItems(userId);
        System.out.println("장바구니 항목: " + cartItems);

        model.addAttribute("cartItems", cartService.getCartItems(userId));
        model.addAttribute("cartTotalPrice", cartService.getCartTotalPrice(userId));

        return "cart"; // templates/cart.html 렌더링
    }



//
//    // 장바구니 항목 추가/수정
//    @PostMapping
//    public ResponseEntity<CartResponseDTO> addOrUpdateCartItem(
//            @RequestHeader("userId") Long userId,
//            @RequestBody CartRequestDTO requestDTO) {
//        CartResponseDTO responseDTO = cartService.addOrUpdateCart(userId, requestDTO);
//        return ResponseEntity.ok(responseDTO);
//    }
//
//    // 장바구니 조회
//    @GetMapping
//    public ResponseEntity<List<CartResponseDTO>> getCartItems(Principal principal) {
//        String email = principal.getName(); // 현재 사용자 ID
//        Long userId = userService.getUserIdByEmail(email);
//        List<CartResponseDTO> cartItems = cartService.getCartItems(userId);
//        return ResponseEntity.ok(cartItems);
//
//    }
//
//    // 장바구니 항목 삭제
//    @DeleteMapping("/{cartId}")
//    public ResponseEntity<Void> removeCartItem(
//            @RequestHeader("userId") Long userId,
//            @PathVariable Long cartId) {
//        cartService.removeCartItem(userId, cartId);
//        return ResponseEntity.noContent().build();
//    }

}
