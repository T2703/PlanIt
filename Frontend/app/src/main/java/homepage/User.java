package homepage;

public class User {
    private int avatarResource;
    private boolean isOnline;

    public User(int avatarResource, boolean isOnline) {
        this.avatarResource = avatarResource;
        this.isOnline = isOnline;
    }

    public int getAvatarResource() {
        return avatarResource;
    }

    public boolean isOnline() {
        return isOnline;
    }
}
