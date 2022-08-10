package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.UserEditAdminDTO;
import bg.beesoft.beehive.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        model.addAttribute("users", userService.findAllUsersAdminView());
        return "users";
    }

    @ModelAttribute("user")
    public UserEditAdminDTO initUserModel(@PathVariable Optional<Long> id) {
        if (id.isPresent()) {
            return userService.findUserAdminEditById(id.get());
        }
        return new UserEditAdminDTO();
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(Model model, @PathVariable Long id) {
        model.addAttribute("userView", userService.findUserViewById(id));
        return "admin-user-edit";

    }

    @PutMapping("/users/edit/{id}")
    public String editUser(UserEditAdminDTO userEditAdminDTO, @PathVariable Long id) {
        userService.updateAdminDTO(userEditAdminDTO, id);

        return "redirect:/admin/users";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }


}
