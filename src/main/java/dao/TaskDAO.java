package dao;

import model.Task;
import util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskDAO {

    public boolean insertTask(Task task) {
    String sql = "INSERT INTO tasks (user_id, title, description, deadline) VALUES (?, ?, ?, ?)";
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Lưu deadline dưới dạng chuỗi (String)
        ps.setInt(1, task.getUserId());
        ps.setString(2, task.getTitle());
        ps.setString(3, task.getDescription());
        ps.setString(4, task.getDeadline()); // Lưu deadline dưới dạng chuỗi

        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public List<Task> getTasksByUser(int userId) {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task task = new Task();
                task.setId(rs.getInt("id"));
                task.setUserId(rs.getInt("user_id"));
                task.setTitle(rs.getString("title"));
                task.setDescription(rs.getString("description"));
                task.setDeadline(rs.getString("deadline")); // Lấy deadline dưới dạng chuỗi
                task.setNotified(rs.getBoolean("is_notified"));
                list.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Hàm dùng cho thread để lấy task gần tới hạn
    public List<Task> getTasksNearingDeadline() {
        List<Task> list = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE is_notified = 0 AND deadline <= datetime('now', '+5 minutes')";

        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("deadline"),
                        rs.getBoolean("is_notified"));
                list.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markAsNotified(int taskId) {
        String sql = "UPDATE tasks SET is_notified = 1 WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, taskId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean updateTask(Task task) {
    String sql = "UPDATE tasks SET title = ?, description = ?, deadline = ? WHERE id = ?";
    try (Connection conn = DBUtil.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        // Chuyển đổi định dạng deadline từ yyyy-MM-dd'T'HH:mm sang TIMESTAMP
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // input từ HTML
        LocalDateTime deadlineLocal = LocalDateTime.parse(task.getDeadline(), formatter);
        Timestamp deadlineTime = Timestamp.valueOf(deadlineLocal);

        ps.setString(1, task.getTitle());
        ps.setString(2, task.getDescription());
        ps.setTimestamp(3, deadlineTime); // Lưu deadline đã định dạng
        ps.setInt(4, task.getId());

        return ps.executeUpdate() > 0;
    } catch (SQLException | IllegalArgumentException e) {
        e.printStackTrace();
        return false;
    }
}

    public Task getTaskById(int taskId) {
        Task task = null;
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, taskId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            task = new Task();
            task.setId(rs.getInt("id"));
            task.setUserId(rs.getInt("user_id"));
            task.setTitle(rs.getString("title"));
            task.setDescription(rs.getString("description"));
            task.setDeadline(rs.getString("deadline")); // Lấy deadline dưới dạng chuỗi
            task.setNotified(rs.getBoolean("is_notified"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return task;
}
}

