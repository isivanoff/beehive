package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.UserLoginDTO;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @ModelAttribute("userModel")
    public void initUserModel(Model model){
        model.addAttribute("userModel",new UserLoginDTO());
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid UserLoginDTO userModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userModel",userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel",bindingResult);
            return  "redirect:/users/login";
        }

        if(userService.login(userModel)){
            return "redirect:/";
        }else{
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("mismatchingData", true);
            return "redirect:/users/login";
        }

    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }


}
