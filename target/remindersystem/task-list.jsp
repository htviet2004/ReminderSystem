<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page import="dao.TaskDAO" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task List</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
        }
        h2 {
            color: #333;
        }
        form {
            margin-bottom: 20px;
        }
        input[type="text"], input[type="datetime-local"], textarea {
            width: 100%;
            padding: 10px;
            margin: 5px 0 15px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #4CAF50;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <h2>Danh sách công việc</h2>

    <!-- Form thêm công việc -->
    <form action="TaskServlet" method="post">
        <label for="title">Tiêu đề:</label><br>
        <input type="text" id="title" name="title" required><br><br>

        <label for="description">Mô tả:</label><br>
        <textarea id="description" name="description" required></textarea><br><br>

        <label for="deadline">Hạn chót:</label><br>
        <input type="datetime-local" id="deadline" name="deadline" required><br><br>

        <input type="submit" value="Thêm công việc">
    </form>

    <% String error = request.getParameter("error"); %>
<% if ("1".equals(error)) { %>
    <p style="color: red;">Thêm công việc thất bại! Hạn chót không hợp lệ hoặc có lỗi xảy ra.</p>
<% } %>

    <hr>

    <!-- Hiển thị danh sách công việc -->
    <h3>Công việc hiện tại:</h3>
    <table border="1">
        <tr>
            <th>Tiêu đề</th>
            <th>Mô tả</th>
            <th>Hạn chót</th>
            <th>Đã thông báo</th>
        </tr>
<%
    // Lấy userId từ session ngầm định
    Integer userId = (Integer) session.getAttribute("userId"); // Sử dụng session ngầm định

    if (userId != null && userId > 0) {
        TaskDAO taskDAO = new TaskDAO();
        List<Task> tasks = taskDAO.getTasksByUser(userId); // Sử dụng userId từ session
        for (Task task : tasks) {
%>
<tr>
    <td><%= task.getTitle() %></td>
    <td><%= task.getDescription() %></td>
    <td><%= task.getDeadline() %></td>
    <td><%= task.isNotified() ? "Có" : "Không" %></td>
</tr>
<%
        }
    } else {
%>
<tr>
    <td colspan="4">Người dùng chưa đăng nhập hoặc không có công việc nào.</td>
</tr>
<%
    }
%>
    </table>
</body>
</html>