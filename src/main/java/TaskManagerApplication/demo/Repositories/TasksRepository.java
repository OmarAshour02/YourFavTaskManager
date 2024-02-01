package TaskManagerApplication.demo.Repositories;

import TaskManagerApplication.demo.Data.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(boolean status);

    List<Task> findByPriority(char priority);
}