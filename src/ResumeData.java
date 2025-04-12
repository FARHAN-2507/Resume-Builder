package src;
import java.util.ArrayList;
import java.util.List;

public class ResumeData {
    public String fullName;
    public String email;
    public String phone;
    public String address;

    public List<Education> educationList = new ArrayList<>();
    public List<Experience> experienceList = new ArrayList<>();
    public List<String> certifications = new ArrayList<>();

    public List<String> skills = new ArrayList<>();
    public List<Project> projects = new ArrayList<>();
}
