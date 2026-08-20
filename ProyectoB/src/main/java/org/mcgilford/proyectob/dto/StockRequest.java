package org.mcgilford.proyectob.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
        @NotBlank(message = "ID del producto es obligatorio")
        private String productId;
        @NotNull(message = "Stock es obligatorio")
        @PositiveOrZero(message = "Stock no puede ser negativo")
        private Integer stock;
}
