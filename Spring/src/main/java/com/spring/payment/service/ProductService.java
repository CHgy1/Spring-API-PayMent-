package com.spring.payment.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.payment.domain.Product;
import com.spring.payment.domain.ProductImg;
import com.spring.payment.domain.User;
import com.spring.payment.repository.ProductImgRepository;
import com.spring.payment.repository.ProductRepository;
import com.spring.payment.request.ProductRequest;
import com.spring.payment.response.ProductResponse;
import com.spring.util.DateUtil;
import com.spring.util.ParserToken;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ParserToken parserToken;

    @Autowired
    private ProductImgRepository productimaRepository;
    
    @Autowired
    private ImageService imageService;

    // 제품을 생성, 이미지를 저장하는 메소드입니다.
    public ProductResponse createProduct(ProductRequest productRequest, List<MultipartFile> images, String token) {
        Product product = conversionToEntity(productRequest, token);
        productRepository.save(product);

        List<ProductImg> savedImages = new ArrayList<>();
        for (MultipartFile image : images) {
            String fileName = imageService.saveImage(image);
            ProductImg img = new ProductImg();
            img.setFileName(fileName);
            img.setFileType(image.getContentType());
            img.setUrl(fileName);
            img.setProduct(product);
            savedImages.add(img);                
        }

        productimaRepository.saveAll(savedImages);

        ProductResponse productResponse = new ProductResponse();
        // 저장된 제품 데이터로 productResponse를 채웁니다.
        return productResponse;
    }
    public ProductResponse getProduct(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return convertToResponse(product);
    }
    
    // 제품 수정 메소드
    public ProductResponse updateProduct(ProductRequest productRequest, List<MultipartFile> images, String token) {
    	// 상품 정보 엔티티
        Product product = productRepository.findById(productRequest.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productRequest.getId()));

        // 로그인한 유저의 엔티티
        User user = parserToken.conversionToToken(token);
        
        // 상품 등록 유저와 수정 시도한 유저와 비교
        if (!product.getUsers().getId().equals(user.getId())) {
            throw new RuntimeException("자신이 등록한 상품만 수정 가능합니다.");
        }

        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setDescription(productRequest.getDescription());
        product.setStock(productRequest.getStock());
        productRepository.save(product);

        // 삭제할 이미지 처리
        if (productRequest.getDeleteImages() != null && !productRequest.getDeleteImages().isEmpty()) {
            List<ProductImg> existingImages = productimaRepository.findByProduct(product);
            existingImages.stream()
                .filter(img -> productRequest.getDeleteImages().contains(img.getFileName()))
                .forEach(img -> {
                    try {
                        Path filePath = Paths.get(img.getFileName());
                        boolean deleted = Files.deleteIfExists(filePath);
                        if (deleted) {
                        	productimaRepository.delete(img);
                        } else {
                            System.err.println("Failed to delete file: " + filePath.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println("Error occurred while deleting file: " + img.getFileName());
                    }
                });
        }

        // 새로운 이미지 저장
        if (images != null && !images.isEmpty()) {
            List<ProductImg> savedImages = new ArrayList<>();
            for (MultipartFile image : images) {
                String fileName = imageService.saveImage(image);
                ProductImg img = new ProductImg();
                img.setFileName(fileName);
                img.setFileType(image.getContentType());
                img.setUrl(fileName);
                img.setProduct(product);
                savedImages.add(img);
            }
            productimaRepository.saveAll(savedImages);
        }

        return convertToResponse(product);
    }

    // 모든 제품을 조회하는 메소드
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // Product -> ProductResponse로 변환 메소드
    private ProductResponse convertToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setProductName(product.getProductName());
        response.setPrice(product.getPrice());
        response.setDescription(product.getDescription());
        response.setStock(product.getStock());
        response.setCreated_at(product.getCreate_at());
        response.setUsers(product.getUsers());
        response.setImages(product.getImages().stream().map(ProductImg::getUrl).collect(Collectors.toList())); // 이미지 URL 추가
        return response;
    }

    // ProductRequest -> Product 엔티티 변환 메소드
    private Product conversionToEntity(ProductRequest productRequest, String token) {
        Product product = new Product();
        User user = parserToken.conversionToToken(token);

        if (productRequest.getId() == null) {
            product.setProductName(productRequest.getProductName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setStock(productRequest.getStock());
            product.setCreate_at(DateUtil.dateFormatter());
            product.setUsers(user);
        } else {
        	product.setId(productRequest.getId());
            product.setProductName(productRequest.getProductName());
            product.setPrice(productRequest.getPrice());
            product.setDescription(productRequest.getDescription());
            product.setStock(productRequest.getStock());
            product.setUsers(productRequest.getUser());
        }
        return product;
    }

}
