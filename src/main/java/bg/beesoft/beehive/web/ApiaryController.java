package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.ApiaryAddDTO;
import bg.beesoft.beehive.model.dto.ApiaryEditDTO;
import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.model.view.ApiaryView;
import bg.beesoft.beehive.model.view.BeehiveView;
import bg.beesoft.beehive.service.AddressService;
import bg.beesoft.beehive.service.ApiaryService;
import bg.beesoft.beehive.service.BeehiveService;
import bg.beesoft.beehive.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/apiaries")
public class ApiaryController {
    private ApiaryService apiaryService;
    private AddressService addressService;
    private UserService userService;
    private BeehiveService beehiveService;

    public ApiaryController(ApiaryService apiaryService, AddressService addressService, UserService userService, BeehiveService beehiveService) {
        this.apiaryService = apiaryService;
        this.addressService = addressService;
        this.userService = userService;
        this.beehiveService = beehiveService;
    }

    @ModelAttribute("apiaryAddDTO")
    public ApiaryAddDTO initModel() {
        return new ApiaryAddDTO();
    }


    @GetMapping("/all")
    public String apiaries(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("apiaries", apiaryService.findAllApiaries(userDetails.getUsername()));
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
    public String edit(Model model, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        ApiaryEditDTO apiaryEditDTO = apiaryService.findById(id,userDetails);
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
                       RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
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

        apiaryService.update(apiaryEditDTO, optionalAddress,userDetails);


        return "redirect:/apiaries/all";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        apiaryService.deleteById(id,userDetails.getUsername());
        return "redirect:/apiaries/all";
    }


    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable Long id,
                       @PageableDefault(
                               sort = "referenceNumber",
                               direction = Sort.Direction.ASC,
                               page = 0,
                               size = 10) Pageable pageable,
                       @AuthenticationPrincipal UserDetails userDetails) {
        ApiaryView apiary = apiaryService.findViewById(id,userDetails);
        Page<BeehiveView> beehives = beehiveService.findViewAllByApiaryId(id,pageable,userDetails);

        model.addAttribute("apiary", apiary);
        model.addAttribute("beehives", beehives);

        return "beehives";
    }


}
