package com.example.projectschedulehaircutserver.service.service;

import com.example.projectschedulehaircutserver.dto.ServiceDTO;
import com.example.projectschedulehaircutserver.response.ShowAllServiceByComboIdResponse;

import java.util.Set;

public interface ProductService {
    Set<ServiceDTO> findAllService();


    Set<ShowAllServiceByComboIdResponse> findAllServiceByComboId(Integer id);
}
