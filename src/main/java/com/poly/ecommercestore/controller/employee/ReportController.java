package com.poly.ecommercestore.controller.employee;

import com.poly.ecommercestore.model.request.ReportRequest;
import com.poly.ecommercestore.model.response.report.ReportSaleResponse;
import com.poly.ecommercestore.service.report.ReportServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("admin/report")
public class ReportController {

    private final ReportServiceImpl iReportService;

    @PostMapping("")
    public ResponseEntity<ReportSaleResponse> getSale(@RequestBody ReportRequest request){
        try {
            System.out.println(request.getStartDay());
            ReportSaleResponse response = iReportService.reportSale(request);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            ReportSaleResponse response = ReportSaleResponse.builder()
                    .message(e.toString())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

    }
}
