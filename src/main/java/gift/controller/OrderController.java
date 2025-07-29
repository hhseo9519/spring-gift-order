package gift.controller;

import gift.dto.OrderRequestDto;
import gift.dto.OrderResponseDto;
import gift.entity.Member;
import gift.security.LoginMember;
import gift.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(
            @RequestBody @Valid OrderRequestDto request,
            @LoginMember Member member
    ){
        OrderResponseDto response = orderService.createOrder(member, request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

}
