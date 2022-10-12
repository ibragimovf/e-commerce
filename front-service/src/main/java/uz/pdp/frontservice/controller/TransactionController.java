package uz.pdp.frontservice.controller;

import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/admin/transaction")
public class TransactionController {
    private final RestTemplate restTemplate;

    public TransactionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list")
    public String getTransactionList(Model model) {
        ResponseEntity<ApiResponse> transaction_list = restTemplate.getForEntity("http://localhost:8080/transaction/get", ApiResponse.class);
//        model.addAttribute("transaction_list", (List<TransactionResponse>) transaction_list.getBody().getT());
        return "/admin/service/transaction";
    }

}

