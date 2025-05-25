package controller;

import dao.TaskDAO;
import model.Task;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EditTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String taskIdParam = request.getParameter("taskId");
        if (taskIdParam != null) {
            try {
                int taskId = Integer.parseInt(taskIdParam);
                TaskDAO taskDAO = new TaskDAO();
                Task task = taskDAO.getTaskById(taskId); // Lấy task từ database
                if (task != null) {
                    request.setAttribute("task", task); // Truyền task sang JSP
                    request.getRequestDispatcher("edit-task.jsp").forward(request, response);
                } else {
                    response.sendRedirect("task-list.jsp?error=notfound");
                }
            } catch (NumberFormatException e) {
                response.sendRedirect("task-list.jsp?error=invalidid");
            }
        } else {
            response.sendRedirect("task-list.jsp?error=missingid");
        }
    }
}