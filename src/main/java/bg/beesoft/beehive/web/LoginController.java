package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.exception.NotFoundException;
import bg.beesoft.beehive.service.UserService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {


    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/login")
    public String login() {
        return "auth-login";
    }

    // NOTE: This should be post mapping!
    @PostMapping("/users/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, userName);
        try {
            if (!userService.findByEmail(userName).isActive()) {
                redirectAttributes.addFlashAttribute("inactive", true);
            } else {
                redirectAttributes.addFlashAttribute("bad_credentials", true);
            }
        } catch (NotFoundException ex) {
            redirectAttributes.addFlashAttribute("bad_credentials", true);
        }
        return "redirect:/users/login";
    }

}
