package uz.pdp.restservice.controller;

import admin.receive.AgentReceiveDto;
import admin.response.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restservice.service.base.BaseService;

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
    public ApiResponse add(AgentReceiveDto agentReceiveDto) {
        return baseService.add(agentReceiveDto);
    }

    @GetMapping("/get")
    public ApiResponse getList() {
        return baseService.getList();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Optional<Long> id) {
        if (id.isPresent()) return baseService.delete(id.get());

        else return new ApiResponse<>(1, ID_EMPTY);
    }
}
