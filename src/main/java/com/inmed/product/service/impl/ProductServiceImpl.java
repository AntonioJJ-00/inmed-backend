package com.inmed.product.service.impl;

import com.inmed.audit.service.AuditService;
import com.inmed.company.entity.Company;
import com.inmed.company.repository.CompanyRepository;
import com.inmed.category.entity.Category;
import com.inmed.category.repository.CategoryRepository;
import com.inmed.supplier.entity.Supplier;
import com.inmed.supplier.repository.SupplierRepository;
import com.inmed.exception.custom.DuplicateResourceException;
import com.inmed.exception.custom.ResourceNotFoundException;
import com.inmed.product.dto.request.CreateProductRequest;
import com.inmed.product.dto.request.UpdateProductRequest;
import com.inmed.product.dto.response.ProductResponse;
import com.inmed.product.entity.Product;
import com.inmed.product.mapper.ProductMapper;
import com.inmed.product.repository.ProductRepository;
import com.inmed.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl
        implements ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final CompanyRepository companyRepository;
    private final AuditService auditService;

    @Override
    public ProductResponse create(
            CreateProductRequest request
    ){
        if(repository.existsByCode(request.getCode())){

            throw new DuplicateResourceException(
                    "Product code already exists"
            );
        }
        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );
        Supplier supplier =
                supplierRepository.findById(
                                request.getSupplierId()
                        )
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Supplier not found"
                                )
                        );
        Company company =
                companyRepository
                        .findTopByOrderByIdAsc()
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Company not found"
                                )
                        );
        Product product =
                Product.builder()
                        .code(
                                request.getCode()
                        )
                        .name(
                                request.getName()
                        )
                        .shortDescription(
                                request.getShortDescription()
                        )
                        .longDescription(
                                request.getLongDescription()
                        )
                        .cost(
                                request.getCost()
                        )
                        .salePrice(
                                request.getSalePrice()
                        )
                        .wholesalePrice(
                                request.getWholesalePrice()
                        )
                        .relativeGain(
                                request.getRelativeGain()
                        )
                        .category(
                                category
                        )
                        .supplier(
                                supplier
                        )
                        .company(
                                company
                        )
                        .build();
        repository.save(product);
        auditService.save(
                "SYSTEM",
                "CREATE_PRODUCT",
                product.getCode(),
                "Product created"
        );
        return ProductMapper.toResponse(
                product
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll(){
        return repository.findAll()
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(
            Long id
    ){
        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                )
                        );
        return ProductMapper.toResponse(product);
    }

    @Override
    public ProductResponse update(
            Long id,
            UpdateProductRequest request
    ){
        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                )
                        );
        if(request.getName()!=null)
            product.setName(
                    request.getName()
            );
        if(request.getSalePrice()!=null)
            product.setSalePrice(
                    request.getSalePrice()
            );
        if(request.getCost()!=null)
            product.setCost(
                    request.getCost()
            );
        if(request.getCategoryId()!=null){
            Category category =
                    categoryRepository.findById(
                                    request.getCategoryId()
                            )
                            .orElseThrow();
            product.setCategory(category);
        }
        if(request.getSupplierId()!=null){
            Supplier supplier =
                    supplierRepository.findById(
                                    request.getSupplierId()
                            )
                            .orElseThrow();
            product.setSupplier(supplier);
        }
        repository.save(product);
        auditService.save(
                "SYSTEM",
                "UPDATE_PRODUCT",
                product.getCode(),
                "Product updated"
        );
        return ProductMapper.toResponse(product);
    }

    @Override
    public void deactivate(
            Long id
    ){
        Product product =
                repository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Product not found"
                                )
                        );
        product.setActive(false);
        repository.save(product);
        auditService.save(
                "SYSTEM",
                "DEACTIVATE_PRODUCT",
                product.getCode(),
                "Product deactivated"
        );
    }

    @Override
    @Transactional(readOnly=true)
    public List<ProductResponse> findChanges(
            Integer version
    ){
        return repository
                .findBySyncVersionGreaterThan(version)
                .stream()
                .map(ProductMapper::toResponse)
                .toList();
    }
}