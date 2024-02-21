package com.poly.ecommercestore.service.payment;

import com.poly.ecommercestore.entity.Payments;
import com.poly.ecommercestore.repository.PaymentRepository;
import com.poly.ecommercestore.model.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements IPaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public List<Payments> getAllPayment() {
        return paymentRepository.findAll();
    }

    @Override
    public Payments addPayment(PaymentRequest request) {
        if(paymentRepository.findByPaymentName(request.getPaymentName()) != null)
            return null;

        Payments newPayment = new Payments(request.getPaymentName(), request.getContents());

        if(paymentRepository.save(newPayment) == null)
            return null;

        return newPayment;
    }

    @Override
    public Boolean updatePayment(PaymentRequest payment, int iDPayment) {

        Payments updatePayment = paymentRepository.getReferenceById(iDPayment);
        if(updatePayment == null)
            return false;

        updatePayment.setPaymentName(payment.getPaymentName());
        updatePayment.setContents(payment.getContents());

        if(paymentRepository.save(updatePayment) == null)
            return false;

        return true;
    }

    @Override
    public Boolean deletePayment(int iDPayment) {
        Payments payment = paymentRepository.getReferenceById(iDPayment);

        if(payment == null)
            return false;

        paymentRepository.delete(payment);
        return true;
    }
}
