package com.poly.ecommercestore.controller.customer;

import com.poly.ecommercestore.model.request.EvaluationRequest;
import com.poly.ecommercestore.util.Message;
import com.poly.ecommercestore.service.Evaluation.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getEvaluation(@PathVariable(value = "id") int id)
    {
        return ResponseEntity.ok(evaluationService.getEvaluationByProduct(id));
    }

    @PostMapping("/create/{id}")
    public ResponseEntity<?> createEvaluation(@RequestHeader("access_token") String tokenHeader, @PathVariable(value = "id") int id, @RequestBody EvaluationRequest request)
    {
        if(request.getEvaluate() == 0){
            return ResponseEntity.badRequest().body(Message.VALIDATION_EVALUATE_ERROR001);
        }
        if(evaluationService.addEvaluation(tokenHeader, id, request))
            return ResponseEntity.ok(Message.EVALUATION_SUCCESS);
        return ResponseEntity.badRequest().body(Message.EVALUATION_ERROR001);
    }
}
