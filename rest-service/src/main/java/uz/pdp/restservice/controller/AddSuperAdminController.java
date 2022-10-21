package uz.pdp.restservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.restservice.service.users.UserService;

@RestController
@RequestMapping("/test")
public class AddSuperAdminController {

    private final UserService userService;

    public AddSuperAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/addSuperAdmin")
    public String getMessage() {
        userService.addSuperAdmin();
        return "success";
    }

}
