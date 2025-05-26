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
    String taskIdParam = request.getParameter("taskId");
    Task task = null;

    if (taskIdParam != null) {
        try {
            int taskId = Integer.parseInt(taskIdParam);
            TaskDAO taskDAO = new TaskDAO();
            task = taskDAO.getTaskById(taskId);

            if (task == null) {
    %>
                <p style="color:red;">Không tìm thấy công việc với ID: <%= taskId %>.</p>
                <p><a href="task-list.jsp">Quay lại danh sách công việc</a></p>
    <%
            }
        } catch (NumberFormatException e) {
    %>
            <p style="color:red;">ID công việc không hợp lệ.</p>
            <p><a href="task-list.jsp">Quay lại danh sách công việc</a></p>
    <%
        }
    } else {
    %>
        <p style="color:red;">Không tìm thấy ID công việc.</p>
        <p><a href="task-list.jsp">Quay lại danh sách công việc</a></p>
    <%
    }

    if (task != null) {
        String deadlineFormatted = task.getDeadline(); // Giữ nguyên định dạng yyyy-MM-dd'T'HH:mm
    %>

    <!-- Form sửa task nếu có -->
    <form action="TaskServlet" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="taskId" value="<%= task.getId() %>">

        <label for="title">Tiêu đề:</label><br>
        <input type="text" id="title" name="title" value="<%= task.getTitle() %>" required><br><br>

        <label for="description">Mô tả:</label><br>
        <textarea id="description" name="description" required><%= task.getDescription() %></textarea><br><br>

        <label for="deadline">Hạn chót:</label><br>
        <input type="datetime-local" id="deadline" name="deadline" value="<%= deadlineFormatted %>" required><br><br>

        <input type="submit" value="Cập nhật">
    </form>

    <%
    }
    %>
    <p><a href="task-list.jsp">Quay lại danh sách công việc</a></p>

    <%-- Hiển thị thông báo nếu có --%>
    <% String message = request.getParameter("message"); %>
    <% if (message != null) { %>
        <p style="color:green;"><%= message %></p>
    <% } %>
</body>
</html>