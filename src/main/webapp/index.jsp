<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Reminder System</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <h2>Đăng nhập hệ thống</h2>
    <form action="LoginServlet" method="post">
        <label>Username:</label><br>
        <input type="text" name="username" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <input type="submit" value="Đăng nhập">
    </form>

    <% if(request.getAttribute("errorMsg") != null) { %>
        <p style="color:red;"><%= request.getAttribute("errorMsg") %></p>
    <% } %>

    <!-- Nút Đăng ký -->
    <p>Bạn chưa có tài khoản? <a href="register.jsp">Đăng ký ngay</a></p>
</body>
</html>