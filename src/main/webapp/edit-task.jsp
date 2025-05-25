<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.Task" %>
<%@ page import="dao.TaskDAO" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sửa Công Việc</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <h2>Sửa Công Việc</h2>
    <%
        int taskId = Integer.parseInt(request.getParameter("taskId"));
        TaskDAO taskDAO = new TaskDAO();
        Task task = taskDAO.getTaskById(taskId);
    %>
    <form action="TaskServlet" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="taskId" value="<%= task.getId() %>">

        <label for="title">Tiêu đề:</label><br>
        <input type="text" id="title" name="title" value="<%= task.getTitle() %>" required><br><br>

        <label for="description">Mô tả:</label><br>
        <textarea id="description" name="description" required><%= task.getDescription() %></textarea><br><br>

        <label for="deadline">Hạn chót:</label><br>
        <input type="datetime-local" id="deadline" name="deadline" value="<%= task.getDeadline() %>" required><br><br>

        <input type="submit" value="Cập nhật">
    </form>
</body>
</html>