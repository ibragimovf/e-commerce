package uz.pdp.frontservice.controller;

import admin.receive.GatewayMerchantReceiveDto;
import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/admin/gateway/merchant")
public class GatewayMerchantController {

    private final RestTemplate restTemplate;

    public GatewayMerchantController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getGatewayList(
            Model model
    ) {
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/gatewayMerchant/get", ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();
        model.addAttribute("gatewayMerchant", new GatewayMerchantReceiveDto());
//        model.addAttribute("gatewayMerchant_list", (List<GatewayResponse>) apiResponse.getT());
        return "/admin/service/list";
    }

    @PostMapping("/add")
    public String addGateway(
            Model model,
            @ModelAttribute GatewayMerchantReceiveDto gatewayMerchant
    ) {
        restTemplate.postForEntity("http://localhost:8080/gatewayMerchant/add", gatewayMerchant, ApiResponse.class);
        return getGatewayList(model);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteGateway(
            Model model,
            @PathVariable Optional<Long> id
    ) {
        if (id.isPresent()) {
            restTemplate.postForEntity("http://localhost:8080/gatewayMerchant/delete", id.get(), ApiResponse.class);
        }
        return getGatewayList(model);
    }
}
