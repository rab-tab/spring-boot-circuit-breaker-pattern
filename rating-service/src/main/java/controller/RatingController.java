package controller;

import dto.ProductRatingDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.RatingService;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("{prodId}")
    public ResponseEntity<dto.ProductRatingDto> getRating(@PathVariable int prodId) throws InterruptedException {
        dto.ProductRatingDto productRatingDto = this.ratingService.getRatingForProduct(prodId);
        return this.failRandomly(productRatingDto);
    }

    private ResponseEntity<ProductRatingDto> failRandomly(ProductRatingDto productRatingDto) throws InterruptedException {
        // simulate delay
        Thread.sleep(100);
        // simulate failure
        int random = ThreadLocalRandom.current().nextInt(1, 4);
        if(random < 3)
            return ResponseEntity.status(500).build();
        return ResponseEntity.ok(productRatingDto);
    }

}
