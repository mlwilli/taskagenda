package com.github.mlwilli.taskagenda.task.repository;

import com.github.mlwilli.taskagenda.task.domain.Task;
import com.github.mlwilli.taskagenda.task.domain.TaskPriority;
import com.github.mlwilli.taskagenda.task.domain.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByDueDateBetween(LocalDate from, LocalDate to);
}
