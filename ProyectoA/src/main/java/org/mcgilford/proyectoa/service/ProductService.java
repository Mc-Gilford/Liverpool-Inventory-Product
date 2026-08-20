package org.mcgilford.proyectoa.service;

import jakarta.annotation.PostConstruct;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.mcgilford.proyectoa.dto.InventoryResponse;
import org.mcgilford.proyectoa.dto.ProductRequest;
import org.mcgilford.proyectoa.dto.ProductResponse;
import org.mcgilford.proyectoa.entity.Product;
import org.mcgilford.proyectoa.external.InventoryApiClient;
import org.mcgilford.proyectoa.repository.ProductRepository;
import org.mcgilford.proyectoa.service.loader.LoaderData;
import org.springframework.stereotype.Service;
import org.mcgilford.proyectoa.mapper.ProductMapper;
import org.springframework.web.client.RestClientException;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final InventoryApiClient inventoryApiClient;

    public Product getProduct(String keyword)
    {
        /*Primer solucion
        List<Product> listProduct =  productRepository.findAll();
        Product productReturn = listProduct.stream().filter(product ->
                (pr;

        for (int i = 0; i < listProduct.size(); i++) {
            Product product = listProduct.get(i);

            if (product.getName().toLowerCase().contains(keyword.toLowerCase())
                    || product.getDescription().toLowerCase()
                    .contains(keyword.toLowerCase())) {

                return product;
            }
        }
        throw new RuntimeException("Producto no encontrado");*/
        String keywordNormalize = normalize(keyword); //Normalize
        return productRepository.findAll().stream().filter(product ->
                        normalize(product.getName()).contains(keywordNormalize) ||
                                normalize(product.getDescription()).contains(keywordNormalize)).findFirst()
                .orElseThrow(()->new RuntimeException("Error"));
    }
    public List<ProductResponse> getProducts(@NonNull String query)
    {
        if(query.isBlank())
        {
            throw new IllegalArgumentException("El parámetro query es obligatorio");
        }
        //ProductResponse productResponse = new ProductResponse();
        String queryNormaliza = normalize(query);
        List<Product> products = productRepository.findAll().stream().filter(product ->
                normalize(product.getName()).contains(queryNormaliza) ||
                        normalize(product.getDescription()).contains(queryNormaliza)).toList();
        if (products.isEmpty()) {
            throw new RuntimeException(
                    "No se encontraron productos para: " + query
            );
        }
        List<ProductResponse> responses = new ArrayList<>();
        for(Product product : products)
        {
            ProductResponse response = productMapper.toResponse(product);

            try{
                InventoryResponse inventory = inventoryApiClient.getInventory(product.getId());
                if(inventory!=null && inventory.getStock()!=null)
                {
                    response.setStock(inventory.getStock());
                    if (inventory.getStock() > 0) {
                        response.setInventoryStatus("AVAILABLE");
                    }
                    else{
                        response.setInventoryStatus("OUT_OF_STOCK");
                    }
                }
                else{
                    response.setStock(null);
                    response.setInventoryStatus("UNAVAILABLE");
                }


            }catch (RestClientException exception)
            {
                response.setStock(null);
                response.setInventoryStatus("UNAVAILABLE");
            }
            responses.add(response);
        }
        return responses;
    }

    private String normalize(String text) {
        if (text == null) {
            return "";
        }

        return Normalizer.normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }

    public Product saveProduct(Product product)
    {
        return productRepository.save(product);
    }
    @PostConstruct
    public void loadInitialProducts(){
        if (productRepository.count() > 0) {
            return;
        }
        LoaderData loaderData = new LoaderData();
        for(ProductRequest productRequest : loaderData.getProducts())
        {
            Product product = productMapper.toEntity(productRequest);
            saveProduct(product);
        }
    }
}
