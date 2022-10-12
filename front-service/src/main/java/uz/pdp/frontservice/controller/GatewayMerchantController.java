package uz.pdp.frontservice.controller;

import admin.receive.GatewayMerchantReceiveDto;
import admin.response.ApiResponse;
import admin.response.GatewayMerchantResponse;
import admin.response.GatewayResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/admin/gateway/merchant")
public class GatewayMerchantController {

    private final RestTemplate restTemplate;

    public GatewayMerchantController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getGatewayMerchantList(Model model) {
        ResponseEntity<ApiResponse> gatewayMerchantList = restTemplate.getForEntity("http://localhost:8080/gateway/merchant/get", ApiResponse.class);
        ResponseEntity<ApiResponse> gatewayList = restTemplate.getForEntity("http://localhost:8080/gateway/get", ApiResponse.class);
        model.addAttribute("gateway_merchant", new GatewayMerchantReceiveDto());
        model.addAttribute("gateway_merchant_list", (List<GatewayMerchantResponse>) gatewayMerchantList.getBody().getT());
        model.addAttribute("gateway_list", (List<GatewayResponse>) gatewayList.getBody().getT());
        return "/admin/service/gateway_merchant";
    }

    @PostMapping("/add")
    public String addGatewayMerchant(Model model, @ModelAttribute GatewayMerchantReceiveDto gateway) {
        restTemplate.postForEntity("http://localhost:8080/gateway/merchant/add", gateway, ApiResponse.class);
        return getGatewayMerchantList(model);
    }

    @PutMapping("/update")
    public String updateGatewayMerchant(Model model, @ModelAttribute GatewayMerchantReceiveDto gatewayMerchant) {
        restTemplate.postForEntity("http://localhost:8080/gateway/merchant/update", gatewayMerchant, ApiResponse.class);
        return getGatewayMerchantList(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteGatewayMerchant(@PathVariable("id") long id, Model model) {
        restTemplate.delete("http://localhost:8080/gatewayMerchant/delete/{id}(id=" + id + ")");
        return getGatewayMerchantList(model);
    }
}
