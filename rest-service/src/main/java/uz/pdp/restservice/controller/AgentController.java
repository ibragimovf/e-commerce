package uz.pdp.restservice.controller;

import admin.receive.AgentReceiveDto;
import admin.receive.GatewayReceiveDto;
import admin.response.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restservice.model.AgentEntity;
import uz.pdp.restservice.model.GatewayEntity;
import uz.pdp.restservice.service.base.BaseService;

import javax.validation.Valid;
import java.util.Optional;

import static uz.pdp.restservice.service.base.ResponseMessage.ID_EMPTY;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping("/agent")
public class AgentController {

    private final BaseService baseService;

    public AgentController(@Qualifier("agentService") BaseService baseService) {
        this.baseService = baseService;
    }

    @PostMapping("/add")
    public ApiResponse add(@Valid @RequestBody AgentReceiveDto agentReceiveDto) {
        return baseService.add(agentReceiveDto);
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
    public ApiResponse edit(@PathVariable Long id,@Valid @RequestBody AgentReceiveDto agentReceiveDto) {
        return baseService.edit(id, agentReceiveDto);
    }

    @GetMapping("/getById/{id}")
    public AgentEntity getById(@PathVariable Long id){
        return (AgentEntity) baseService.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Optional<Long> id) {
        if (id.isPresent()) return baseService.delete(id.get());

        else return new ApiResponse<>(1, ID_EMPTY);
    }
}
