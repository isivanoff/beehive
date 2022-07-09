package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.ApiaryAddDTO;
import bg.beesoft.beehive.model.dto.ApiaryEditDTO;
import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.service.AddressService;
import bg.beesoft.beehive.service.ApiaryService;
import bg.beesoft.beehive.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/apiaries")
public class ApiaryController {
    private ApiaryService apiaryService;
    private AddressService addressService;
    private UserService userService;

    public ApiaryController(ApiaryService apiaryService, AddressService addressService, UserService userService) {
        this.apiaryService = apiaryService;
        this.addressService = addressService;
        this.userService = userService;
    }

    @ModelAttribute("apiaryAddDTO")
    public ApiaryAddDTO initModel() {
        return new ApiaryAddDTO();
    }


    @GetMapping("/all")
    public String apiaries(Model model, Principal principal) {
        model.addAttribute("apiaries", apiaryService.findAllApiaries(principal.getName()));
        return "apiaries";
    }

    @GetMapping("/add")
    public String add() {
        return "apiary-add";
    }

    @PostMapping("/add")
    public String add(@Valid ApiaryAddDTO apiaryAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails userDetails) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("apiaryAddDTO", apiaryAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.apiaryAddDTO", bindingResult);
            return "redirect:/apiaries/add";
        }

        Optional<AddressEntity> optionalAddress = addressService.findByCityAndCountryAndStreet(apiaryAddDTO.getCity(), apiaryAddDTO.getCountry(), apiaryAddDTO.getStreet());

        if (optionalAddress.isPresent()) {
            redirectAttributes.addFlashAttribute("apiaryAddDTO", apiaryAddDTO);
            redirectAttributes.addFlashAttribute("usedAddress", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.apiaryAddDTO", bindingResult);
            return "redirect:/apiaries/add";

        }

        apiaryService.save(apiaryAddDTO, userDetails);
        return "redirect:/apiaries/all";

    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) {
        ApiaryEditDTO apiaryEditDTO = apiaryService.findById(id);
        model.addAttribute("apiaryEditDTO", apiaryEditDTO);
        return "apiary-edit";
    }

    @GetMapping("/edit/{id}/error")
    public String editError() {
        return "apiary-edit";
    }


    @PutMapping("/edit/{id}")
    public String edit(@Valid ApiaryEditDTO apiaryEditDTO,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("apiaryEditDTO", apiaryEditDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.apiaryEditDTO", bindingResult);
            return "redirect:/apiaries/edit/{id}/error";
        }

        Optional<AddressEntity> optionalAddress = addressService.findByCityAndCountryAndStreet(apiaryEditDTO.getAddressCity(), apiaryEditDTO.getAddressCountry(), apiaryEditDTO.getAddressStreet());

        if (optionalAddress.isPresent() && optionalAddress.get().getId() != apiaryService.findApiaryAddressId(apiaryEditDTO.getId())) {
            redirectAttributes.addFlashAttribute("apiaryEditDTO", apiaryEditDTO);
            redirectAttributes.addFlashAttribute("usedAddress", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.apiaryEditDTO", bindingResult);
            return "redirect:/apiaries/edit/{id}/error";

        }

        apiaryService.update(apiaryEditDTO, optionalAddress);


        return "redirect:/apiaries/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        apiaryService.deleteById(id);
        return "redirect:/apiaries/all";
    }

}
