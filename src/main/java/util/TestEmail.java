package util;

public class TestEmail {
    public static void main(String[] args) {
        // Địa chỉ email người nhận
        String to = "htviet4961@gmail.com"; // Thay bằng email người nhận
        String subject = "Test Email";
        String body = "Đây là email kiểm tra từ hệ thống Reminder System.";

        // Gửi email
        EmailUtil.sendEmail(to, subject, body);
    }
}