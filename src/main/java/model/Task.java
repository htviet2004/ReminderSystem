package model;

public class Task {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String deadline;
    private boolean notified;

    // Constructor mặc định
    public Task() {
    }

    // Constructor đầy đủ
    public Task(int id, int userId, String title, String description, String deadline, boolean notified) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.notified = notified;
    }

     public Task(int userId, String title, String description, String deadline) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.notified = false; // Mặc định là chưa thông báo
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }
}