package model;

public class Task {
    private int id;
    private int userId;
    private String title;
    private String description;
    private String deadline;
    private boolean isNotified;

    public Task(int id, int userId, String title, String description, String deadline, boolean isNotified) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isNotified = isNotified;
    }

    public Task(int userId, String title, String description, String deadline) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.isNotified = false;
    }

    // Các getter
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDeadline() {
        return deadline;
    }

    public boolean isNotified() {
        return isNotified;
    }

    // Nếu cần, thêm setter ở đây
}
