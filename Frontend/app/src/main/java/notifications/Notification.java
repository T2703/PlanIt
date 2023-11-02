package notifications;

public class Notification {
    private String title;
    private String description;
    private boolean isRead;
    private boolean isSelected;

    public Notification(String title, String description, boolean isRead) {
        this.title = title;
        this.description = description;
        this.isRead = isRead;
        this.isSelected = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean flag) {
        this.isSelected = flag;
    }
}
