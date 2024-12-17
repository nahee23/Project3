package org.example.project3.Exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(GoodsNotFoundException.class)
    public String handleGoodsNotFoundException(HttpServletRequest req, GoodsNotFoundException ex, Model model) {
        model.addAttribute("notfound", true);
        model.addAttribute("message", ex.getMessage());
        return "response";
    }

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(HttpServletRequest req, Exception ex, Model model) {
        model.addAttribute("serverError", true);
        model.addAttribute("message", ex.getMessage());
        return "response";
    }
}
