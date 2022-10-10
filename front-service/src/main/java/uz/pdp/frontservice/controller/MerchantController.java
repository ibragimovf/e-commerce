package uz.pdp.frontservice.controller;

import admin.receive.MerchantReceiveDto;
import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Controller
@RequestMapping("/admin/merchant")
public class MerchantController {

    private final RestTemplate restTemplate;

    public MerchantController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getMerchantList(
            Model model
    ) {
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/merchant/get", ApiResponse.class);
        ApiResponse apiResponse = responseEntity.getBody();
        model.addAttribute("merchant", new MerchantReceiveDto());
//        model.addAttribute("merchant_list", (List<MerchantResponse>) apiResponse.getT());
        return "/admin/service/list";
    }

    @PostMapping("/add")
    public String addMerchant(
            Model model,
            @ModelAttribute MerchantReceiveDto merchant
    ) {
        restTemplate.postForEntity("http://localhost:8080/merchant/add", merchant, ApiResponse.class);
        return getMerchantList(model);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteMerchant(
            Model model,
            @PathVariable Optional<Long> id
    ) {
        if (id.isPresent()) {
            restTemplate.postForEntity("http://localhost:8080/merchant/delete", id.get(), ApiResponse.class);
        }
        return getMerchantList(model);
    }
}
