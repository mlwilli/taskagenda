package com.github.mlwilli.taskagenda.task.service;

import com.github.mlwilli.taskagenda.task.dto.TaskCreateRequest;
import com.github.mlwilli.taskagenda.task.dto.TaskDto;
import com.github.mlwilli.taskagenda.task.dto.TaskUpdateRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<TaskDto> listAll();

    Optional<TaskDto> findById(Long id);

    TaskDto create(TaskCreateRequest request);

    Optional<TaskDto> update(Long id, TaskUpdateRequest request);

    boolean delete(Long id);

    List<TaskDto> findByDueDateRange(LocalDate from, LocalDate to);

    Optional<TaskDto> markCompleted(Long id);
}
