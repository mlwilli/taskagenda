package com.github.mlwilli.taskagenda.task.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mlwilli.taskagenda.task.domain.TaskPriority;
import com.github.mlwilli.taskagenda.task.domain.TaskStatus;
import com.github.mlwilli.taskagenda.task.dto.TaskCreateRequest;
import com.github.mlwilli.taskagenda.task.dto.TaskDto;
import com.github.mlwilli.taskagenda.task.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/tasks should return list of tasks")
    void listTasks() throws Exception {
        TaskDto dto = new TaskDto(
                1L,
                "Demo task",
                "Demo description",
                TaskStatus.OPEN,
                TaskPriority.MEDIUM,
                LocalDate.now().plusDays(1),
                Instant.now(),
                Instant.now()
        );

        Mockito.when(taskService.listAll())
                .thenReturn(List.of(dto));

        mockMvc.perform(get("/api/tasks"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].id").value(1L))
               .andExpect(jsonPath("$[0].title").value("Demo task"));
    }

    @Test
    @DisplayName("POST /api/tasks should create a new task")
    void createTask() throws Exception {
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("New task");
        request.setDescription("New description");
        request.setStatus(TaskStatus.OPEN);
        request.setPriority(TaskPriority.HIGH);
        request.setDueDate(LocalDate.now().plusDays(3));

        TaskDto dto = new TaskDto(
                10L,
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getPriority(),
                request.getDueDate(),
                Instant.now(),
                Instant.now()
        );

        Mockito.when(taskService.create(any(TaskCreateRequest.class))).thenReturn(dto);

        mockMvc.perform(post("/api/tasks")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.id").value(10L))
               .andExpect(jsonPath("$.title").value("New task"));
    }

    @Test
    @DisplayName("GET /api/tasks/{id} should return 404 when not found")
    void getTaskNotFound() throws Exception {
        Mockito.when(taskService.findById(eq(999L))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/tasks/999"))
               .andExpect(status().isNotFound());
    }
}
