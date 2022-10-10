package uz.pdp.frontservice.controller;

import admin.receive.GatewayReceiveDto;
import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/admin/gateway")
public class GatewayController {

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getGatewayList(
            Model model
    ) {
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/gateway/get", ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();
        model.addAttribute("gateway", new GatewayReceiveDto());
//        model.addAttribute("gateway_list", (List<GatewayResponse>) apiResponse.getT());
        return "/admin/service/list";
    }

    @PostMapping("/add")
    public String addGateway(
            Model model,
            @ModelAttribute GatewayReceiveDto gateway
    ) {
        restTemplate.postForEntity("http://localhost:8080/gateway/add", gateway, ApiResponse.class);
        return getGatewayList(model);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGateway(
            Model model,
            @PathVariable Optional<Long> id
    ) {
        if (id.isPresent()) {
            restTemplate.postForEntity("http://localhost:8080/gateway/delete", id.get(), ApiResponse.class);
        }
        return getGatewayList(model);
    }
}
