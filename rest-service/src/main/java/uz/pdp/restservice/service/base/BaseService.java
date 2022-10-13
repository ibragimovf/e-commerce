package uz.pdp.restservice.service.base;

import admin.response.ApiResponse;

public interface BaseService<T,R,R1> extends ResponseMessage{

    ApiResponse<Object> add(T t);
    ApiResponse<R> getList();

    ApiResponse<Object> edit(long id,T t);
    R1 getById(long id);
    ApiResponse<Object> delete(long id);

    ApiResponse<R> getDisabledList();
}
