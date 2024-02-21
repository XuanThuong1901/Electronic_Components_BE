package com.poly.ecommercestore.service.ReturnOrder;

import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.poly.ecommercestore.configuration.jwt.JwtService;
import com.poly.ecommercestore.model.request.ReturnOrderRequest;
import com.poly.ecommercestore.entity.*;
import com.poly.ecommercestore.repository.*;
import com.poly.ecommercestore.model.response.ReturnOrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReturnOrderServiceImpl implements IReturnOrderService {

    private final JwtService jwtUnit;
    private final ReturnOrderRepository returnOrderRepository;
    private final ImageReturnRepository imageReturnRepository;
    private final ProductRepository productRepository;
    private final DetailOrderRepository detailOrderRepository;
    private final AccountRepository accountRepository;

    @Override
    public ReturnOrderResponse getAll(String token) {
        String email = jwtUnit.extractUserName(token);
        Accounts account = accountRepository.findByEmail(email).orElse(null);
        if(account.getEmployer() == null){
            return ReturnOrderResponse.builder()
                    .message("ACCOUNT_NOT_ROLE")
                    .build();
        }
        return ReturnOrderResponse.builder()
                .returnOrderList(returnOrderRepository.findAll())
                .message("GET_ALL_RETURN_ORDER_SUCCESS")
                .build();
    }

    @Override
    public ReturnOrderResponse getByCustomer(String token) {
        String email = jwtUnit.extractUserName(token);
        Accounts account = accountRepository.findByEmail(email).orElse(null);
        if(account.getCustomers() == null){
            return ReturnOrderResponse.builder()
                    .message("ACCOUNT_NOT_ROLE")
                    .build();
        }
        return ReturnOrderResponse.builder()
                .returnOrderList(returnOrderRepository.findByCustomer(account.getCustomers()))
                .message("GET_RETURN_ORDER_BY_CUSTOMER_SUCCESS")
                .build();
    }

    @Override
    public ReturnOrderResponse getOne(Integer id) {
        ReturnOrder returnOrder = returnOrderRepository.findById(id).orElse(null);
        if(returnOrder == null){
            return ReturnOrderResponse.builder()
                    .message("GET_RETURN_ORDER_BY_ID_ERROR001")
                    .build();
        }
        List<ReturnOrder> returnOrderList = new ArrayList<>();
        returnOrderList.add(returnOrder);
        return ReturnOrderResponse.builder()
                .returnOrderList(returnOrderList)
                .message("GET_RETURN_ORDER_BY_ID_SUCCESS")
                .build();
    }

    @Override
    @Transactional
    public ReturnOrderResponse add(String token, ReturnOrderRequest request, List<MultipartFile> imgProductReturn) {
        String email = jwtUnit.extractUserName(token);
        Accounts account = accountRepository.findByEmail(email).orElse(null);
        if(account.getCustomers() == null){
            return ReturnOrderResponse.builder()
                    .message("ACCOUNT_NOT_ROLE")
                    .build();
        }
        try {
            Products product = productRepository.findById(request.getProductId()).orElse(null);
            if(product == null){
                return ReturnOrderResponse.builder()
                        .message("PRODUCT_RETURN_ORDER_ERROR001")
                        .build();
            }

//            DetailOrders detailOrder = detailOrderRepository.findByOrder_IDOrderAndProduct_IDProduct(account.getIDAccount(), product.getIDProduct())

            ReturnOrder newReturnOrder = ReturnOrder.builder()
                    .name(request.getName())
                    .customer(account.getCustomers())
                    .product(product)
                    .address(request.getAddress())
                    .phoneNumber(request.getPhoneNumber())
                    .quantity(request.getQuantity())
                    .amount(request.getAmount())
                    .reason(request.getReason())
                    .formality(request.getFormality())
                    .build();

            newReturnOrder = returnOrderRepository.save(newReturnOrder);

            List<ImageReturnOrder> imageReturnOrderList = saveListImgOnFirebase(newReturnOrder, imgProductReturn);
            imageReturnRepository.saveAll(imageReturnOrderList);
            newReturnOrder.setImageReturnOrderList(imageReturnOrderList);
            returnOrderRepository.save(newReturnOrder);

            return ReturnOrderResponse.builder()
                    .message("CREATED_RETURN_ORDER_SUCCESS")
                    .build();

        }catch (Exception e){
            System.out.printf(e.toString());
            return ReturnOrderResponse.builder()
                    .message("CREATED_RETURN_ORDER_ERROR001")
                    .build();
        }
    }

    @Override
    public ReturnOrderResponse confirm(String token, Integer returnOrderId, String status) {
        try {
            String email = jwtUnit.extractUserName(token);
            Accounts account = accountRepository.findByEmail(email).orElse(null);
            if(account.getEmployer() == null ){
                return ReturnOrderResponse.builder()
                        .message("ACCOUNT_NOT_ROLE")
                        .build();
            }

            ReturnOrder returnOrder = returnOrderRepository.findById(returnOrderId).orElse(null);
            if(returnOrder == null){
                return null;
            }
            if(returnOrder.getStatus().equals("unconfirmed")){
                if(status.equals("confirmed") && returnOrder.getFormality().equals("change")){
                    Products product = returnOrder.getProduct();
                    product.setQuantity(product.getQuantity() - returnOrder.getQuantity());
                    productRepository.save(product);
                    returnOrder.setStatus(status);
                    returnOrderRepository.save(returnOrder);
                    return ReturnOrderResponse.builder()
                            .message("CONFIRMED_RETURN_ORDER_SUCCESS")
                            .build();
                }
                else if(status.equals("confirmed") && returnOrder.getFormality().equals("giveBack")) {
                    returnOrder.setStatus(status);
                    returnOrderRepository.save(returnOrder);
                    return ReturnOrderResponse.builder()
                            .message("CONFIRMED_RETURN_ORDER_SUCCESS")
                            .build();
                }
                else {
                    returnOrder.setStatus(status);
                    returnOrderRepository.save(returnOrder);
                    return ReturnOrderResponse.builder()
                            .message("REFUSE_RETURN_ORDER_SUCCESS")
                            .build();
                }
            }
            else {
                return ReturnOrderResponse.builder()
                        .message("CONFIRMED_RETURN_ORDER_ERROR001")
                        .build();
            }
        }catch (Exception e){
            System.out.printf(e.toString());
            return ReturnOrderResponse.builder()
                    .message("CONFIRMED_RETURN_ORDER_ERROR001")
                    .build();
        }
    }

    private List<ImageReturnOrder> saveListImgOnFirebase(ReturnOrder returnOrder, List<MultipartFile> fileList) throws IOException {

        List<ImageReturnOrder> newImgList = new ArrayList<>();
        for (MultipartFile file: fileList) {
            String url = saveImgOnFirebase(file);
            System.out.println(url);
            ImageReturnOrder temp = ImageReturnOrder.builder()
                    .returnOrder(returnOrder)
                    .url(url)
                    .build();
            newImgList.add(temp);
        }
        System.out.println("\n\n" + newImgList);
        return newImgList;
    }

    private String saveImgOnFirebase(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Tạo một số ngẫu nhiên dưới dạng UUID và ghép vào tên tệp
        String name = UUID.randomUUID().toString() + extension;

        bucket.create(name, file.getBytes(), file.getContentType());

        return "https://storage.googleapis.com/linkkiendientu-796a8.appspot.com/".concat(name);
    }
}
