package com.jackass.RestAPI.controller;

import com.jackass.RestAPI.entity.Order;
import com.jackass.RestAPI.entity.OrderInfo;
import com.jackass.RestAPI.entity.Product;
import com.jackass.RestAPI.exception.NotFoundException;
import com.jackass.RestAPI.repository.OrderInfoRepository;
import com.jackass.RestAPI.repository.OrderRepository;
import com.jackass.RestAPI.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/orders/info")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderInfoController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderInfoRepository orderInfoRepository;
    @Autowired
    private ProductRepository productRepository;

    private void recalculateCost(int orderId, int multiplier) {
        Order order = orderRepository.getOrderById(orderId);
        Set<OrderInfo> products = order.getProducts();
        int totalCost = 0;
        for (OrderInfo of : products) {
            totalCost += of.getAmount() * of.getProduct().getCost();
        }
        totalCost *= multiplier;
        order.setTotalCost(totalCost);
        orderRepository.save(order);
    }

    private void recalculateCost(int orderId) {
        recalculateCost(orderId, 1);
    }

    //
    // GET
    //
    @RequestMapping(
            value = "id={id}",
            method = RequestMethod.GET
    )
    public ResponseEntity<?> getProducts(@PathVariable int id) {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new NotFoundException("Wrong order ID.");
        }
        
        return ResponseEntity.ok().body(order.getProducts());
    }
    
    //
    //  POST
    //
    @RequestMapping(
            value = "orderId={}&productId={}&amount={amount}",
            method = RequestMethod.POST
    )
    public void addProduct(@PathVariable int orderId,
                           @PathVariable int productId,
                           @PathVariable int amount) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Wrong order ID.");
        }

        Product product = productRepository.getProductById(productId);
        if (product == null) {
            throw new NotFoundException("Wrong product ID.");
        }

        Set<OrderInfo> products = order.getProducts();

        if (products != null) {
            OrderInfo elem = products.stream()
                    .filter(orderInfo -> orderInfo.getProduct().getId() == productId)
                    .findFirst()
                    .orElse(null);
            if (elem == null) {
                elem = new OrderInfo();
                elem.setOrderId(orderId);
                elem.setAmount(amount);
                elem.setProduct(product);
            } else {
                elem.setAmount(elem.getAmount() + amount);
            }
            orderInfoRepository.save(elem);
        } else {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderId(orderId);
            orderInfo.setProduct(product);
            orderInfo.setAmount(amount);
            orderInfoRepository.save(orderInfo);
        }
        recalculateCost(orderId);
    }

    //
    //  DELETE
    //
    @RequestMapping(
            value = "orderId={orderId}&productId={productId}",
            method = RequestMethod.DELETE
    )
    public void deleteFromOrder(@PathVariable int orderId,
                                @PathVariable int productId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Wrong order ID.");
        }

        Set<OrderInfo> products = order.getProducts();
        if (products == null) {
            throw new NotFoundException("Order does not have a products.");
        }

        Product product = productRepository.getProductById(productId);
        if (product == null) {
            throw new NotFoundException("Wrong product ID.");
        }

        OrderInfo elem = products
                .stream()
                .filter(orderInfo -> orderInfo.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);
        if (elem == null) {
            throw new NotFoundException("Order does not have such product.");
        }
        orderInfoRepository.delete(elem);
        recalculateCost(orderId);
    }

    @RequestMapping(
            value = "orderId={orderId}",
            method = RequestMethod.DELETE
    )
    public void deleteOrderInfo(@PathVariable int orderId) {
        Order order = orderRepository.getOrderById(orderId);
        if (order == null) {
            throw new NotFoundException("Wrong order ID.");
        }

        Set<OrderInfo> products = order.getProducts();
        if (products == null) {
            throw new NotFoundException("Order does not have a products in it.");
        }
        for (OrderInfo of : products) {
            orderInfoRepository.delete(of);
        }
        order.setTotalCost(0);
        orderRepository.save(order);
    }
}
