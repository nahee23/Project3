package org.example.project3.Controller;

import jakarta.validation.Valid;
import org.example.project3.DTO.GoodsDTO;
import org.example.project3.Entity.Category;
import org.example.project3.Entity.Goods;
import org.example.project3.Repository.GoodsRepository;
import org.example.project3.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsRepository gRepo;

    @Autowired
    private UserService uService;

    @GetMapping({"","/"})
    public String showGoodsList(Model model) {
        List<Goods> goods = gRepo.findAll();
        model.addAttribute("goods", goods);
        return "Main";
    }

    @GetMapping("/sale")
    public String showSaleList(Model model) {
        List<Goods> goods = gRepo.findAll();
        model.addAttribute("goods", goods);
        return "index";
    }

    @GetMapping("/official")
    public String showOfficialList(Model model) {
        List<Goods> goods = gRepo.findAll();
        model.addAttribute("goods", goods);
        return "index2";
    }

    @GetMapping("/create/official")
    public String createOfficial(Model model) {
        GoodsDTO goodsDTO = new GoodsDTO();
        model.addAttribute("goodsDTO", goodsDTO);
        return "createGoods";
    }
    @PostMapping("/create/official")
    public String CreatOfficialProduct(@Valid @ModelAttribute GoodsDTO goodsDTO,
                               BindingResult result) {

        if (goodsDTO.getImageFile() == null || goodsDTO.getImageFile().isEmpty()) {
            result.addError(new FieldError("goodsDTO", "imageFile", "Image file is required"));
            System.out.println("No file uploaded or file is empty!");
        }

        if (result.hasErrors()) {
            System.out.println(result);
            return "createGoods";
        }

        //에러가 없을시 새 제품데이터를 저장
        MultipartFile image = goodsDTO.getImageFile();
        System.out.println("Uploading file: " + image.getOriginalFilename());
        System.out.println("File size: " + image.getSize());

        Date createDate = new Date();
        String storeFileName = createDate.getTime() + "_" + image.getOriginalFilename();
        //이미지를 public/images 폴더에 저장
        String uploadDir = null;
        try {
            uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
                System.out.println("Directory created at: " + uploadPath.toAbsolutePath());
            }  else {
                System.out.println("Directory already exists at: " + uploadPath.toAbsolutePath());
            }
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = Paths.get(uploadDir + storeFileName);
                System.out.println("Saving file to: " + filePath.toAbsolutePath());
                Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File successfully saved.");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Goods goods = new Goods();
        goods.setTitle(goodsDTO.getTitle());
        goods.setCategory(Category.OFFICIAL);
        goods.setPrice(goodsDTO.getPrice());
        goods.setDescription(goodsDTO.getDescription());
        goods.setImageFileName(storeFileName);
        goods.setUser(uService.getLoggedInUser());

        gRepo.save(goods);

        return "redirect:/goods";
    }

    @GetMapping("/create/unofficial")
    public String showCreatePage(Model model) {
        GoodsDTO goodsDTO = new GoodsDTO();
        model.addAttribute("goodsDTO", goodsDTO);
        return "createGoods";
    }

    @PostMapping("/create/unofficial")
    public String CreatProduct(@Valid @ModelAttribute GoodsDTO goodsDTO,
                               BindingResult result) {

        if (goodsDTO.getImageFile() == null || goodsDTO.getImageFile().isEmpty()) {
            result.addError(new FieldError("goodsDTO", "imageFile", "Image file is required"));
            System.out.println("No file uploaded or file is empty!");
        }

        if (result.hasErrors()) {
            System.out.println(result);
            return "createGoods";
        }

        //에러가 없을시 새 제품데이터를 저장
        MultipartFile image = goodsDTO.getImageFile();
        System.out.println("Uploading file: " + image.getOriginalFilename());
        System.out.println("File size: " + image.getSize());

        Date createDate = new Date();
        String storeFileName = createDate.getTime() + "_" + image.getOriginalFilename();
        //이미지를 public/images 폴더에 저장
        String uploadDir = null;
        try {
            uploadDir = "public/images/";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectory(uploadPath);
                System.out.println("Directory created at: " + uploadPath.toAbsolutePath());
            }  else {
                System.out.println("Directory already exists at: " + uploadPath.toAbsolutePath());
            }
            try (InputStream inputStream = image.getInputStream()) {
                Path filePath = Paths.get(uploadDir + storeFileName);
                System.out.println("Saving file to: " + filePath.toAbsolutePath());
                Files.copy(inputStream, Paths.get(uploadDir + storeFileName), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File successfully saved.");
            }

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        Goods goods = new Goods();
        goods.setTitle(goodsDTO.getTitle());
        goods.setCategory(Category.UNOFFICIAL);
        goods.setPrice(goodsDTO.getPrice());
        goods.setDescription(goodsDTO.getDescription());
        goods.setImageFileName(storeFileName);
        goods.setUser(uService.getLoggedInUser());

        gRepo.save(goods);

        return "redirect:/goods";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        try {
            Goods goods = gRepo.findById(id).get();
            model.addAttribute("goods", goods);

            GoodsDTO goodsDTO = new GoodsDTO();
            goodsDTO.setTitle(goods.getTitle());
            goodsDTO.setCategory(String.valueOf(goods.getCategory()));
            goodsDTO.setPrice(goods.getPrice());
            goodsDTO.setDescription(goods.getDescription());

            model.addAttribute("goodsDTO", goodsDTO);

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        return "goods/editGoods";
    }

    //수정하기
    @PostMapping("/edit")
    public String editProduct(@Valid GoodsDTO goodsDTO, BindingResult bindingResult,
                              @RequestParam int id, Model model) {

        Goods goods = gRepo.findById(id).get();
        model.addAttribute("goods", goods);

        if (bindingResult.hasErrors()) {
            return "goods/editGoods";
        }
        //수정할 이미지 있으면 기존이미지 삭제하고 수정 이미지를 업로드함
        if(!goodsDTO.getImageFile().isEmpty()){
            String uploadDir = "public/images/";
            Path oldImagePath = Paths.get(uploadDir + goods.getImageFileName());

            try {
                Files.delete(oldImagePath);
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
            }
            //새 이미지 업로드
            MultipartFile image = goodsDTO.getImageFile();
            Date createDate = new Date();
            String storeFileName = createDate.getTime() + "_" + image.getOriginalFilename();

            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir+storeFileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            goods.setImageFileName(storeFileName); //이미지 파일 이름을 업데이트
        }
        //이미지 제외한 수정 내용을 업데이트
        goods.setTitle(goodsDTO.getTitle());
        goods.setCategory(Category.valueOf(goodsDTO.getCategory()));
        goods.setPrice(goodsDTO.getPrice());
        goods.setDescription(goodsDTO.getDescription());

        gRepo.save(goods); //수정이 완료된 제품객체로 DB 업데이트함

        return "redirect:/goods";
    }

    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {

        try {
            Goods goods = gRepo.findById(id).get();
            //이미지 파일 삭제하기
            String uploadDir = "public/images/";
            Path imagePath = Paths.get(uploadDir + goods.getImageFileName());

            try {
                Files.delete(imagePath);
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
            // 제품 삭제
            gRepo.delete(goods);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return "redirect:/goods";
    }

    @GetMapping("/view")
    public String showGoodsView(Model model, @RequestParam int id) {
        Goods goods = gRepo.findById(id).get();
        model.addAttribute("goods", goods);
        return "view";
    }
}
