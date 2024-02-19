package TaskManagerApplication.demo.initializer;

import TaskManagerApplication.demo.data.Task;

public class TaskInitializer {
    public static Task createTask(Task task){
        Task task1 = new Task();
        task1.setId(task.getId());
        task1.setTitle(task.getTitle());
        task1.setDescription(task.getDescription());
        task1.setDueDate(task.getDueDate());
        task1.setPriority(task.getPriority());
        task1.setStatus(task.isStatus());
        return task1;

    }
    public static Task createTask(){
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(null);
        task.setPriority('M');
        task.setStatus(false);
        return task;
    }

}
