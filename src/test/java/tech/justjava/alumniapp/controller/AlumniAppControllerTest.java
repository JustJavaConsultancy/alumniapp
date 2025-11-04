package tech.justjava.alumniApp.controller;

import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tech.justjava.alumniApp.entity.AlumniDocumentRequest;
import tech.justjava.alumniApp.service.AlumniAppService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AlumniAppControllerTest {

    @Mock
    private AlumniAppService alumniAppService;

    @InjectMocks
    private AlumniAppController alumniAppController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeployProcess() {
        doNothing().when(alumniAppService).deployProcess();

        ResponseEntity<String> response = alumniAppController.deployProcess();

        assertEquals("Process deployed successfully", response.getBody());
        verify(alumniAppService, times(1)).deployProcess();
    }

    @Test
    public void testStartProcess() {
        AlumniDocumentRequest request = new AlumniDocumentRequest();
        request.setDocumentType("transcript");
        request.setAlumniId("alumni123");
        request.setPaymentMethod("remita");

        when(alumniAppService.startProcess(request)).thenReturn("process123");

        ResponseEntity<String> response = alumniAppController.startProcess(request);

        assertEquals("Process started with ID: process123", response.getBody());
        verify(alumniAppService, times(1)).startProcess(request);
    }

    @Test
    public void testGetTasks() {
        Task task1 = mock(Task.class);
        Task task2 = mock(Task.class);
        List<Task> tasks = Arrays.asList(task1, task2);

        when(alumniAppService.getTasks("alumni123")).thenReturn(tasks);

        ResponseEntity<List<Task>> response = alumniAppController.getTasks("alumni123");

        assertEquals(tasks, response.getBody());
        verify(alumniAppService, times(1)).getTasks("alumni123");
    }

    @Test
    public void testCompleteTask() {
        doNothing().when(alumniAppService).completeTask("task123", null);

        ResponseEntity<String> response = alumniAppController.completeTask("task123", null);

        assertEquals("Task completed successfully", response.getBody());
        verify(alumniAppService, times(1)).completeTask("task123", null);
    }

    @Test
    public void testGetProcessInstances() {
        ProcessInstance processInstance1 = mock(ProcessInstance.class);
        ProcessInstance processInstance2 = mock(ProcessInstance.class);
        List<ProcessInstance> processInstances = Arrays.asList(processInstance1, processInstance2);

        when(alumniAppService.getProcessInstances()).thenReturn(processInstances);

        ResponseEntity<List<ProcessInstance>> response = alumniAppController.getProcessInstances();

        assertEquals(processInstances, response.getBody());
        verify(alumniAppService, times(1)).getProcessInstances();
    }
}
