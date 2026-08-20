package org.mcgilford.proyectoa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    @NotBlank(message = "ID obligatorio")
    private String id;
    @NotBlank(message = "Nombre  obligatorio")
    private String name;
    @NotBlank(message = "Descripción es obligatoria")
    private String description;
    @NotNull(message = "Precio obligatorio")
    @Positive(message = "El precio mayor que cero")
    private Double price;
}
