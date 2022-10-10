package uz.pdp.frontservice.controller;

import admin.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final RestTemplate restTemplate;

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public String getLoginPage() {
        return "/admin/common/login";
    }

    @PostMapping("")
    public String getAgentList(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model
    ) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://localhost:8080/login", ApiResponse.class, username, password);
        ApiResponse apiResponse = responseEntity.getBody();
        Boolean success = (Boolean) apiResponse.getT();

        if (!success) {
            model.addAttribute("is_success", false);
            return "/admin/common/login";
        }

        return "redirect: /admin/agent/list";
    }
}
