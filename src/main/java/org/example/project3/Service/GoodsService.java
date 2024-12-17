package org.example.project3.Service;

import lombok.RequiredArgsConstructor;
import org.example.project3.DTO.GoodsDTO;
import org.example.project3.Entity.Category;
import org.example.project3.Entity.Goods;
import org.example.project3.Repository.GoodsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsRepository gRepo;
    private final UserService uService;
    private final ModelMapper modelMapper;

    public GoodsDTO saveGoodsDetails (GoodsDTO goodsDTO) {
        Goods goods = mapToEntity(goodsDTO);
        goods.setUser(uService.getLoggedInUser());
        goods = gRepo.save(goods);
        return mapToDTO(goods);
    }

    private Goods mapToEntity(GoodsDTO goodsDTO) {
        Goods goods = modelMapper.map(goodsDTO, Goods.class);
        return goods;
    }

    private GoodsDTO mapToDTO(Goods goods) {
        GoodsDTO goodsDTO = modelMapper.map(goods, GoodsDTO.class);
        goodsDTO.setImageFileName(goods.getImageFileName());
        goodsDTO.setCreateAt(goods.getCreateAt());
        return goodsDTO;
    }

    public List<GoodsDTO> getOfficialGoods(){
        List<Goods> officialGoods = gRepo.findByCategory(Category.valueOf("OFFICIAL"));
        return officialGoods.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<GoodsDTO> getUnofficialGoods() {
        List<Goods> unofficialGoods = gRepo.findByCategory(Category.valueOf("UNOFFICIAL"));
        return unofficialGoods.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
}
