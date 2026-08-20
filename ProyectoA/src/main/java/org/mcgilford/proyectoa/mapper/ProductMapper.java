package org.mcgilford.proyectoa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mcgilford.proyectoa.dto.ProductRequest;
import org.mcgilford.proyectoa.dto.ProductResponse;
import org.mcgilford.proyectoa.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequest request);
    @Mapping(target = "stock", ignore = true)
    @Mapping(target = "inventoryStatus", ignore = true)
    ProductResponse toResponse(Product product);

}
