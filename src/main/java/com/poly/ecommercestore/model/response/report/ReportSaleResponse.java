package com.poly.ecommercestore.model.response.report;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSaleResponse {

    private List<BigDecimal> saleOrder;
    private List<BigDecimal> saleImport;
    private List<Date> dateList;
    private String message;
}
