package src;

public class Experience {
    String company;
    String role;
    String duration;
    String description;

    public Experience(String company, String role, String duration, String description) {
        this.company = company;
        this.role = role;
        this.duration = duration;
        this.description = description;
    }

    public String toString() {
        return company + " | " + role + " | " + duration + " | " + description;
    }
}
