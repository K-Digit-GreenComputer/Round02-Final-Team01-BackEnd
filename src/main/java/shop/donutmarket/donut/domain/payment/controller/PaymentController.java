package shop.donutmarket.donut.domain.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.donutmarket.donut.domain.payment.dto.PaymentInfoReq;
import shop.donutmarket.donut.domain.payment.dto.PaymentReq;
import shop.donutmarket.donut.domain.payment.service.PaymentService;
import shop.donutmarket.donut.global.auth.MyUserDetails;

@RestController
@RequiredArgsConstructor
public class PaymentController {
    
    private final PaymentService paymentService;

    @PostMapping("/payments")
    public ResponseEntity<?> insert(@RequestBody PaymentReq.insertDTO insertDTO) {
        // participant Id 받아오기 필요함
        paymentService.결제성공(insertDTO);
        return ResponseEntity.ok().body("결제 성공");
    }
}
