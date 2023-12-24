package TaskManagerApplication.demo.Repositories;

import TaskManagerApplication.demo.Data.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

}
