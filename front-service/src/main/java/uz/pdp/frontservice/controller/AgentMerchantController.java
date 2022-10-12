package uz.pdp.frontservice.controller;

import admin.receive.AgentMerchantReceiveDto;
import admin.response.AgentMerchantResponse;
import admin.response.AgentResponse;
import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/admin/agent/merchant")
public class AgentMerchantController {

    private final RestTemplate restTemplate;

    public AgentMerchantController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getAgentMerchantList(Model model) {
        ResponseEntity<ApiResponse> agentMerchantList = restTemplate.getForEntity("http://localhost:8080/agent/merchant/get", ApiResponse.class);
        ResponseEntity<ApiResponse> agentList = restTemplate.getForEntity("http://localhost:8080/agent/get", ApiResponse.class);
        model.addAttribute("agent_merchant", new AgentMerchantReceiveDto());
        model.addAttribute("agent_merchant_list", (List<AgentMerchantResponse>) agentMerchantList.getBody().getT());
        model.addAttribute("agent_list", (List<AgentResponse>) agentList.getBody().getT());
        return "/admin/service/agent_merchant";
    }

    @PostMapping("/add")
    public String addAgentMerchant(Model model, @ModelAttribute AgentMerchantReceiveDto agentMerchant) {
        restTemplate.postForEntity("http://localhost:8080/agent/merchant/add", agentMerchant, ApiResponse.class);
        return getAgentMerchantList(model);
    }


}

