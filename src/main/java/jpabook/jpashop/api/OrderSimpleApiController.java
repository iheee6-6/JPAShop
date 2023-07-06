package jpabook.jpashop.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/*xToone
Order
Order -> member
order -> delivery
* */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); //Lazy 강제 초기화
            order.getDelivery().getAddress(); //Lazy 강제 초기환
        }
        return all;// -> 무한 루프로 빠짐
    }


    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> ordersV2() {

        List<Order> orders = orderRepository.findAllByString(new OrderSearch());
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o)) //order를 dto로 바꿈
                .collect(Collectors.toList()); //리스트로 만듬
        return result;
    }

    @Data
    static class SimpleOrderDto {

        @JsonIgnore
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName(); //Lazy 초기화
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();//Lazy 초기화
        }
    }


    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() { //패치 조인
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        return orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(Collectors.toList());
    }


    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() { //패치 조인
        return orderRepository.findOrderDtos();
    }
}