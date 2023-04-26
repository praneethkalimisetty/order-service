package std.application.order.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import std.application.order.dto.OrderLineItemsDto;
import std.application.order.dto.OrderRequest;
import std.application.order.model.Order;
import std.application.order.model.OrderLineItems;
import std.application.order.repository.OrderRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	public void placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
				.map(orderLineItemsDto -> mapToDto(orderLineItemsDto)).collect(Collectors.toList());

		order.setOrderLineItemsList(orderLineItems);

		log.info(orderLineItems.toString());
		log.info(order.toString());
		orderRepository.save(order);
		log.info("Order saved successfully");
	}

	private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
