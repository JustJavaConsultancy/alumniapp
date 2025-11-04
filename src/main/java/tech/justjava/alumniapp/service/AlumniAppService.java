package tech.justjava.alumniApp.service;

import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.justjava.alumniApp.entity.AlumniDocumentRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AlumniAppService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    public void deployProcess() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("processes/alumniApp.bpmn20.xml")
                .deploy();
    }

    public String startProcess(AlumniDocumentRequest request) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("documentType", request.getDocumentType());
        variables.put("alumniId", request.getAlumniId());
        variables.put("paymentMethod", request.getPaymentMethod());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("alumniApp", variables);
        return processInstance.getId();
    }

    public List<Task> getTasks(String alumniId) {
        return taskService.createTaskQuery()
                .taskAssignee(alumniId)
                .list();
    }

    public void completeTask(String taskId, Map<String, Object> variables) {
        taskService.complete(taskId, variables);
    }

    public List<ProcessInstance> getProcessInstances() {
        return runtimeService.createProcessInstanceQuery()
                .list();
    }
}
