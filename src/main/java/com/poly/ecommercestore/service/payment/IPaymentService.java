package com.poly.ecommercestore.service.payment;

import com.poly.ecommercestore.entity.Payments;
import com.poly.ecommercestore.model.request.PaymentRequest;

import java.util.List;

public interface IPaymentService {

    List<Payments> getAllPayment();

    Payments addPayment(PaymentRequest request);

    Boolean updatePayment(PaymentRequest request, int iDPayment);

    Boolean deletePayment(int iDPayment);
}
