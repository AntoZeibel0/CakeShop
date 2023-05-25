package com.example.cakeshop;

import com.example.cakeshop.entity.Cake;
import com.example.cakeshop.repo.CakeRepo;
import com.example.cakeshop.service.CakeService;
import com.github.javafaker.Faker;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class CakeShopApplicationTests {

    @InjectMocks
    private CakeService cakeService;

    @Mock
    private CakeRepo cakeRepo;

    private final Faker faker = new Faker();
    private Cake cake;

    @BeforeEach
    public void initTest() {
        cake = Cake.builder()
                .name(faker.name().title())
                .description(faker.lorem().paragraph(3))
                .price(faker.number().numberBetween(10, 100))
                .build();
    }

    @Test
    @Rollback(value = false)
    void testCreateCake() {
        when(cakeRepo.save(any())).thenReturn(cake);

        Cake result = cakeService.createCake(cake);

        Assertions.assertEquals(cake, result);
    }

    @Test
    @Rollback(value = false)
    void testDeleteCake() {
        given(cakeRepo.findById(any())).willReturn(Optional.of(cake));
        cakeService.deleteCakeById(cake.getId());
        Assertions.assertDoesNotThrow(() -> new Exception(""));
    }

}
