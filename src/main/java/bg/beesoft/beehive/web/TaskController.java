package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}/add")
    public String addTask(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("taskAddDTO", taskService.initializeTask(id, userDetails));
        return "task-add";
    }

    @GetMapping("/{id}/add/error")
    public String add(Model model, @Valid TaskAddDTO taskAddDTO, BindingResult bindingResult) {
        model.addAttribute("taskAddDTO", taskAddDTO);
        return "task-add";
    }

    @PostMapping("/{id}/add")
    public String add(@Valid TaskAddDTO taskAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @PathVariable Long id,
                      @AuthenticationPrincipal UserDetails userDetails) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("taskAddDTO", taskAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskAddDTO", bindingResult);
            return "redirect:/tasks/" + id + "/add/error";
        }

        taskService.addTask(taskAddDTO, id, userDetails);

        return "redirect:/beehives/view/" + id;
    }

}
