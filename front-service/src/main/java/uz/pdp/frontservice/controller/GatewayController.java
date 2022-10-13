package uz.pdp.frontservice.controller;

import admin.receive.GatewayReceiveDto;
import admin.response.ApiResponse;
import admin.response.GatewayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/admin/gateway")
public class
GatewayController {

    private final RestTemplate restTemplate;

    public GatewayController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getGatewayList(Model model) {
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/gateway/get", ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();
        model.addAttribute("gateway", new GatewayReceiveDto());
        model.addAttribute("gateway_list", (List<GatewayResponse>) apiResponse.getT());
        return "admin/service/gateway";
    }

    @PostMapping("/add")
<<<<<<< HEAD
    public String addGateway(
            Model model,
            @ModelAttribute GatewayReceiveDto gateway
    ) {
        ResponseEntity<ApiResponse> responseEntity
                = restTemplate.postForEntity("http://localhost:8080/gateway/add", gateway, ApiResponse.class);
=======
    public String addGateway(Model model, @ModelAttribute GatewayReceiveDto gateway) {
        restTemplate.postForEntity("http://localhost:8080/gateway/add", gateway, ApiResponse.class);
>>>>>>> f6b11f6437509dbd86ca146da519dc588e08ed5a
        return getGatewayList(model);
    }

    @PutMapping("/update")
<<<<<<< HEAD
    public String updateGateway(
            Model model,
            @ModelAttribute GatewayReceiveDto gateway
    ) {
        ResponseEntity<ApiResponse> responseEntity
                = restTemplate.postForEntity("http://localhost:8080/gateway/update", gateway, ApiResponse.class);
=======
    public String updateGateway(Model model, @ModelAttribute GatewayReceiveDto gateway) {
        restTemplate.postForEntity("http://localhost:8080/gateway/update", gateway, ApiResponse.class);
>>>>>>> f6b11f6437509dbd86ca146da519dc588e08ed5a
        return getGatewayList(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteGateway(@PathVariable("id") long id, Model model) {
        restTemplate.delete("http://localhost:8080/gateway/delete/{id}(id=" + id + ")");
        return getGatewayList(model);
    }
}
