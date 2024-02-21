package com.poly.ecommercestore.service.status;

import com.poly.ecommercestore.entity.Status;

import java.util.List;

public interface IStatusService {
    List<Status> getStatus(String type);
}
