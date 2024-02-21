package com.poly.ecommercestore.service.report;

import com.poly.ecommercestore.model.request.ReportRequest;
import com.poly.ecommercestore.model.response.report.ReportSaleProductResponse;
import com.poly.ecommercestore.model.response.report.ReportSaleResponse;

public interface IReportService {
    ReportSaleResponse reportSale(ReportRequest request);

    ReportSaleProductResponse reportSaleProduct(ReportRequest request);
}
