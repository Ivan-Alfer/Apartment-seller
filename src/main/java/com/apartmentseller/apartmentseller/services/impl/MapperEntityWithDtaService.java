package com.apartmentseller.apartmentseller.services.impl;

import com.apartmentseller.apartmentseller.services.MapperService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class MapperEntityWithDtaService implements MapperService {

    public <T,E> E mapEntityWithDto(T t, E e){
        BeanUtils.copyProperties(t, e);
        return e;
    }

}
