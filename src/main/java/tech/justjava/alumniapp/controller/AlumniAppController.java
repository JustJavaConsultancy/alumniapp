package tech.justjava.alumniApp.controller;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.justjava.alumniApp.entity.AlumniDocumentRequest;
import tech.justjava.alumniApp.service.AlumniAppService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumniApp")
public class AlumniAppController {

    @Autowired
    private AlumniAppService alumniAppService;

    @PostMapping("/deploy")
    public ResponseEntity<String> deployProcess() {
        alumniAppService.deployProcess();
        return ResponseEntity.ok("Process deployed successfully");
    }

    @PostMapping("/start")
    public ResponseEntity<String> startProcess(@RequestBody AlumniDocumentRequest request) {
        String processInstanceId = alumniAppService.startProcess(request);
        return ResponseEntity.ok("Process started with ID: " + processInstanceId);
    }

    @GetMapping("/tasks/{alumniId}")
    public ResponseEntity<List<Task>> getTasks(@PathVariable String alumniId) {
        List<Task> tasks = alumniAppService.getTasks(alumniId);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/complete/{taskId}")
    public ResponseEntity<String> completeTask(@PathVariable String taskId, @RequestBody Map<String, Object> variables) {
        alumniAppService.completeTask(taskId, variables);
        return ResponseEntity.ok("Task completed successfully");
    }

    @GetMapping("/processes")
    public ResponseEntity<List<ProcessInstance>> getProcessInstances() {
        List<ProcessInstance> processInstances = alumniAppService.getProcessInstances();
        return ResponseEntity.ok(processInstances);
    }
}
