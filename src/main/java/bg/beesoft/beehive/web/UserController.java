package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.ChangePasswordDTO;
import bg.beesoft.beehive.model.dto.UserEditDTO;
import bg.beesoft.beehive.service.ApiaryService;
import bg.beesoft.beehive.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private ApiaryService apiaryService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, ApiaryService apiaryService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.apiaryService = apiaryService;
    }

    @ModelAttribute("changePasswordDTO")
    public ChangePasswordDTO initChangePassword() {
        return new ChangePasswordDTO();
    }

    @ModelAttribute("userEditDTO")
    public UserEditDTO initModel() {
        return new UserEditDTO();
    }

    @GetMapping("/profile")
    String profile(@AuthenticationPrincipal UserDetails userDetails,
                   Model model) {
        UserEditDTO userEditDTO = userService.getEditDetails(userDetails.getUsername());
        model.addAttribute("user", userEditDTO);
        return "profile";
    }

    @GetMapping("/profile/edit")
    String edit(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        UserEditDTO userEditDTO = userService.getEditDetails(userDetails.getUsername());
        model.addAttribute("userEditDTO", userEditDTO);
        return "profile-edit";
    }

    @GetMapping("/profile/edit/error")
    public String editError() {
        return "profile-edit";
    }

    @PutMapping("/profile/edit")
    String edit(@Valid UserEditDTO userEditDTO,
                BindingResult bindingResult,
                RedirectAttributes redirectAttributes,
                @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditDTO", userEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditDTO", bindingResult);
            return "redirect:/users/profile/edit/error";
        }

        userService.update(userEditDTO.setEmail(userDetails.getUsername()));

        return "redirect:/users/profile";
    }

    @DeleteMapping("/delete")
    public String delete(@AuthenticationPrincipal UserDetails userDetails) {
        apiaryService.deleteAllApiaries(userDetails.getUsername());
        userService.deleteByEmail(userDetails.getUsername());

        return "redirect:/";
    }

    @GetMapping("/password-change")
    public String changePassword(){
        return "password-change";
    }

    @PutMapping("/password-change")
    public String changePassword(@Valid ChangePasswordDTO changePasswordDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 @AuthenticationPrincipal UserDetails userDetails){
        boolean passwordsDoNotMatch = !changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmNewPassword());

        if (bindingResult.hasErrors() || passwordsDoNotMatch) {
            if (passwordsDoNotMatch) {
                redirectAttributes.addFlashAttribute("passwordsDoNotMatch", true);
            }
            redirectAttributes.addFlashAttribute("changePasswordDTO", changePasswordDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDTO", bindingResult);
            return "redirect:/users/password-change";
        }

        if(!passwordEncoder.matches(changePasswordDTO.getPassword(),userDetails.getPassword())){
            redirectAttributes.addFlashAttribute("changePasswordDTO", changePasswordDTO);
            redirectAttributes.addFlashAttribute("incorrectPassword", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDTO", bindingResult);
            return "redirect:/users/password-change";
        }

        if(passwordEncoder.matches(changePasswordDTO.getNewPassword(),userDetails.getPassword())){
            redirectAttributes.addFlashAttribute("changePasswordDTO", changePasswordDTO);
            redirectAttributes.addFlashAttribute("alreadyUsed", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordDTO", bindingResult);
            return "redirect:/users/password-change";
        }

        userService.updatePassword(userDetails.getUsername(),changePasswordDTO.getNewPassword());


        return "redirect:/users/profile";
    }


}
