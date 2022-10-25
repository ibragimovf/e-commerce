package uz.pdp.restservice.controller;

import admin.receive.AgentReceiveDto;
import admin.response.ApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import uz.pdp.restservice.service.AgentService;
import uz.pdp.restservice.service.base.BaseService;

import java.util.Optional;

import static uz.pdp.restservice.service.base.ResponseMessage.ID_EMPTY;
import static uz.pdp.restservice.service.base.ResponseMessage.SUCCESS;

@CrossOrigin(origins = "http://localhost:80")
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final BaseService baseService;

    public AgentController(@Qualifier("agentService") BaseService baseService, AgentService agentService) {
        this.baseService = baseService;
    }

    @PostMapping("/add")
    private ApiResponse add(@RequestBody AgentReceiveDto agentReceiveDto) {
        return baseService.add(agentReceiveDto);
    }

    @PostMapping("/list/{page}")
    private ApiResponse getList(@PathVariable int page, @RequestBody AgentReceiveDto agentReceiveDto) {
        return baseService.getList(page, agentReceiveDto);
    }

    @GetMapping("/list/all")
    private ApiResponse getAllList() {
        return baseService.getAllList();
    }

    @GetMapping("/get/{id}")
    private ApiResponse get(@PathVariable long id) {
        return new ApiResponse<>(0, SUCCESS, baseService.getById(id));
    }

    @PostMapping("/edit/{id}")
    @ResponseBody
    private ApiResponse edit(@PathVariable long id, @RequestBody AgentReceiveDto agentReceiveDto) {
        return baseService.edit(agentReceiveDto, id);
    }

    @DeleteMapping("/delete/{id}")
    private ApiResponse delete(@PathVariable Optional<Long> id) {
        if (id.isPresent()) return baseService.delete(id.get());
        else return new ApiResponse<>(1, ID_EMPTY);
    }
}
