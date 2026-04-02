package com.gamesecommerce.store.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gamesecommerce.store.model.Order;
import com.gamesecommerce.store.repository.OrderRepository;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentService {
    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductService productService;

    public void processPayment(Event event) {
        switch (event.getType()) {
            case "payment_intent.succeeded":
                handlePaymentIntentSucceeded(event);
                break;
            case "payment_intent.payment_failed":
                handlePaymentIntentFailed(event);
                break;
            default:
                System.out.println("Evento não tratado: " + event.getType());
        }
    }

    public void handlePaymentIntentSucceeded(Event event) {
        PaymentIntent intent = extracPaymentIntent(event);

        Order order = getOrderFromIntent(intent);
        order.setStatus(Order.Status.COMPLETED);
        orderRepository.save(order);

        System.out.println("Pagamento aprovado para o pedido: " + order.getId());
    }

    public void handlePaymentIntentFailed(Event event) {
        PaymentIntent intent = extracPaymentIntent(event);

        Order order = getOrderFromIntent(intent);
        order.setStatus(Order.Status.REFUSED);
        orderRepository.save(order);

        System.out.println("Pagamento falhou para o pedido: " + order.getId());
    }

    public PaymentIntent extracPaymentIntent(Event event) {
        PaymentIntent intent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
        return intent;
    }

    public Order getOrderFromIntent(PaymentIntent intent) {
        String orderIdStr = intent.getMetadata().get("order_id");

        if (orderIdStr == null) {
            throw new IllegalArgumentException("ID do pedido não encontrado no metadata do PaymentIntent");
        }

        UUID orderId = UUID.fromString(orderIdStr);

        Order order = orderRepository.findById(orderId).orElseThrow();
        
        return order;
    }
}
