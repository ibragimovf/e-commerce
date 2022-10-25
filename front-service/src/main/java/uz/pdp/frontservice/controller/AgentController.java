package uz.pdp.frontservice.controller;

import admin.receive.AgentReceiveDto;
import admin.response.AgentResponse;
import admin.response.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static uz.pdp.frontservice.service.Service.getPage;
import static uz.pdp.frontservice.service.Service.isEmpty;

@Controller
@RequestMapping("/admin/agent")
public class AgentController {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public AgentController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/list/{pageSize}")
    public String getAgentList(Model model, @PathVariable Optional<Integer> pageSize, @ModelAttribute AgentReceiveDto searchAgent) {
        List<AgentResponse> agentList = (List<AgentResponse>) restTemplate.postForEntity("http://localhost:8080/api/agent/list/" + getPage(pageSize) + "", searchAgent, ApiResponse.class).getBody().getT();
        model.addAttribute("agent", new AgentReceiveDto());
        model.addAttribute("agentList", agentList);
        model.addAttribute("page", getPage(pageSize));
        model.addAttribute("isEmpty", isEmpty(agentList.size()));
        return "/admin/service/agent/list";
    }

    @GetMapping("/list/get/{id}")
    public String getAgent(Model model, @PathVariable long id) {
        Object t = restTemplate.getForEntity("http://localhost:8080/api/agent/get/" + id + "", ApiResponse.class).getBody().getT();
        AgentResponse agentResponse = objectMapper.convertValue(t, AgentResponse.class);
        model.addAttribute("agent", agentResponse);
        return "admin/service/agent/edit";
    }

    @PostMapping("/add")
    public String addAgent(@ModelAttribute AgentReceiveDto agent) {
        restTemplate.postForEntity("http://localhost:8080/api/agent/add", agent, ApiResponse.class);
        return "redirect:/admin/agent/list/1";
    }

    @PostMapping("/edit/{id}")
    public String editAgent(@PathVariable("id") long id, @ModelAttribute AgentReceiveDto agent) {
        restTemplate.postForEntity("http://localhost:8080/api/agent/edit/" + id + "", agent, ApiResponse.class);
        return "redirect:/admin/agent/list/1";
    }

    @GetMapping("/delete/{id}")
    public String deleteAgent(@PathVariable("id") long id) {
        restTemplate.delete("http://localhost:8080/api/agent/delete/{id}", id);
        return "redirect:/admin/agent/list/1";
    }
}
