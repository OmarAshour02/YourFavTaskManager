package TaskManagerApplication.demo.repositories;

import TaskManagerApplication.demo.data.Task;
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

    Page<Task> findAll(Pageable pageable);

    Optional<Task> findById(Long id);

}
