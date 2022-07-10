package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import bg.beesoft.beehive.model.view.ApiaryView;
import bg.beesoft.beehive.service.ApiaryService;
import bg.beesoft.beehive.service.BeehiveService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/beehives")
public class BeehiveController {

    private ApiaryService apiaryService;
    private BeehiveService beehiveService;

    public BeehiveController(ApiaryService apiaryService, BeehiveService beehiveService) {
        this.apiaryService = apiaryService;
        this.beehiveService = beehiveService;
    }

    @ModelAttribute("beehiveAddDTO")
    public BeehiveAddDTO initModel() {
        return new BeehiveAddDTO();
    }

    @GetMapping("/add")
    public String add(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam Optional<Long> apiary) {
        boolean hasApiary = apiary.isPresent();

        List<ApiaryView> apiaries = apiaryService.viewAllByBeekeperEmail(userDetails.getUsername());
        model.addAttribute("apiaries", apiaries);
        model.addAttribute("hasApiary", hasApiary);
        if (hasApiary) {
            model.addAttribute("apiary", apiary.get());
        }
        return "beehive-add";
    }

    @PostMapping("/add")
    public String add(@Valid BeehiveAddDTO beehiveAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @AuthenticationPrincipal UserDetails userDetails,
                      @RequestParam Optional<Long> apiary
    ) {

        List<ApiaryView> apiaries = apiaryService.viewAllByBeekeperEmail(userDetails.getUsername());

        boolean hasApiary = apiary.isPresent();

        apiary.ifPresent(beehiveAddDTO::setApiaryId);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("beehiveAddDTO", beehiveAddDTO);
            redirectAttributes.addFlashAttribute("apiaries", apiaries);
            redirectAttributes.addFlashAttribute("hasApiary", hasApiary);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveAddDTO", bindingResult);
            return "redirect:/beehives/add?apiary=" + (apiary.isPresent() ? apiary.get().toString() : "");
        }

        boolean numberIsTaken = apiaryService.apiaryAlreadyHasBeehiveNumber(beehiveAddDTO.getApiaryId(), beehiveAddDTO.getReferenceNumber());

        if (numberIsTaken) {
            redirectAttributes.addFlashAttribute("beehiveAddDTO", beehiveAddDTO);
            redirectAttributes.addFlashAttribute("apiaries", apiaries);
            redirectAttributes.addFlashAttribute("hasApiary", hasApiary);
            redirectAttributes.addFlashAttribute("numberIsTaken", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveAddDTO", bindingResult);
            return "redirect:/beehives/add?apiary=" + (apiary.isPresent() ? apiary.get().toString() : "");
        }

        beehiveService.addBeehive(beehiveAddDTO, userDetails.getUsername());


        return "redirect:/apiaries/view/" + beehiveAddDTO.getApiaryId().toString();


    }

}
