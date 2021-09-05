package com.biggwang.web.controller;

import com.biggwang.web.code.InvestResultEnum;
import com.biggwang.web.dto.ProductDTO;
import com.biggwang.web.dto.ResponseDTO;
import com.biggwang.web.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "PRODUCT")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @ApiOperation(value = "1.전체 투자 상품 조회", notes = "상품 모집기간내의 상품만 조회 됩니다.")
    @GetMapping("/product")
    public ResponseDTO<List<ProductDTO>> getProductList() {
        List<ProductDTO> productList = productService.getProductList();
        return ResponseDTO.<List<ProductDTO>>builder()
                .success(true)
                .message(InvestResultEnum.SUCCESS.getMessage())
                .data(productList)
                .build();
    }
}
