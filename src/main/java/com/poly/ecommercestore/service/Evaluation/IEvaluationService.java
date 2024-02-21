package com.poly.ecommercestore.service.Evaluation;

import com.poly.ecommercestore.model.request.EvaluationRequest;
import com.poly.ecommercestore.entity.Evaluations;

import java.util.List;

public interface IEvaluationService {

    Boolean addEvaluation(String tokenHeader, int idProduct, EvaluationRequest request);

    List<Evaluations> getEvaluationByProduct(int idProduct);

    Evaluations getEvaluation(int id);
}
