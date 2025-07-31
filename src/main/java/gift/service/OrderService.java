package gift.service;

import gift.client.KakaoMessageClient;
import gift.dto.OrderRequestDto;
import gift.dto.OrderResponseDto;
import gift.entity.Member;
import gift.entity.Option;
import gift.entity.Order;
import gift.exception.OptionNotFoundException;
import gift.repository.OptionRepository;
import gift.repository.OrderRepository;
import gift.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OptionRepository optionRepository;
    private final WishlistRepository wishlistRepository;
    private final KakaoMessageClient kakaoMessageClient;

    public OrderService(OrderRepository orderRepository, OptionRepository optionRepository,
            WishlistRepository wishlistRepository, KakaoMessageClient kakaoMessageClient) {
        this.orderRepository = orderRepository;
        this.optionRepository = optionRepository;
        this.wishlistRepository = wishlistRepository;
        this.kakaoMessageClient = kakaoMessageClient;
    }
    @Transactional
    public OrderResponseDto createOrder(Member member, OrderRequestDto request) {

        Option option = optionRepository.findById(request.optionId())
                .orElseThrow(OptionNotFoundException::new);

        option.subtract(request.quantity());

        Order order = new Order(member, option, request.quantity(), request.message());
        orderRepository.save(order);

        wishlistRepository.deleteByMemberAndProduct(member, option.getProduct());

        kakaoMessageClient.sendOrderMessage(member, order);

        return new OrderResponseDto(
                order.getId(),
                option.getId(),
                order.getQuantity(),
                order.getOrderDateTime(),
                order.getMessage()
        );
    }

}
