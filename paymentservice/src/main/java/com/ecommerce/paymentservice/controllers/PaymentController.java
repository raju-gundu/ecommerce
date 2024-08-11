package com.ecommerce.paymentservice.controllers;

import com.ecommerce.paymentservice.DTO.CreatePaymentLinkRequestDTO;
import com.ecommerce.paymentservice.services.PaymentService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/")
    public String createPaymentLink(@RequestBody CreatePaymentLinkRequestDTO request) throws RazorpayException {

        String link = paymentService.createPaymentLink(request.getOrderId());
        return link;
    }
}
