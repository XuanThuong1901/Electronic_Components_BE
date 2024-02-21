package com.poly.ecommercestore.service.ReturnOrder;

import com.poly.ecommercestore.model.request.ReturnOrderRequest;
import com.poly.ecommercestore.model.response.ReturnOrderResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IReturnOrderService {
    ReturnOrderResponse getAll(String token);
    ReturnOrderResponse getByCustomer(String token);
    ReturnOrderResponse getOne(Integer id);
    ReturnOrderResponse add(String token, ReturnOrderRequest request, List<MultipartFile> imgProductReturn);
    ReturnOrderResponse confirm(String token, Integer returnOrderId, String status);
}
