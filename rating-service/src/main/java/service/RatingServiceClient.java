package service;

import dto.ProductRatingDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class RatingServiceClient {

    private final RestTemplate restTemplate = new RestTemplate();

   // @Value("${rating.service.endpoint}")
    private String ratingService="http://localhost:8081/rating";

    public void setRatingService(String ratingService) {
        this.ratingService = ratingService;
    }

    @CircuitBreaker(name = "ratingService", fallbackMethod = "getDefault")
    public ProductRatingDto getProductRatingDto(int productId){
        return this.restTemplate.getForEntity(this.ratingService + productId, ProductRatingDto.class)
                .getBody();
    }

    public ProductRatingDto getDefault(int productId, Throwable throwable){
        return ProductRatingDto.of(0, Collections.emptyList());
    }

}
