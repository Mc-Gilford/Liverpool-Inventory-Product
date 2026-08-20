package org.mcgilford.proyectob.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="stock")
@Entity
public class Stock {
    @Id
    private String productId;
    @Column(nullable = false)
    private Integer stock;
}
