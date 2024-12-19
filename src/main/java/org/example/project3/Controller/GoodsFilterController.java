package org.example.project3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.project3.DTO.GoodsDTO;
import org.example.project3.DTO.GoodsFilterDTO;
import org.example.project3.Service.GoodsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoodsFilterController {

    private final GoodsService goodsService;

    @GetMapping("/filterGoods")
    public String filterGoods(@ModelAttribute("filter")GoodsFilterDTO filter, Model model) {
        System.out.println(filter);
        List<GoodsDTO> list = goodsService.getFilterGoods(filter);
        model.addAttribute("goods", list);
        return "index2";
    }

    @GetMapping("/filterUnofficial")
    public String filterUnofficial(@ModelAttribute("filter")GoodsFilterDTO filter, Model model) {
        System.out.println(filter);
        List<GoodsDTO> list = goodsService.getFilterGoods(filter);
        model.addAttribute("goods", list);
        return "index";
    }
}
