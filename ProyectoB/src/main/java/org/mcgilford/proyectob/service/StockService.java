package org.mcgilford.proyectob.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.mcgilford.proyectob.entity.Stock;
import org.mcgilford.proyectob.repository.StockRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    public Stock getProduct(String id)
    {
        ///Stock stock= stockRepository.findById(id);
        //return stockRepository.findById(id).orElseThrow(()-> new RuntimeException("No encontrado"));
        return stockRepository.findById(id).orElseThrow(()->new RuntimeException("Falla al encontrar por ID"));
    }
    public Stock saveProduct(Stock product)
    {
        return stockRepository.save(product);
    }

    @PostConstruct
    public void loadInitialStock(){
        if(stockRepository.count()>0)
        {
            return;
        }
        LoadInventory loadInventory = new LoadInventory();
        for(Stock stock: loadInventory.getStockList())
        {
            saveProduct(stock);
        }
    }
}
