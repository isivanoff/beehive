package bg.beesoft.beehive.web;

import bg.beesoft.beehive.model.dto.TaskAddDTO;
import bg.beesoft.beehive.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}/add")
    public String add(Model model, @PathVariable Long id){
        TaskAddDTO taskAddDTO = taskService.initializeTask(id);
        model.addAttribute("taskAddDTO",taskAddDTO);
        return "task-add";
    }

}
