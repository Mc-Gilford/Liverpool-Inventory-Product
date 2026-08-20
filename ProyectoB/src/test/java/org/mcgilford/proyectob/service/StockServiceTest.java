package org.mcgilford.proyectob.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgilford.proyectob.entity.Stock;
import org.mcgilford.proyectob.repository.StockRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StockServiceTest {
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private StockService stockService;
    @Test
    void shouldReturnStockWhenProductExists(){
        Stock stock = new Stock("EXT-001", 10);

        when(stockRepository.findById("EXT-001")).thenReturn(Optional.of(stock));
        Stock result = stockService.getProduct("EXT-001");
        assertNotNull(result);
        assertEquals("EXT-001", result.getProductId());
        assertEquals(10, result.getStock());

        verify(stockRepository).findById("EXT-001");


    }
    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        // Arrange
        when(stockRepository.findById("EXT-999"))
                .thenReturn(Optional.empty());

        // Act y Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> stockService.getProduct("EXT-999")
        );

        assertEquals("Falla al encontrar por ID", exception.getMessage());

        verify(stockRepository).findById("EXT-999");
    }

    @Test
    void shouldSaveStock() {
        // Arrange
        Stock stock = new Stock("EXT-006", 8);

        when(stockRepository.save(stock)).thenReturn(stock);

        // Act
        Stock result = stockService.saveProduct(stock);

        // Assert
        assertNotNull(result);
        assertEquals("EXT-006", result.getProductId());
        assertEquals(8, result.getStock());

        verify(stockRepository).save(stock);
    }
}
