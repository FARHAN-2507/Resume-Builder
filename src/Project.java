package src;

public class Project {
    public String title;
    public String description;
    public String link;

    public Project(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    @Override
    public String toString() {
        return title + " | " + description + " | " + link;
    }
}
