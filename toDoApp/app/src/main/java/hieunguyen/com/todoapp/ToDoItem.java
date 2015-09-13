package hieunguyen.com.todoapp;

/**
 * Created by hieunguyen on 9/12/15.
 */
public class ToDoItem {

    private String text;
    private Boolean completed;
    private Boolean isHeader;

    public ToDoItem(String text, Boolean isHeader) {
        this.text = text;
        this.isHeader = isHeader;
        this.completed = false;
    }

    public Boolean getIsHeader() {
        return isHeader;
    }

    public void setIsHeader(Boolean isHeader) {
        this.isHeader = isHeader;
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

    @Override
    public String toString() {
        return '[' +
                "text='" + text + '\'' +
                ", completed=" + completed +
                ", isHeader=" + isHeader +
                ']';
    }
}
