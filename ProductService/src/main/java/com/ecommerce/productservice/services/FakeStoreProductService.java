package com.ecommerce.productservice.services;


import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.exceptions.ProductNotExistsException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

//@Primary
@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String,Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    private Product convertFakeStoreProductToProduct(FakeStoreProductDto fakeStoreProduct) {
        Product product = new Product();
        product.setTitle(fakeStoreProduct.getTitle());
        product.setId(fakeStoreProduct.getId());
        product.setPrice(fakeStoreProduct.getPrice());
        product.setDescription(fakeStoreProduct.getDescription());
        product.setImageUrl(fakeStoreProduct.getImage());
        product.setCategory(new Category());
        product.getCategory().setName(fakeStoreProduct.getCategory());

        return product;
    }

    @Override
    public Product getSingleProduct(Long id) throws ProductNotExistsException {

        Product p = (Product) redisTemplate.opsForHash().get("PRODUCTS","PRODUCTS_" + id);

        ResponseEntity<FakeStoreProductDto> productDto = restTemplate.getForEntity(
                "https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class
        );

        if (p!=null){
            return p;
        }



        if (productDto.getStatusCode() != HttpStatusCode.valueOf(200)) {
            // log this to command line
        }

        if (productDto == null) {
            throw new ProductNotExistsException(
                    "Product with id: " + id + " doesn't exist."
            );
        }
        Product p1 =convertFakeStoreProductToProduct(productDto.getBody());
        redisTemplate.opsForHash().put("PRODUCTS","PRODUCTS_" + id, p1);
        return p1;
    }

    @Override
    public List<Product> getAllProducts() {


//        List<FakeStoreProductDto> response = restTemplate.getForObject(
//                "https://fakestoreapi.com/products",
//                List<FakeStoreProductDto>.class
//        );

        // runtime
        FakeStoreProductDto[] response = restTemplate.getForObject(
                "https://fakestoreapi.com/products",
                FakeStoreProductDto[].class
        );


        List<Product> answer = new ArrayList<>();


        for (FakeStoreProductDto dto : response) {
            answer.add(convertFakeStoreProductToProduct(dto));
        }

        return answer;
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }


    @Override
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor =
                new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);

        return convertFakeStoreProductToProduct(response);
    }

    @Override
    public Product addNewProduct(Product product) {
        return null;
    }

    @Override
    public Product deleteProduct(Long id) {
        return null;
    }
}

// Break till 10:35
