package org.mcgilford.proyectoa.service.loader;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mcgilford.proyectoa.dto.ProductRequest;

import java.util.List;

@Getter
public class LoaderData {
    private final List<ProductRequest> products = List.of(
            new ProductRequest(
                    "EXT-001",
                    "iPhone 13 128GB",
                    "Smartphone Apple",
                    18999.99
            ),
            new ProductRequest(
                    "EXT-002",
                    "Samsung Galaxy S22",
                    "Smartphone gama alta",
                    17499.50
            ),
            new ProductRequest(
                    "EXT-003",
                    "MacBook Air M1",
                    "Laptop ligera",
                    21999.00
            ),
            new ProductRequest(
                    "EXT-004",
                    "Dell XPS 13",
                    "Ultrabook premium",
                    24999.99
            ),
            new ProductRequest(
                    "EXT-005",
                    "Audífonos Sony WH-1000XM4",
                    "Audífonos con cancelación de ruido",
                    5999.00
            )
    );

}