package com.dan13l.taskapi.repository;

import com.dan13l.taskapi.entity.Task;
import com.dan13l.taskapi.enums.TaskStatus;
import com.dan13l.taskapi.enums.TaskPriority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByStatus(TaskStatus status);

    List<Task> findByPriority(TaskPriority priority);

    List<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority);

    @Query("SELECT t FROM Task t WHERE " +
            "LOWER(t.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Task> findByTitleOrDescriptionContaining(@Param("searchTerm") String searchTerm);

    List<Task> findByDueDateBefore(LocalDateTime date);

    List<Task> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    long countByStatus(TaskStatus status);

    @Query("SELECT t FROM Task t ORDER BY t.priority DESC, t.createdAt ASC")
    List<Task> findAllOrderedByPriorityAndDate();

}
