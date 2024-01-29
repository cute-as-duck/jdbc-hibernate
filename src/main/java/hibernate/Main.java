package hibernate;

import hibernate.model.TaskClass;
import hibernate.utils.TaskProcess;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TaskProcess tp = new TaskProcess();
        List<TaskClass> list = tp.getAll();
    }
}
