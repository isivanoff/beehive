package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import bg.beesoft.beehive.model.dto.BeehiveEditDTO;
import bg.beesoft.beehive.model.view.ApiaryView;
import bg.beesoft.beehive.model.view.BeehiveFullView;
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

    @ModelAttribute("beehiveEditDTO")
    public BeehiveEditDTO initEditModel() {
        return new BeehiveEditDTO();
    }

    @GetMapping("/add")
    public String add(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam Optional<Long> apiary, BeehiveAddDTO beehiveAddDTO) {
        boolean hasApiary = apiary.isPresent();

        List<ApiaryView> apiaries = apiaryService.viewAllByBeekeperEmail(userDetails.getUsername());
        model.addAttribute("apiaries", apiaries);
        model.addAttribute("hasApiary", hasApiary);
        if (hasApiary) {
            beehiveAddDTO.setApiaryId(apiary.get());
        }
        return "beehive-add";
    }

    @GetMapping("/add/error")
    public String add() {
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

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("beehiveAddDTO", beehiveAddDTO);
            redirectAttributes.addFlashAttribute("apiaries", apiaries);
            redirectAttributes.addFlashAttribute("hasApiary", hasApiary);
            redirectAttributes.addFlashAttribute("invalidTemperament", beehiveAddDTO.getTemperament() == null);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveAddDTO", bindingResult);
            return "redirect:/beehives/add/error" + (hasApiary ? "?apiary=" + apiary.get().toString() : "");
        }

        boolean numberIsTaken = apiaryService.apiaryAlreadyHasBeehiveNumber(beehiveAddDTO.getApiaryId(), beehiveAddDTO.getReferenceNumber());

        if (numberIsTaken) {
            redirectAttributes.addFlashAttribute("beehiveAddDTO", beehiveAddDTO);
            redirectAttributes.addFlashAttribute("apiaries", apiaries);
            redirectAttributes.addFlashAttribute("hasApiary", hasApiary);
            redirectAttributes.addFlashAttribute("numberIsTaken", true);
            redirectAttributes.addFlashAttribute("invalidTemperament", beehiveAddDTO.getTemperament() == null);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveAddDTO", bindingResult);
            return "redirect:/beehives/add/error" + (apiary.isPresent() ? "?apiary=" + beehiveAddDTO.getApiaryId().toString() : "");
        }

        beehiveService.addBeehive(beehiveAddDTO, userDetails.getUsername());


        return "redirect:/apiaries/view/" + beehiveAddDTO.getApiaryId().toString();


    }

    @GetMapping("/view/{id}")
    public String view(Model model, @PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        BeehiveFullView beehive = beehiveService.viewById(id,userDetails);
        model.addAttribute("beehive", beehive);
        return "beehive-view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails) {
        Long apiaryId = beehiveService.findApiaryIdByBeehiveId(id,userDetails);
        beehiveService.deleteById(id,userDetails);
        return "redirect:/apiaries/view/" + apiaryId;
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        List<ApiaryView> apiaries = apiaryService.viewAllByBeekeperEmail(userDetails.getUsername());
        BeehiveEditDTO beehiveEditDTO = beehiveService.getEditDTOById(id,userDetails);
        beehiveEditDTO.setId(id);

        model.addAttribute("beehiveEditDTO",beehiveEditDTO);
        model.addAttribute("apiaries", apiaries);

        return "beehive-edit";
    }

    @GetMapping("/edit/{id}/error")
    public String editError() {
        return "beehive-edit";
    }

    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id, @Valid BeehiveEditDTO beehiveEditDTO,BindingResult bindingResult, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails){


        List<ApiaryView> apiaries = apiaryService.viewAllByBeekeperEmail(userDetails.getUsername());

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("beehiveEditDTO", beehiveEditDTO);
            redirectAttributes.addFlashAttribute("apiaries", apiaries);
            redirectAttributes.addFlashAttribute("invalidTemperament", beehiveEditDTO.getTemperament() == null);

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveEditDTO", bindingResult);
            return "redirect:/beehives/edit/" + id.toString() + "/error";
        }

        boolean numberIsTaken = apiaryService.apiaryAlreadyHasBeehiveNumber(beehiveEditDTO.getApiaryId(), beehiveEditDTO.getReferenceNumber());
        Long idByReferenceNumber = apiaryService.findIdByReferenceNumberInApiary(beehiveEditDTO.getApiaryId(),beehiveEditDTO.getReferenceNumber());

        if (numberIsTaken && idByReferenceNumber != beehiveEditDTO.getId()) {
            redirectAttributes.addFlashAttribute("beehiveEditDTO", beehiveEditDTO);
            redirectAttributes.addFlashAttribute("apiaries", apiaries);
            redirectAttributes.addFlashAttribute("invalidTemperament", beehiveEditDTO.getTemperament() == null);

            redirectAttributes.addFlashAttribute("numberIsTaken", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.beehiveEditDTO", bindingResult);
            return "redirect:/beehives/edit/"  + id.toString() + "/error";
        }

        beehiveService.updateBeehive(beehiveEditDTO,userDetails,id);

        return "redirect:/beehives/view/" + beehiveEditDTO.getId();
    }


}
