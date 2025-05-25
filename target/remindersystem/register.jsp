<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đăng ký - Reminder System</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <h2>Đăng ký tài khoản</h2>
    <form action="RegisterServlet" method="post">
        <label>Username:</label><br>
        <input type="text" name="username" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <input type="submit" value="Đăng ký">
    </form>

    <% if (request.getAttribute("errorMsg") != null) { %>
        <p style="color:red;"><%= request.getAttribute("errorMsg") %></p>
    <% } %>
</body>
</html>