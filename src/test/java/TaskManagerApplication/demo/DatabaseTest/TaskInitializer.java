package TaskManagerApplication.demo.DatabaseTest;

import TaskManagerApplication.demo.data.Task;

public class TaskInitializer {
    public static Task createTask(){
        Task task = new Task();
        task.setTitle("Test Task");
        task.setDescription("Test Description");
        task.setDueDate(null);
        task.setPriority('M');
        task.setStatus(false);
        return task;
    }

    public static Task createTask(String title, String description, char priority, boolean status){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(null);
        task.setPriority(priority);
        task.setStatus(status);
        return task;
    }
}
