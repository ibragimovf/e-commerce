package uz.pdp.frontservice.controller;

import admin.receive.AgentMerchantReceiveDto;
import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/admin/agent/merchant")
public class AgentMerchantController {

    private final RestTemplate restTemplate;

    public AgentMerchantController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getAgentMerchantList(
            Model model
    ) {
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/agentMerchant/get", ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();
        model.addAttribute("agentMerchant", new AgentMerchantReceiveDto());
//        model.addAttribute("agentMerchant_list", (List<AgentMerchantResponse>) apiResponse.getT());
        return "/admin/service/list";
    }

    @PostMapping("/add")
    public String addAgentMerchant(
            Model model,
            @ModelAttribute AgentMerchantReceiveDto agentMerchant
    ) {
        restTemplate.postForEntity("http://localhost:8080/agentMerchant/add", agentMerchant, ApiResponse.class);
        return getAgentMerchantList(model);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAgentMerchant(
            Model model,
            @PathVariable Optional<Long> id
    ) {
        if (id.isPresent()) {
            restTemplate.postForEntity("http://localhost:8080/agentMerchant/delete", id.get(), ApiResponse.class);
        }
        return getAgentMerchantList(model);
    }
}
