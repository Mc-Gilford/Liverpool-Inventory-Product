package org.mcgilford.proyectoa.external;

import org.mcgilford.proyectoa.dto.InventoryResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Component
public class InventoryApiClient {
    private final RestClient restClient;

    public InventoryApiClient(RestClient.Builder restBuilder, @Value("${inventory.service.url}") String uri) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(Duration.ofSeconds(2));
        requestFactory.setReadTimeout(Duration.ofSeconds(3));
        this.restClient = restBuilder.baseUrl(uri).build();
    }
    public InventoryResponse getInventory(String productId){
        return restClient.get().uri("/api/v1/inventory/{productId}",productId).retrieve().body(InventoryResponse.class);
    }
}
