package tech.justjava.alumni.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tech.justjava.alumni.service.AlumniAppService;

import java.util.Map;

@Controller
@RequestMapping("/alumni")
public class AlumniAppController {

    @Autowired
    private AlumniAppService alumniAppService;

    @GetMapping("/start")
    public String showStartForm(Model model) {
        model.addAttribute("documentTypes", alumniAppService.getDocumentTypes());
        model.addAttribute("paymentMethods", alumniAppService.getPaymentMethods());
        return "alumni/start-form";
    }

    @PostMapping("/start")
    public String startProcess(@RequestParam Map<String, String> formData, Model model) {
        String processInstanceId = alumniAppService.startProcess(formData);
        model.addAttribute("processInstanceId", processInstanceId);
        return "alumni/process-started";
    }

    @GetMapping("/tasks")
    public String showTasks(@RequestParam String userId, Model model) {
        model.addAttribute("tasks", alumniAppService.getTasksForUser(userId));
        return "alumni/tasks";
    }

    @GetMapping("/task/{taskId}")
    public String showTaskForm(@PathVariable String taskId, Model model) {
        model.addAttribute("task", alumniAppService.getTaskDetails(taskId));
        return "alumni/task-form";
    }

    @PostMapping("/task/{taskId}/complete")
    public String completeTask(@PathVariable String taskId, @RequestParam Map<String, String> formData) {
        alumniAppService.completeTask(taskId, formData);
        return "redirect:/alumni/tasks?userId=" + formData.get("userId");
    }

    @GetMapping("/processes")
    public String showProcesses(Model model) {
        model.addAttribute("processes", alumniAppService.getProcessInstances());
        return "alumni/processes";
    }
}
