package TaskManagerApplication.demo.repositories;

import TaskManagerApplication.demo.data.Task;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
    // Find by status and user id

    List<Task> findByUserIdAndStatus(Long userId, boolean status, Pageable pageable);

    List<Task> findByUserIdAndPriority(Long userId, char priority, Pageable pageable);

    List<Task> findByUserId(Long userId, Pageable pageable);

    @NotNull
    Page<Task> findAll(@NotNull Pageable pageable);

    @NotNull
    Optional<Task> findById(@NotNull Long id);

}
