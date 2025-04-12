package src;

public class Education {
    String degree;
    String university;
    String year;
    String CGPA;

    public Education(String degree, String university, String year, String CGPA) {
        this.degree = degree;
        this.university = university;
        this.year = year;
        this.CGPA = CGPA;
    }

    public String toString() {
        return degree + " | " + university + " | " + year + " | " + CGPA;
    }
}
