package tech.justjava.alumni.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.justjava.alumni.service.AlumniAppService;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AlumniAppControllerTest {

    @Mock
    private AlumniAppService alumniAppService;

    @InjectMocks
    private AlumniAppController alumniAppController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(alumniAppController).build();
    }

    @Test
    public void testShowStartForm() throws Exception {
        mockMvc.perform(get("/alumni/start"))
                .andExpect(status().isOk())
                .andExpect(view().name("alumni/start-form"))
                .andExpect(model().attributeExists("documentTypes", "paymentMethods"));
    }

    @Test
    public void testStartProcess() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("documentType", "transcript");
        formData.put("alumniId", "ALU001");
        formData.put("paymentMethod", "paystack");

        when(alumniAppService.startProcess(formData)).thenReturn("process123");

        mockMvc.perform(post("/alumni/start")
                        .param("documentType", "transcript")
                        .param("alumniId", "ALU001")
                        .param("paymentMethod", "paystack"))
                .andExpect(status().isOk())
                .andExpect(view().name("alumni/process-started"))
                .andExpect(model().attribute("processInstanceId", "process123"));

        verify(alumniAppService, times(1)).startProcess(formData);
    }

    @Test
    public void testShowTasks() throws Exception {
        mockMvc.perform(get("/alumni/tasks")
                        .param("userId", "user123"))
                .andExpect(status().isOk())
                .andExpect(view().name("alumni/tasks"))
                .andExpect(model().attributeExists("tasks"));

        verify(alumniAppService, times(1)).getTasksForUser("user123");
    }

    @Test
    public void testShowTaskForm() throws Exception {
        mockMvc.perform(get("/alumni/task/task123"))
                .andExpect(status().isOk())
                .andExpect(view().name("alumni/task-form"))
                .andExpect(model().attributeExists("task"));

        verify(alumniAppService, times(1)).getTaskDetails("task123");
    }

    @Test
    public void testCompleteTask() throws Exception {
        Map<String, String> formData = new HashMap<>();
        formData.put("userId", "user123");
        formData.put("paymentStatus", "successful");

        mockMvc.perform(post("/alumni/task/task123/complete")
                        .param("userId", "user123")
                        .param("paymentStatus", "successful"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alumni/tasks?userId=user123"));

        verify(alumniAppService, times(1)).completeTask("task123", formData);
    }

    @Test
    public void testShowProcesses() throws Exception {
        mockMvc.perform(get("/alumni/processes"))
                .andExpect(status().isOk())
                .andExpect(view().name("alumni/processes"))
                .andExpect(model().attributeExists("processes"));

        verify(alumniAppService, times(1)).getProcessInstances();
    }
}
