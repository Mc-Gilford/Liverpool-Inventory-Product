package org.mcgilford.proyectob.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockRequest {
        private String productId;
        private Integer stock;
}
