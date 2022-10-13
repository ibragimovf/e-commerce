package uz.pdp.restservice.controller;

import admin.receive.AgentMerchantReceiveDto;
import admin.response.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import uz.pdp.restservice.model.AgentMerchantEntity;
import uz.pdp.restservice.service.base.BaseService;

import javax.validation.Valid;
import java.util.Optional;

import static uz.pdp.restservice.service.base.ResponseMessage.ID_EMPTY;

@RestController
@RequestMapping("/agentMerchant")
public class AgentMerchantController {

    private final BaseService baseService;

    public AgentMerchantController(@Qualifier("agentMerchantService") BaseService baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/add")
    public ApiResponse add(@Valid @RequestBody AgentMerchantReceiveDto agentMerchantReceiveDto) {
        return baseService.add(agentMerchantReceiveDto);
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
    public ApiResponse edit(@PathVariable Long id,@Valid @RequestBody AgentMerchantReceiveDto agentMerchantReceiveDto) {
        return baseService.edit(id, agentMerchantReceiveDto);
    }

    @GetMapping("/getById/{id}")
    public AgentMerchantEntity getById(@PathVariable Long id){
        return (AgentMerchantEntity) baseService.getById(id);
    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Optional<Long> id) {
        if (id.isPresent())
            return baseService.delete(id.get());

        else
            return new ApiResponse<>(1, ID_EMPTY);
    }
}
