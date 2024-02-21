package com.poly.ecommercestore.service.Evaluation;

import com.poly.ecommercestore.entity.Customers;
import com.poly.ecommercestore.model.request.EvaluationRequest;
import com.poly.ecommercestore.entity.Accounts;
import com.poly.ecommercestore.entity.Evaluations;
import com.poly.ecommercestore.entity.Products;
import com.poly.ecommercestore.repository.EvaluationRepository;
import com.poly.ecommercestore.repository.ProductRepository;
import com.poly.ecommercestore.util.extractToken.IExtractToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationService implements IEvaluationService{

    private final IExtractToken iExtractToken;
    private final EvaluationRepository evaluationRepository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public Boolean addEvaluation(String tokenHeader, int idProduct, EvaluationRequest request) {
       try{
           Customers user = iExtractToken.extractCustomer(tokenHeader);
           if(user == null)
               return false;
           Products product = productRepository.findById(idProduct).get();
           if(product == null)
               return false;
           Date createDate = new Date();
           Evaluations evaluation = new Evaluations(request.getContents(), request.getEvaluate(), createDate, user, product);
           evaluationRepository.save(evaluation);
           return true;
       }
       catch (Exception ex){
           System.out.printf(ex.toString());
           return false;
       }

    }

    @Override
    public List<Evaluations> getEvaluationByProduct(int idProduct) {
        return evaluationRepository.findByProduct(idProduct);
    }

    @Override
    public Evaluations getEvaluation(int id) {
        return evaluationRepository.findById(id).get();
    }
}
