package tech.justjava.alumniapp.controller;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.justjava.alumniapp.service.AlumniProcessService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumni")
public class AlumniProcessController {

    @Autowired
    private AlumniProcessService alumniProcessService;

    @PostMapping("/start")
    public String startProcess(@RequestParam String alumniId,
                              @RequestParam String documentType,
                              @RequestParam String paymentMethod) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("alumniId", alumniId);
        variables.put("documentType", documentType);
        variables.put("paymentMethod", paymentMethod);

        return alumniProcessService.startProcess("alumniApp", variables);
    }

    @GetMapping("/tasks")
    public List<Task> getTasks(@RequestParam String alumniId) {
        return alumniProcessService.getTasks(alumniId);
    }

    @PostMapping("/complete-task")
    public void completeTask(@RequestParam String taskId,
                            @RequestParam(required = false) String approvalStatus,
                            @RequestParam(required = false) String paymentAmount) {
        Map<String, Object> variables = new HashMap<>();
        if (approvalStatus != null) {
            variables.put("approvalStatus", approvalStatus);
        }
        if (paymentAmount != null) {
            variables.put("paymentAmount", paymentAmount);
        }
        alumniProcessService.completeTask(taskId, variables);
    }

    @GetMapping("/process-instances")
    public List<ProcessInstance> getProcessInstances(@RequestParam String alumniId) {
        return alumniProcessService.getProcessInstances(alumniId);
    }
}
