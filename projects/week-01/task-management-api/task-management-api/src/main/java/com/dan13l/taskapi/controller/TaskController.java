package com.dan13l.taskapi.controller;

import com.dan13l.taskapi.entity.Task;
import com.dan13l.taskapi.enums.TaskStatus;
import com.dan13l.taskapi.enums.TaskPriority;
import com.dan13l.taskapi.repository.TaskRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/tasks")
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());

        Task savedTask = taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
            @Valid @RequestBody Task taskDetails) {
        Optional<Task> optionalTask = taskRepository.findById(id);

        if (optionalTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task existingTask = optionalTask.get();

        existingTask.setTitle(taskDetails.getTitle());
        existingTask.setDescription(taskDetails.getDescription());
        existingTask.setStatus(taskDetails.getStatus());
        existingTask.setPriority(taskDetails.getPriority());
        existingTask.setDueDate(taskDetails.getDueDate());
        existingTask.setUpdatedAt(LocalDateTime.now());

        Task updatedTask = taskRepository.save(existingTask);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        if (!taskRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        taskRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable TaskPriority priority) {
        List<Task> tasks = taskRepository.findByPriority(priority);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Task>> searchTasks(@RequestParam String query) {
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Task> tasks = taskRepository.findByTitleOrDescriptionContaining(query.trim());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/stats")
    public ResponseEntity<TaskStats> getTaskStats() {
        TaskStats stats = new TaskStats();
        stats.setPendingCount(taskRepository.countByStatus(TaskStatus.PENDING));
        stats.setInProgressCount(taskRepository.countByStatus(TaskStatus.IN_PROGRESS));
        stats.setCompletedCount(taskRepository.countByStatus(TaskStatus.COMPLETED));
        stats.setCancelledCount(taskRepository.countByStatus(TaskStatus.CANCELLED));
        stats.setTotalCount(taskRepository.count());

        return ResponseEntity.ok(stats);
    }

    public static class TaskStats {
        private long totalCount;
        private long pendingCount;
        private long inProgressCount;
        private long completedCount;
        private long cancelledCount;

        public long getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(long totalCount) {
            this.totalCount = totalCount;
        }

        public long getPendingCount() {
            return pendingCount;
        }

        public void setPendingCount(long pendingCount) {
            this.pendingCount = pendingCount;
        }

        public long getInProgressCount() {
            return inProgressCount;
        }

        public void setInProgressCount(long inProgressCount) {
            this.inProgressCount = inProgressCount;
        }

        public long getCompletedCount() {
            return completedCount;
        }

        public void setCompletedCount(long completedCount) {
            this.completedCount = completedCount;
        }

        public long getCancelledCount() {
            return cancelledCount;
        }

        public void setCancelledCount(long cancelledCount) {
            this.cancelledCount = cancelledCount;
        }
    }
}
