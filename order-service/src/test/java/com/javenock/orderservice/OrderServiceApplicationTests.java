package com.javenock.orderservice;

import com.javenock.orderservice.model.ProductRequest;
import com.javenock.orderservice.repository.OrderDetailsRepositoryTest;
import com.javenock.orderservice.repository.OrderRepository;
import com.javenock.orderservice.request.OrderRequest;
import com.javenock.orderservice.utils.ArithmeticUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderServiceApplicationTests {

	@LocalServerPort
	private int port;

	private String baseUrl = "http://localhost";

	private static RestTemplate restTemplate;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderDetailsRepositoryTest orderDetailsRepositoryTest;

	@BeforeAll
	public static void init(){
		restTemplate = new RestTemplate();
	}

	@BeforeEach
	public void setUp(){
		baseUrl = baseUrl.concat(":").concat(port+"").concat("/orders");
	}

	@Test
	@Sql(statements = "DELETE FROM ORDERS WHERE order_number = '1000'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	@Sql(statements = "DELETE FROM ORDER_DETAILS WHERE order_number = '1000'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void testMakeOrder(){
		List<ProductRequest> prods = new ArrayList<>();
		ProductRequest productRequest = new ProductRequest("S10_2016", 2L, 10.0);
		ProductRequest productRequest_1 = new ProductRequest("S10_1678", 10L, 120.0);
		prods.add(productRequest);
		prods.add(productRequest_1);

		OrderRequest orderRequest = OrderRequest.builder()
				.orderNumber("1000")
				.comment("in demand")
				.customerNumber(1234L)
				.productRequestList(prods)
				.build();
		List<String> productCode = orderRequest.getProductRequestList().stream().map(prod -> prod.getProductCode()).collect(Collectors.toList());

		ResponseEntity<Boolean> booleanResponseEntity = restTemplate.postForEntity("http://localhost:8082/products/product-in-stock", productCode, Boolean.class);
		assertEquals(2, productCode.size());
		assertEquals(true, booleanResponseEntity.getBody().booleanValue());
		ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(baseUrl, orderRequest, String.class);
		assertAll(
				() -> assertEquals(2, orderDetailsRepositoryTest.findAll().size()),
				() -> assertEquals(1, orderRepository.findAll().size()),
				() -> assertEquals(1220,orderRequest.getProductRequestList().stream().map(prod -> prod.getQuantityOrdered() * prod.getPriceEach()).reduce((double) 0, ArithmeticUtils::add))
		);
	}


}
