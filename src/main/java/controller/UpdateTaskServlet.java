package controller;
import dao.TaskDAO;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class UpdateTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String deadline = request.getParameter("deadline").replace("T", " ");

        Task task = new Task(id, userId, title, description, deadline, false);
        TaskDAO dao = new TaskDAO();
        dao.updateTask(task); // thêm hàm này
        response.sendRedirect("task-list.jsp");
    }
}