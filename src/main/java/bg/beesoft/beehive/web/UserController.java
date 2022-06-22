package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.UserLoginDTO;
import bg.beesoft.beehive.model.dto.UserRegisterDTO;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.service.UserService;
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

    public UserController(UserService userService) {
        this.userService = userService;
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

        Optional<UserEntity> optionalUser = userService.findByUsernameAndPassword(userModel.getEmail(),userModel.getPassword());
        if (optionalUser.isEmpty()){
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("mismatchingData", true);
            return "redirect:/users/login";

        }

        userService.login(userModel);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {
        userService.logout();
        return "redirect:/";
    }


}
