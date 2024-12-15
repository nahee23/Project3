package org.example.project3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.project3.DTO.UserDTO;
import org.example.project3.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService uService;

    @GetMapping({"/login","/"})
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register (@Valid @ModelAttribute("user") UserDTO user, Model model, BindingResult result) {
        System.out.println("유저DTO객체 : " + user);
        if (result.hasErrors()) {
            return "register";
        }
        uService.save(user);
        model.addAttribute("successMsg",true);
        return "redirect:/login";
    }
}
