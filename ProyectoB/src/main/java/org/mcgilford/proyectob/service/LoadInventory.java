package org.mcgilford.proyectob.service;

import lombok.Getter;
import org.mcgilford.proyectob.entity.Stock;

import java.util.List;
@Getter
public class LoadInventory {
    private final List<Stock> stockList = List.of(
            new Stock("EXT-001", 10),
            new Stock("EXT-002", 0),
            new Stock("EXT-003", 5),
            new Stock("EXT-004", 2),
            new Stock("EXT-005", 20)
    );

}
