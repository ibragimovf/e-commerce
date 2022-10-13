package uz.pdp.restservice.controller;

import admin.receive.GatewayMerchantReceiveDto;
import admin.receive.MerchantReceiveDto;
import admin.response.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restservice.model.GatewayMerchantEntity;
import uz.pdp.restservice.service.base.BaseService;

import javax.validation.Valid;
import java.util.Optional;

import static uz.pdp.restservice.service.base.ResponseMessage.ID_EMPTY;

@RestController
@RequestMapping("/merchant")
public class MerchantController {

    private final BaseService baseService;

    public MerchantController(@Qualifier("merchantService") BaseService baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/add")
    public ApiResponse add(@Valid @RequestBody MerchantReceiveDto merchantReceiveDto) {
        return baseService.add(merchantReceiveDto);
    }

    @GetMapping("/get")
    public ApiResponse getList() {
        return baseService.getList();
    }

    @GetMapping("/get/disabled")
    public ApiResponse getDisabledList() {
        return baseService.getDisabledList();
    }

    @PutMapping("/edit/{id}")
    public ApiResponse edit(@PathVariable Long id,@Valid @RequestBody MerchantReceiveDto merchantReceiveDto) {
        return baseService.edit(id, merchantReceiveDto);
    }

    @GetMapping("/getById/{id}")
    public MerchantReceiveDto getById(@PathVariable Long id){
        return (MerchantReceiveDto) baseService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Optional<Long> id) {
        if (id.isPresent())
            return baseService.delete(id.get());

        else
            return new ApiResponse<>(1, ID_EMPTY);
    }
}
