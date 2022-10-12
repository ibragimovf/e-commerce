package uz.pdp.frontservice.controller;

import admin.receive.MerchantReceiveDto;
import admin.response.ApiResponse;
import admin.response.GatewayMerchantResponse;
import admin.response.MerchantResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/admin/merchant")
public class MerchantController {

    private final RestTemplate restTemplate;

    public MerchantController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getMerchantList(Model model) {
        ResponseEntity<ApiResponse> merchantList = restTemplate.getForEntity("http://localhost:8080/merchant/get", ApiResponse.class);
        ResponseEntity<ApiResponse> gatewayMerchantList = restTemplate.getForEntity("http://localhost:8080/gateway/merchant/get", ApiResponse.class);
        model.addAttribute("merchant", new MerchantReceiveDto());
        model.addAttribute("merchant_list", (List<MerchantResponse>) merchantList.getBody().getT());
        model.addAttribute("gateway_merchant_list", (List<GatewayMerchantResponse>) gatewayMerchantList.getBody().getT());
        return "admin/service/merchant";
    }

    @PostMapping("/add")
    public String addMerchant(Model model, @ModelAttribute MerchantReceiveDto merchant) {
        restTemplate.postForEntity("http://localhost:8080/merchant/add", merchant, ApiResponse.class);
        return getMerchantList(model);
    }

    @PutMapping("/update")
    public String updateMerchant(Model model, @ModelAttribute MerchantReceiveDto merchant) {
        restTemplate.postForEntity("http://localhost:8080/merchant/update", merchant, ApiResponse.class);
        return getMerchantList(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteMerchant(@PathVariable("id") long id, Model model) {
        restTemplate.delete("http://localhost:8080/merchant/delete/{id}(id=" + id + ")");
        return getMerchantList(model);
    }
}
