import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mcgilford.proyectoa.dto.InventoryResponse;
import org.mcgilford.proyectoa.dto.ProductResponse;
import org.mcgilford.proyectoa.entity.Product;
import org.mcgilford.proyectoa.external.InventoryApiClient;
import org.mcgilford.proyectoa.mapper.ProductMapper;
import org.mcgilford.proyectoa.repository.ProductRepository;
import org.mcgilford.proyectoa.service.ProductService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private InventoryApiClient inventoryApiClient;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnOutOfStockWhenStockIsZero() {
        // Arrange
        Product product = new Product(
                "EXT-002",
                "Samsung Galaxy S22",
                "Smartphone gama alta",
                17499.50
        );

        ProductResponse productResponse = new ProductResponse(
                "EXT-002",
                "Samsung Galaxy S22",
                "Smartphone gama alta",
                17499.50,
                null,
                null
        );

        InventoryResponse inventoryResponse = new InventoryResponse(
                "EXT-002",
                0
        );

        when(productRepository.findAll())
                .thenReturn(List.of(product));

        when(productMapper.toResponse(product))
                .thenReturn(productResponse);

        when(inventoryApiClient.getInventory("EXT-002"))
                .thenReturn(inventoryResponse);

        List<ProductResponse> result = productService.getProducts("samsung");

        assertEquals(1, result.size());
        assertEquals(0, result.get(0).getStock());
        assertEquals("OUT_OF_STOCK", result.get(0).getInventoryStatus()
        );
    }

    @Test
    void shouldThrowExceptionWhenProductsAreNotFound() {
        // Arrange
        when(productRepository.findAll())
                .thenReturn(List.of());

        // Act y Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> productService.getProducts("producto inexistente")
        );

        assertEquals(
                "No se encontraron productos para: producto inexistente",
                exception.getMessage()
        );

        verify(productRepository).findAll();
        verifyNoInteractions(productMapper, inventoryApiClient);
    }
}