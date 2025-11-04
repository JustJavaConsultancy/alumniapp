package tech.justjava.alumni.service;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AlumniAppService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public Deployment deployProcess() {
        return repositoryService.createDeployment()
                .addClasspathResource("processes/alumniApp.bpmn20.xml")
                .name("Alumni Document Request Process")
                .deploy();
    }

    public String startProcess(Map<String, String> formData) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("documentType", formData.get("documentType"));
        variables.put("alumniId", formData.get("alumniId"));
        variables.put("paymentMethod", formData.get("paymentMethod"));

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("alumniApp", variables);
        return processInstance.getId();
    }

    public List<Task> getTasksForUser(String userId) {
        return taskService.createTaskQuery()
                .taskAssignee(userId)
                .list();
    }

    public Map<String, Object> getTaskDetails(String taskId) {
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .singleResult();

        Map<String, Object> taskDetails = new HashMap<>();
        taskDetails.put("task", task);
        taskDetails.put("variables", taskService.getVariables(taskId));

        return taskDetails;
    }

    @Transactional
    public void completeTask(String taskId, Map<String, String> formData) {
        Map<String, Object> variables = new HashMap<>(formData);
        taskService.complete(taskId, variables);
    }

    public List<Map<String, Object>> getProcessInstances() {
        return runtimeService.createProcessInstanceQuery()
                .list()
                .stream()
                .map(processInstance -> {
                    Map<String, Object> instanceInfo = new HashMap<>();
                    instanceInfo.put("id", processInstance.getId());
                    instanceInfo.put("processDefinitionId", processInstance.getProcessDefinitionId());
                    instanceInfo.put("startTime", processInstance.getStartTime());
                    instanceInfo.put("variables", runtimeService.getVariables(processInstance.getId()));
                    return instanceInfo;
                })
                .collect(Collectors.toList());
    }

    public List<String> getDocumentTypes() {
        return List.of("transcript", "certificate", "letterOfAttestation");
    }

    public List<String> getPaymentMethods() {
        return List.of("remita", "paystack", "interswitch");
    }
}
