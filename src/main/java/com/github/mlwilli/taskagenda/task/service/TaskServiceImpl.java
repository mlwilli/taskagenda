package com.github.mlwilli.taskagenda.task.service;

import com.github.mlwilli.taskagenda.task.domain.Task;
import com.github.mlwilli.taskagenda.task.domain.TaskStatus;
import com.github.mlwilli.taskagenda.task.dto.TaskCreateRequest;
import com.github.mlwilli.taskagenda.task.dto.TaskDto;
import com.github.mlwilli.taskagenda.task.dto.TaskUpdateRequest;
import com.github.mlwilli.taskagenda.task.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> listAll() {
        return taskRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskDto> findById(Long id) {
        return taskRepository.findById(id)
                .map(this::toDto);
    }

    @Override
    public TaskDto create(TaskCreateRequest request) {
        Task task = new Task(
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                request.getPriority(),
                request.getDueDate()
        );
        Task saved = taskRepository.save(task);
        return toDto(saved);
    }

    @Override
    public Optional<TaskDto> update(Long id, TaskUpdateRequest request) {
        return taskRepository.findById(id)
                .map(existing -> {
                    existing.setTitle(request.getTitle());
                    existing.setDescription(request.getDescription());
                    existing.setStatus(request.getStatus());
                    existing.setPriority(request.getPriority());
                    existing.setDueDate(request.getDueDate());
                    return toDto(existing);
                });
    }

    @Override
    public boolean delete(Long id) {
        if (!taskRepository.existsById(id)) {
            return false;
        }
        taskRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskDto> findByDueDateRange(LocalDate from, LocalDate to) {
        return taskRepository.findByDueDateBetween(from, to)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public Optional<TaskDto> markCompleted(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setStatus(TaskStatus.COMPLETED);
                    return toDto(task);
                });
    }

    private TaskDto toDto(Task task) {
        return new TaskDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getPriority(),
                task.getDueDate(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}
