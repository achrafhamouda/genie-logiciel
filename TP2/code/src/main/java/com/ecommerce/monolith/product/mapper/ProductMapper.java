package com.ecommerce.monolith.product.mapper;

import com.ecommerce.monolith.product.dto.*;
import com.ecommerce.monolith.product.model.Product;
import java.util.List;

public interface ProductMapper {

	ProductDTO toDTO(Product product);

	List<ProductDTO> toDTOList(List<Product> products);

	Product toEntity(ProductRequest request);

	void updateEntity(ProductRequest request, Product product);
}