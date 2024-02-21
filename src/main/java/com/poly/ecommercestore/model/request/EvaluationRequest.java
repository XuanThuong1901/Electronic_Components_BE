package com.poly.ecommercestore.model.request;

import lombok.Data;

@Data
public class EvaluationRequest {
    private int evaluate;
    private String contents;

    public EvaluationRequest(int evaluate, String contents) {
        this.evaluate = evaluate;
        this.contents = contents;
    }

    public EvaluationRequest() {
    }
}
