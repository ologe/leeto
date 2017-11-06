package olog.dev.leeto.model;

public class DisplayableJourney {

    private final long id;
    private final String name;
    private final String date;
    private final String location;
    private final String description;

    public DisplayableJourney(long id, String name, String date, String location, String description) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.location = location;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DisplayableJourney that = (DisplayableJourney) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null)
            return false;
        if (location != null ? !location.equals(that.location) : that.location != null)
            return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
