package tech.justjava.alumniapp.controller;

import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.justjava.alumniapp.service.AlumniProcessService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AlumniProcessControllerTest {

    @Mock
    private AlumniProcessService alumniProcessService;

    @InjectMocks
    private AlumniProcessController alumniProcessController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alumniProcessController).build();
    }

    @Test
    public void testStartProcess() throws Exception {
        when(alumniProcessService.startProcess(anyString(), any())).thenReturn("processInstanceId");

        mockMvc.perform(post("/api/alumni/start")
                        .param("alumniId", "123")
                        .param("documentType", "transcript")
                        .param("paymentMethod", "remita"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTasks() throws Exception {
        Task task = new Task() {
            @Override
            public String getId() {
                return "taskId";
            }

            @Override
            public String getName() {
                return "Task Name";
            }

            // Implement other required methods
        };
        List<Task> tasks = Arrays.asList(task);
        when(alumniProcessService.getTasks(anyString())).thenReturn(tasks);

        mockMvc.perform(get("/api/alumni/tasks")
                        .param("alumniId", "123"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCompleteTask() throws Exception {
        mockMvc.perform(post("/api/alumni/complete-task")
                        .param("taskId", "taskId"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetProcessInstances() throws Exception {
        ProcessInstance processInstance = new ProcessInstance() {
            @Override
            public String getId() {
                return "processInstanceId";
            }

            // Implement other required methods
        };
        List<ProcessInstance> processInstances = Arrays.asList(processInstance);
        when(alumniProcessService.getProcessInstances(anyString())).thenReturn(processInstances);

        mockMvc.perform(get("/api/alumni/process-instances")
                        .param("alumniId", "123"))
                .andExpect(status().isOk());
    }
}
