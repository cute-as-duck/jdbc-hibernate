package hibernate.dao;

import hibernate.model.Task;

import java.util.List;

public interface TaskDao {
    void addTask(Task task);
    List<Task> getAll();
    void deleteTask(Task task);
}
