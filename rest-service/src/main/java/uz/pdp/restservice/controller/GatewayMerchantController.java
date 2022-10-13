package uz.pdp.restservice.controller;

import admin.receive.AgentMerchantReceiveDto;
import admin.receive.AgentReceiveDto;
import admin.receive.GatewayMerchantReceiveDto;
import admin.response.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restservice.model.AgentMerchantEntity;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.model.GatewayMerchantEntity;
import uz.pdp.restservice.service.base.BaseService;

import javax.validation.Valid;
import java.util.Optional;

import static uz.pdp.restservice.service.base.ResponseMessage.ID_EMPTY;

@RestController
@RequestMapping("/gatewayMerchant")
public class GatewayMerchantController {

    private final BaseService baseService;

    public GatewayMerchantController(@Qualifier("gatewayMerchantService") BaseService baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/add")
    public ApiResponse add(@Valid @RequestBody GatewayMerchantReceiveDto gatewayMerchantReceiveDto) {
        return baseService.add(gatewayMerchantReceiveDto);
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
    public ApiResponse edit(@PathVariable Long id,@Valid @RequestBody GatewayMerchantReceiveDto gatewayMerchantReceiveDto) {
        return baseService.edit(id, gatewayMerchantReceiveDto);
    }

    @GetMapping("/getById/{id}")
    public GatewayMerchantEntity getById(@PathVariable Long id){
        return (GatewayMerchantEntity) baseService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Optional<Long> id) {
        if (id.isPresent())
            return baseService.delete(id.get());

        else
            return new ApiResponse<>(1, ID_EMPTY);
    }
}
