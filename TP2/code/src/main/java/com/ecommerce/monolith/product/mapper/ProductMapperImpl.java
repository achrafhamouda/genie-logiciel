package com.ecommerce.monolith.product.mapper;

import com.ecommerce.monolith.product.dto.*;
import com.ecommerce.monolith.product.model.Product;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapperImpl implements ProductMapper {

	@Override
	public ProductDTO toDTO(Product product) {
		if (product == null) return null;
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setName(product.getName());
		dto.setDescription(product.getDescription());
		dto.setPrice(product.getPrice());
		dto.setStock(product.getStock());
		return dto;
	}

	@Override
	public List<ProductDTO> toDTOList(List<Product> products) {
		return products.stream().map(this::toDTO).collect(Collectors.toList());
	}

	@Override
	public Product toEntity(ProductRequest request) {
		if (request == null) return null;
		Product product = new Product();
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
		return product;
	}

	@Override
	public void updateEntity(ProductRequest request, Product product) {
		if (request == null) return;
		product.setName(request.getName());
		product.setDescription(request.getDescription());
		product.setPrice(request.getPrice());
		product.setStock(request.getStock());
	}
}