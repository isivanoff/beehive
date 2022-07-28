package bg.beesoft.beehive.web.rest;

import bg.beesoft.beehive.model.view.TaskFullView;
import bg.beesoft.beehive.model.view.TaskView;
import bg.beesoft.beehive.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskRestController {

    private TaskService taskService;

    public TaskRestController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{beehiveId}/tasks")
    public ResponseEntity<List<TaskView>> getTasks(@PathVariable("beehiveId") Long beehiveId) {
        return ResponseEntity.ok(taskService.findAllByBeehiveId(beehiveId));
    }

    @GetMapping("/tasks/view/{taskId}")
    public ResponseEntity<TaskFullView> viewTasks(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.findViewById(taskId));
    }

    @DeleteMapping("/tasks/delete/{taskId}")
    public ResponseEntity<Long> deleteTask(@PathVariable Long taskId) {
        boolean isRemoved = taskService.deleteById(taskId);

        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taskId, HttpStatus.OK);

    }

}
