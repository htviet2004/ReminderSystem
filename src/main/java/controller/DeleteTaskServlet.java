package controller;

import dao.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteTaskServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));
        TaskDAO dao = new TaskDAO();
        dao.deleteTask(taskId); // hàm này bạn phải thêm ở TaskDAO
        response.sendRedirect("task-list.jsp");
    }
}
