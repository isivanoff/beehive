package bg.beesoft.beehive.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/apiaries")
public class ApiariesController {
    @GetMapping("/all")
    public String apiaries() {
        return "apiaries";
    }

    @GetMapping("/add")
    public String add() {
        return "apiary-add";
    }

}
