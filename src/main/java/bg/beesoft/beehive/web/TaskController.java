package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.service.TaskService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @ModelAttribute("taskAddDTO")
    public TaskAddDTO initModel(@PathVariable Long id){
        TaskAddDTO taskAddDTO = taskService.initializeTask(id);
        return taskAddDTO;
    }

    @GetMapping("/{id}/add")
    public String addTask(@PathVariable Long id){
        return "task-add";
    }

    @GetMapping("/{id}/add/error")
    public String add(@PathVariable Long id) {
        return "task-add";
    }

    @PostMapping("/{id}/add")
    public String add(@Valid TaskAddDTO taskAddDTO,
                      BindingResult bindingResult,
                      RedirectAttributes redirectAttributes,
                      @PathVariable Long id,
                      @AuthenticationPrincipal UserDetails userDetails){

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("taskAddDTO",taskAddDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taskAddDTO", bindingResult);
            return "redirect:/tasks/" + id + "/add/error";
        }

        taskService.addTask(taskAddDTO,id,userDetails);

        return "redirect:/beehives/view/" + id;
    }

}
