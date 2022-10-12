package uz.pdp.frontservice.controller;

import admin.receive.AgentReceiveDto;
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
@RequestMapping("/admin/agent")
public class AgentController {

    private final RestTemplate restTemplate;

    public AgentController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getAgentList(Model model) {
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/agent/get", ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();
        model.addAttribute("agent", new AgentReceiveDto());
        model.addAttribute("agent_list", (List<AgentResponse>) apiResponse.getT());
        return "/admin/service/agent";
    }

    @PostMapping("/add")
    public String addAgent(Model model, @ModelAttribute AgentReceiveDto agent) {
        restTemplate.postForEntity("http://localhost:8080/agent/add", agent, ApiResponse.class);
        return getAgentList(model);
    }
}
