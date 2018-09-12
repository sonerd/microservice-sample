package com.dastan.microservice.product.api;

import com.dastan.microservice.product.model.Product;
import com.dastan.microservice.product.repository.ProductRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Flux;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository products;

    @Ignore
    @Test
    public void shouldReturnAllProductsAsJson() throws Exception {
        final Product p1 = Product.builder().id("111").build();
        final Product p2 = Product.builder().id("333").build();
        final Flux<Product> productList = Flux.just(p1, p2);
        given(this.products.findAll()).willReturn(productList);

        this.mvc.perform(get("/products")).andExpect(status().isOk());
    }
}