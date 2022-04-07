package pl.edu.pwr.lab2.i269691;

import java.time.LocalDate;

public class Task {
    private static int last_id = -1;
    private int id;
    private boolean status;
    private String title;
    private String description;
    private String DueDate;
    private String type;

    public Task(String title, String description,String type,String DueDate){
        this.title = title;
        this.description = description;
        this.type = type;
        this.DueDate = DueDate;
        this.id = last_id+1;
        this.status = true;
    }

    public int getId() {
        return id;
    }

    public String getDueDate() {
        return DueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(String dueDate) {
        this.DueDate = dueDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static void setLast_id(int last_id) {
        Task.last_id = last_id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }
}
