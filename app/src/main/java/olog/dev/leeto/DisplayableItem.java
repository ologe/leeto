package olog.dev.leeto;

public class DisplayableItem {

    private final long id;
    private final String title;
    private final String description;

    public DisplayableItem(long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
