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

            // Chuyển đổi định dạng deadline
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"); // input từ HTML
            LocalDateTime deadlineLocal = LocalDateTime.parse(task.getDeadline(), formatter);
            Timestamp deadlineTime = Timestamp.valueOf(deadlineLocal);
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());

            // Kiểm tra logic thời gian
            if (deadlineTime.before(currentTime)) {
                System.out.println("Deadline không hợp lệ: nhỏ hơn thời gian hiện tại.");
                return false; // Không thêm task nếu deadline không hợp lệ
            }

            ps.setInt(1, task.getUserId());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getDescription());
            ps.setTimestamp(4, deadlineTime); // Lưu deadline đã định dạng
            return ps.executeUpdate() > 0;
        } catch (SQLException | IllegalArgumentException e) {
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
}
