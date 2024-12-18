package org.example.project3.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequestDTO {
    @NotNull
    private Long goodsId;

    @Min(1)
    private int quantity;

    @Min(0)
    private int price;

    public CartRequestDTO(Long goodsId, int quantity) {
    }
}
