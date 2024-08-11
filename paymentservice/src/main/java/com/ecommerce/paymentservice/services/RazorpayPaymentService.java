package com.ecommerce.paymentservice.services;

import com.ecommerce.paymentservice.configs.RazorpayConfig;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class RazorpayPaymentService implements PaymentService {

    private RazorpayConfig razorpayConfig;

    @Autowired
    public RazorpayPaymentService(RazorpayConfig razorpayConfig) {
        this.razorpayConfig = razorpayConfig;
    }

    @Override
    public String createPaymentLink(String orderId) throws RazorpayException {
        RazorpayClient razorpayClient = razorpayConfig.createRazorpayClient();
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",1000);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
//        paymentLinkRequest.put("first_min_partial_amount",100);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis()+15*60*1000);
        paymentLinkRequest.put("reference_id",orderId);
        paymentLinkRequest.put("description","Payment for order no "+ orderId);

        // Order order = orderService.getOrderDetails(orderId)
        // String number = order.getUser().getMobileNumber
        // String email = order.getUser().getEmail


        JSONObject customer = new JSONObject();
        customer.put("name","+919664533828");
        customer.put("contact","Raju Gundu");
        customer.put("email","rajugundu849@gmail.com");
        paymentLinkRequest.put("customer",customer);

        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        JSONObject notes = new JSONObject();
        notes.put("policy_name","Iphone 15 pro max");
        paymentLinkRequest.put("notes",notes);
        paymentLinkRequest.put("callback_url","https://google.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        return payment.get("short_url");
    }

    @Override
    public String getPaymentStatus(String paymentId) {
        return "";
    }
}
