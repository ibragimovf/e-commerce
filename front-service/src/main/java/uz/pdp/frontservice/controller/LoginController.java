package uz.pdp.frontservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/")
public class LoginController {

    private final RestTemplate restTemplate;

    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public String getLoginPage() {
        return "/admin/service/login/login";
    }

    @PostMapping("/login")
    public String getAgentList(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model
    ) {
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        if (!username.equals("a") && !password.equals("a")) {
            return "redirect:/";
        }
//        ResponseEntity<ApiResponse> responseEntity = restTemplate.getForEntity("http://REST-SERVICE/api/login", ApiResponse.class, username, password);
//        ApiResponse apiResponse = responseEntity.getBody();
//        Boolean success = (Boolean) apiResponse.getT();
//        if (!success) {
//            model.addAttribute("is_success", false);
//            return "redirect:/";
//        }
        return "redirect:/admin/agent/list/1";
    }
}
