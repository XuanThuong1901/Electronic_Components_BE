package com.poly.ecommercestore.model.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {
    private Date startDay;
    private Date endDay;
    private String type;
}
