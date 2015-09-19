package hieunguyen.com.todoapp;

/**
 * Created by hieunguyen on 9/12/15.
 * Data Transfer Object to hold the data retrieved from the database and allow Java to display it.
 */
public class ToDoItem {

    private int id;
    private String text;
    private Boolean completed;

    public ToDoItem(String text) {
        this.text = text;
        this.completed = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return '[' +
                "text='" + text + '\'' +
                ", completed=" + completed +
                ']';
    }
}
