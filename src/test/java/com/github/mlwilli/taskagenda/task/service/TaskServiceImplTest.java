package com.github.mlwilli.taskagenda.task.service;

import com.github.mlwilli.taskagenda.task.domain.Task;
import com.github.mlwilli.taskagenda.task.domain.TaskPriority;
import com.github.mlwilli.taskagenda.task.domain.TaskStatus;
import com.github.mlwilli.taskagenda.task.dto.TaskCreateRequest;
import com.github.mlwilli.taskagenda.task.repository.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    @DisplayName("create should map request to entity, save it, and return a DTO")
    void createTask() throws Exception {
        // arrange
        TaskCreateRequest request = new TaskCreateRequest();
        request.setTitle("Write unit tests");
        request.setDescription("Cover service layer with unit tests");
        request.setStatus(TaskStatus.OPEN);
        request.setPriority(TaskPriority.HIGH);
        request.setDueDate(LocalDate.now().plusDays(2));

        Task persisted = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getPriority(),
                request.getDueDate()
        );

        // manually set id via reflection to simulate JPA-generated id
        Field idField = Task.class.getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(persisted, 1L);

        when(taskRepository.save(any(Task.class))).thenReturn(persisted);

        // act
        var result = taskService.create(request);

        // assert
        verify(taskRepository).save(any(Task.class));
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Write unit tests");
        assertThat(result.getPriority()).isEqualTo(TaskPriority.HIGH);
        assertThat(result.getStatus()).isEqualTo(TaskStatus.OPEN);
    }
}
