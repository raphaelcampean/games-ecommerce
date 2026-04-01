package com.gamesecommerce.store.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.record.PaymentDTO;
import com.gamesecommerce.store.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequestMapping
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping("/orders/{id}/payment")
    public String postMethodName(@RequestBody String entity) {
        
        return entity;
    }

    @PostMapping("/api/create-payment-intent")
    public String createPaymentIntent(@RequestBody PaymentDTO paymentDTO) {
        Stripe.apiKey = System.getenv("STRIPE_SECRET_KEY");

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(paymentDTO.amount())
            .setCurrency("brl")
            .addPaymentMethodType("pix")
            .putMetadata("order_id", paymentDTO.orderId().toString())
            .build();

        try {
            PaymentIntent intent = PaymentIntent.create(params);
            return intent.getClientSecret(); 
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }
    
    @PostMapping("/api/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, sigHeader, System.getenv("STRIPE_WEBHOOK_SECRET"));

        ResponseEntity<Order> result = paymentService.processPayment(event);

        return ResponseEntity.ok("Webhook processado com sucesso");
    }
}
