package uz.pdp.frontservice.controller;

import admin.receive.GatewayMerchantReceiveDto;
import admin.response.ApiResponse;
import admin.response.GatewayMerchantResponse;
import admin.response.GatewayResponse;
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
@RequestMapping("/admin/gateway/merchant")
public class GatewayMerchantController {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GatewayMerchantController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/list/{pageSize}")
    public String getGatewayMerchantList(Model model, @PathVariable Optional<Integer> pageSize, @ModelAttribute GatewayMerchantReceiveDto gatewayMerchant) {
        List<GatewayMerchantResponse> gatewayMerchantList = (List<GatewayMerchantResponse>) restTemplate.postForEntity("http://localhost:8080/api/gateway/merchant/list/" + getPage(pageSize) + "", gatewayMerchant, ApiResponse.class).getBody().getT();
        List<GatewayResponse> gatewayList = (List<GatewayResponse>) restTemplate.getForEntity("http://localhost:8080/api/gateway/list/all", ApiResponse.class).getBody().getT();
        model.addAttribute("gatewayMerchant", new GatewayMerchantReceiveDto());
        model.addAttribute("gatewayMerchantList", gatewayMerchantList);
        model.addAttribute("gatewayList", gatewayList);
        model.addAttribute("page", getPage(pageSize));
        model.addAttribute("isEmpty", isEmpty(gatewayMerchantList.size()));
        return "admin/service/gatewayMerchant/list";
    }

    @GetMapping("/list/get/{id}")
    public String getGatewayMerchant(Model model, @PathVariable long id) {
        Object t = restTemplate.getForEntity("http://localhost:8080/api/gateway/merchant/get/" + id + "", ApiResponse.class).getBody().getT();
        GatewayMerchantResponse gatewayMerchantResponse = objectMapper.convertValue(t, GatewayMerchantResponse.class);
        List<GatewayResponse> gatewayList = (List<GatewayResponse>) restTemplate.getForEntity("http://localhost:8080/api/gateway/list/all", ApiResponse.class).getBody().getT();
        model.addAttribute("gatewayMerchant", gatewayMerchantResponse);
        model.addAttribute("gatewayList", gatewayList);
        return "admin/service/gatewayMerchant/edit";
    }

    @PostMapping("/add")
    public String addGatewayMerchant(@ModelAttribute GatewayMerchantReceiveDto gateway) {
        restTemplate.postForEntity("http://localhost:8080/api/gateway/merchant/add", gateway, ApiResponse.class);
        return "redirect:/admin/gateway/merchant/list/1";
    }

    @PostMapping("/edit/{id}")
    public String editGatewayMerchant(@PathVariable("id") long id, @ModelAttribute GatewayMerchantReceiveDto gateway) {
        restTemplate.postForEntity("http://localhost:8080/api/gateway/merchant/edit/" + id + "", gateway, ApiResponse.class);
        return "redirect:/admin/gateway/merchant/list/1";
    }

    @GetMapping("/delete/{id}")
    public String deleteGatewayMerchant(@PathVariable("id") long id) {
        restTemplate.delete("http://localhost:8080/api/gateway/merchant/delete/{id}", id);
        return "redirect:/admin/gateway/merchant/list/1";
    }
}
