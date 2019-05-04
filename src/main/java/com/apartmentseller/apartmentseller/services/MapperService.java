package com.apartmentseller.apartmentseller.services;

public interface MapperService {
    <T,E> E mapEntityWithDto(T t, E e);
}
