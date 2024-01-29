package hibernate.dao.impl;

import hibernate.dao.TaskDao;
import hibernate.model.Task;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TaskDaoJdbcImpl implements TaskDao {
    private static final String DRIVER = "org.postgresql.Driver";

    public TaskDaoJdbcImpl() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTask(Task task) {
        String sql = "INSERT INTO tasks (name, owner, priority) VALUES(?, ?, ?)";
        try (Connection connection = getConnection()){
            connection.setAutoCommit(false);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task.name());
            statement.setString(2, task.owner());
            statement.setInt(3, task.priority());
            statement.executeQuery();
            connection.commit();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Task> getAll() {
        String sql = "SELECT * FROM tasks";
        List<Task> list = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql))
        {
            connection.setAutoCommit(false);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String owner = rs.getString("owner");
                int priority = rs.getInt("priority");
                Task task = new Task(id, name, owner, priority);
                list.add(task);
            }
            connection.commit();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void deleteTask(Task task) {
        String sql = "DELETE FROM tasks";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement())
        {
            connection.setAutoCommit(false);
            statement.executeQuery(sql);
            connection.commit();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws IOException, SQLException {
        Properties properties = new Properties();
        try (InputStream is = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(is);
        }
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        return DriverManager.getConnection(url, username, password);
    }
}
