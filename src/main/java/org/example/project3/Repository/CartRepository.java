package org.example.project3.Repository;

import org.example.project3.DTO.CartResponseDTO;
import org.example.project3.Entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndGoodsId(Long userId, Long goodsId);
    List<Cart> findAllByUserId(Long userId);

    Optional<Cart> findByCartIdAndUserId(Long cartId, Long userId);

    List<Cart> findByUserId(Long userId);
//
//    Cart findByCartIdAndGoodsId(Long cartId, Long id);
//
//    @Query("SELECT new org.example.project3.DTO.CartResponseDTO(c.cartId, g.id, g.title, g.price, c.quantity, c.totalPrice, u.name, g.imageFileName) " +
//            "FROM Cart c " +
//            "JOIN c.goods g " +
//            "JOIN c.user u " +
//            "WHERE c.cartId = :cartId")
//    List<CartResponseDTO> findCartResponseDTOList (Long cartId);
}
