package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.UserRegisterDTO;
import bg.beesoft.beehive.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;

    @ModelAttribute("userModel")
    public void initUserModel(Model model){
        model.addAttribute("userModel",new UserRegisterDTO());
    }

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/register")
    public String register() {
        return "auth-register";
    }

    @PostMapping("/users/register")
    public String register(@Valid UserRegisterDTO userRegisterDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        boolean passwordsDoNotMatch = !userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());

        if (bindingResult.hasErrors() || passwordsDoNotMatch) {
            if(passwordsDoNotMatch){
                redirectAttributes.addFlashAttribute("passwordsDoNotMatch",true);
            }
            redirectAttributes.addFlashAttribute("userModel", userRegisterDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);
            return "redirect:/users/register";
        }

        userService.registerAndLogin(userRegisterDTO);
        return "redirect:/";
    }
}
