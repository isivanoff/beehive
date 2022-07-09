package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.ApiaryAddDTO;
import bg.beesoft.beehive.model.dto.BeehiveAddDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/beehives")
public class BeehiveController {

    @ModelAttribute("beehiveAddDTO")
    public BeehiveAddDTO initModel() {
        return new BeehiveAddDTO();
    }

    @GetMapping("/add")
    public String add() {
        return "beehive-add";
    }

}
