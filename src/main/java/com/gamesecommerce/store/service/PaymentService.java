package com.gamesecommerce.store.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.repository.OrderRepository;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;

public class PaymentService {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    public ResponseEntity<Order> processPayment(Event event) {
        switch (event.getType()) {
            case "payment_intent.succeeded":
                return handlePaymentIntentSucceeded(event);
            case "payment_intent.payment_failed":
                return handlePaymentIntentFailed(event);
            default:
                System.out.println("Evento não tratado: " + event.getType());
        }
        return ResponseEntity.ok().body(null);
    }

    public ResponseEntity<Order> handlePaymentIntentSucceeded(Event event) {
        PaymentIntent intent = extracPaymentIntent(event);

        Order order = getOrderFromIntent(intent);
        order.setStatus(Order.Status.COMPLETED);
        orderRepository.save(order);

        System.out.println("Pagamento aprovado para o pedido: " + order.getId());

        return ResponseEntity.ok().body(order);
    }

    public ResponseEntity<Order> handlePaymentIntentFailed(Event event) {
        PaymentIntent intent = extracPaymentIntent(event);

        Order order = getOrderFromIntent(intent);
        order.setStatus(Order.Status.REFUSED);
        orderRepository.save(order);

        System.out.println("Pagamento falhou para o pedido: " + order.getId());

        return ResponseEntity.ok().body(order);
    }

    public PaymentIntent extracPaymentIntent(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
        return intent;
    }

    public Order getOrderFromIntent(PaymentIntent intent) {
        String orderIdStr = intent.getMetadata().get("order_id");
        UUID orderId = UUID.fromString(orderIdStr);

        Order order = orderRepository.findById(orderId).orElseThrow();
        
        return order;
    }
}
