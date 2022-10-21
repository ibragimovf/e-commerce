package uz.pdp.frontservice.controller;

import admin.response.ApiResponse;
import admin.response.TransactionResponseDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static uz.pdp.frontservice.service.Service.getPage;
import static uz.pdp.frontservice.service.Service.isEmpty;

@Controller
@RequestMapping("/admin/transaction")
public class TransactionController {
    private final RestTemplate restTemplate;

    public TransactionController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/list/{pageSize}")
    public String getTransaction(Model model, @PathVariable Optional<Integer> pageSize) {
        List<TransactionResponseDto> transactionList = (List<TransactionResponseDto>) restTemplate.getForEntity("http://localhost:8080/api/transaction/list/" + getPage(pageSize) + "", ApiResponse.class).getBody().getT();
        model.addAttribute("transactionList", transactionList);
        model.addAttribute("page", getPage(pageSize));
        model.addAttribute("isEmpty", isEmpty(transactionList.size()));
        return "/admin/service/transaction/list";
    }
}
