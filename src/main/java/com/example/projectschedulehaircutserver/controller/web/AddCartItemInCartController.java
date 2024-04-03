package com.example.projectschedulehaircutserver.controller.web;

import com.example.projectschedulehaircutserver.service.cart.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/web")
@AllArgsConstructor
public class AddCartItemInCartController {
    private final CartService cartService;

    @PostMapping("/add/combo")
    public ResponseEntity<?> addCombo(@RequestBody Integer comboId, Integer customerId){
        try {
            return ResponseEntity.ok("Thêm Combo Vào Giỏ Hàng Thành Công");
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
