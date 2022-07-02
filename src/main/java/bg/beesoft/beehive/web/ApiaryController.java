package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.ApiaryAddDTO;
import bg.beesoft.beehive.model.entity.AddressEntity;
import bg.beesoft.beehive.model.entity.ApiaryEntity;
import bg.beesoft.beehive.model.entity.UserEntity;
import bg.beesoft.beehive.service.AddressService;
import bg.beesoft.beehive.service.ApiaryService;
import bg.beesoft.beehive.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/apiaries")
public class ApiaryController {
    private ModelMapper modelMapper;
    private ApiaryService apiaryService;
    private AddressService addressService;
    private UserService userService;

    public ApiaryController(ModelMapper modelMapper, ApiaryService apiaryService, AddressService addressService, UserService userService) {
        this.modelMapper = modelMapper;
        this.apiaryService = apiaryService;
        this.addressService = addressService;
        this.userService = userService;
    }

    @ModelAttribute("apiaryAddDTO")
    public ApiaryAddDTO initModel() {
        return new ApiaryAddDTO();
    }

    @GetMapping("/all")
    public String apiaries(Model model,Principal principal) {
        model.addAttribute("apiaries",apiaryService.findAllApiaries(principal.getName()));

        return "apiaries";
    }

    @GetMapping("/add")
    public String add() {
        return "apiary-add";
    }

    @PostMapping("/add")
    public String add(@Valid ApiaryAddDTO apiaryAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("apiaryAddDTO", apiaryAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.apiaryAddDTO", bindingResult);
            return "redirect:/apiaries/add";
        }

        ApiaryEntity apiaryEntity = new ApiaryEntity().
                setName(apiaryAddDTO.getName())
                .setDescription(apiaryAddDTO.getDescription())
                .setArea(apiaryAddDTO.getArea())
                .setImageUrl(apiaryAddDTO.getImageURL());

        Optional<AddressEntity> optionalAddress = addressService.findByCityAndCountryAndStreet(apiaryAddDTO.getCity(), apiaryAddDTO.getCountry(), apiaryAddDTO.getStreet());

        if (optionalAddress.isPresent()) {
            redirectAttributes.addFlashAttribute("apiaryAddDTO", apiaryAddDTO);
            redirectAttributes.addFlashAttribute("usedAddress", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.apiaryAddDTO", bindingResult);
            return "redirect:/apiaries/add";

        }
        AddressEntity address = new AddressEntity().
                setCity(apiaryAddDTO.getCity()).
                setCountry(apiaryAddDTO.getCountry()).
                setPostcode(apiaryAddDTO.getPostcode()).
                setStreet(apiaryAddDTO.getStreet());

        addressService.add(address);
        apiaryEntity.setAddress(address);


        UserEntity userEntity = userService.findByEmail(principal.getName());
        apiaryEntity.setBeekeeper(userEntity);

        apiaryService.save(apiaryEntity);
        return "redirect:/apiaries/all";

    }

}
