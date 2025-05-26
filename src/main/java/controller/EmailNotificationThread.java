package controller;

import dao.TaskDAO;
import dao.UserDAO;
import model.Task;
import util.EmailUtil;

import java.util.List;

public class EmailNotificationThread extends Thread {
    private TaskDAO taskDAO = new TaskDAO();
    private UserDAO userDAO = new UserDAO();

    @Override
    public void run() {
        while (true) {
            try {
                // Lấy danh sách các công việc sắp đến hạn trong vòng 30 phút
                List<Task> tasks = taskDAO.getTasksBeforeDeadline(30);
                for (Task task : tasks) {
                    String email = userDAO.getEmailByUserId(task.getUserId());
                    if (email != null && !email.isEmpty()) {
                        String subject = "Nhắc nhở: Công việc sắp đến hạn";
                        String body = "Công việc \"" + task.getTitle() + "\" sắp đến hạn vào lúc " + task.getDeadline() + ".\n\n"
                                + "Vui lòng kiểm tra và hoàn thành công việc đúng hạn.";

                        try {
                            // Gửi email
                            EmailUtil.sendEmail(email, subject, body);

                            // Đánh dấu công việc đã được thông báo
                            taskDAO.markAsNotified(task.getId());
                            System.out.println("Email đã được gửi đến: " + email);
                        } catch (Exception e) {
                            System.err.println("Lỗi khi gửi email đến: " + email);
                            e.printStackTrace();
                        }
                    } else {
                        System.err.println("Không tìm thấy email hợp lệ cho userId: " + task.getUserId());
                    }
                }

                // Chờ 1 phút trước khi kiểm tra lại
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                System.err.println("EmailNotificationThread bị gián đoạn.");
                e.printStackTrace();
                break; // Thoát khỏi vòng lặp nếu thread bị gián đoạn
            } catch (Exception e) {
                System.err.println("Lỗi không xác định trong EmailNotificationThread.");
                e.printStackTrace();
            }
        }
    }
}