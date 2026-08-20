package org.mcgilford.proyectob.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.mcgilford.proyectob.entity.Stock;
import org.mcgilford.proyectob.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory/")
@Tag(name = "Inventory Service",description = "Product inventory operations")
public class StockController {
    private final StockService stockService;
    @GetMapping("/{productId}")
    @Operation(summary = "Get inventory by product ID")
    public ResponseEntity<Stock> getProduct(@PathVariable String productId)
    {
        /*Stock returnStock = new Stock();
        Optional<Stock> stock = stockService.getProduct(id);
        //stock.orElseThrow();
        if(stock.isPresent())
        {
            returnStock = stock.get();
        }
        return returnStock;*/
        return ResponseEntity.ok().body(stockService.getProduct(productId));
    }

    @PostMapping
    @Operation (summary = "Create inventory")
    public ResponseEntity<Stock> saveProduct(@RequestBody  Stock product){
        return ResponseEntity.status(HttpStatus.CREATED).body(stockService.saveProduct(product));
    }
}
