
package src;

import javax.swing.*;
import java.awt.*;


// Define the Project class
class Project {
    String title;
    String description;
    String link;

    public Project(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }
}

public class MainForm extends JFrame {
    private JTextField nameField, emailField, phoneField;
    private JTextArea addressField;

    private DefaultListModel<Education> educationListModel = new DefaultListModel<>();
    private DefaultListModel<Experience> experienceListModel = new DefaultListModel<>();
    private DefaultListModel<String> certificationsListModel = new DefaultListModel<>();

    private DefaultListModel<String> skillsListModel = new DefaultListModel<>();
    private DefaultListModel<Project> projectListModel = new DefaultListModel<>();



    public MainForm() {
        setTitle("Resume Builder");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        tabs.add("Personal Info", createPersonalInfoPanel());
        tabs.add("Education", createEducationPanel());
        tabs.add("Experience", createExperiencePanel());
        tabs.add("Certifications", createCertificationsPanel());
        tabs.add("Skills", createSkillsPanel());
        tabs.add("Projects", createProjectsPanel());

        JButton previousBtn = new JButton("Previous");
        JButton nextBtn = new JButton("Next");
        JButton generateBtn = new JButton("Generate Resume (PDF)");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(previousBtn);
        bottomPanel.add(nextBtn);
        bottomPanel.add(generateBtn);

        add(tabs, "Center");
        add(bottomPanel, "South");

        previousBtn.addActionListener(e -> {
            int current = tabs.getSelectedIndex();
            if (current > 0) tabs.setSelectedIndex(current - 1);
        });

        nextBtn.addActionListener(e -> {
            int current = tabs.getSelectedIndex();
            if (current < tabs.getTabCount() - 1) tabs.setSelectedIndex(current + 1);
        });

        generateBtn.addActionListener(e -> {
            ResumeData data = collectResumeData();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save Resume as PDF");
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                String path = fileChooser.getSelectedFile().getAbsolutePath();
                if (!path.toLowerCase().endsWith(".pdf")) {
                    path += ".pdf";
                }
                try {
                    PDFGenerator.generatePDF(data, path);
                    JOptionPane.showMessageDialog(null, "Resume saved to:\n" + path);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to generate PDF.");
                }
            }
        });
        ;
    }

    private JPanel createPersonalInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        nameField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);
        addressField = new JTextArea(3, 20);
        JScrollPane addressScroll = new JScrollPane(addressField);

        gbc.gridx = 0; gbc.gridy = 0; panel.add(new JLabel("Full Name:"), gbc);
        gbc.gridx = 1; panel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; panel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1; panel.add(phoneField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; panel.add(new JLabel("Address:"), gbc);
        gbc.gridx = 1; panel.add(addressScroll, gbc);

        return panel;
    }

    private JPanel createEducationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField degreeField = new JTextField(20);
        JTextField universityField = new JTextField(20);
        JTextField yearField = new JTextField(10);
        JTextField gradeField = new JTextField(10);

        JList<Education> educationList = new JList<>(educationListModel);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Degree:"), gbc);
        gbc.gridx = 1; formPanel.add(degreeField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("University:"), gbc);
        gbc.gridx = 1; formPanel.add(universityField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Year:"), gbc);
        gbc.gridx = 1; formPanel.add(yearField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("CGPA:"), gbc);
        gbc.gridx = 1; formPanel.add(gradeField, gbc);

        JButton addBtn = new JButton("Add Education");
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(addBtn, gbc);

        addBtn.addActionListener(e -> {
            String degree = degreeField.getText();
            String university = universityField.getText();
            String year = yearField.getText();
            String grade = gradeField.getText();
            if (!degree.isEmpty() && !university.isEmpty()) {
                educationListModel.addElement(new Education(degree, university, year, grade));
                degreeField.setText(""); universityField.setText(""); yearField.setText(""); gradeField.setText("");
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(educationList), BorderLayout.CENTER);
        return panel;
    }

    private JPanel createExperiencePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField companyField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField durationField = new JTextField(15);
        JTextArea descriptionArea = new JTextArea(3, 20);
        JScrollPane descScroll = new JScrollPane(descriptionArea);

        gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Company:"), gbc);
        gbc.gridx = 1; formPanel.add(companyField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Job Title:"), gbc);
        gbc.gridx = 1; formPanel.add(roleField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Duration:"), gbc);
        gbc.gridx = 1; formPanel.add(durationField, gbc);

        gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; formPanel.add(descScroll, gbc);

        JButton addBtn = new JButton("Add Experience");
        gbc.gridx = 1; gbc.gridy = 4; formPanel.add(addBtn, gbc);

        JList<Experience> experienceList = new JList<>(experienceListModel);
        JScrollPane listScrollPane = new JScrollPane(experienceList);
        listScrollPane.setPreferredSize(new Dimension(500, 150));
        listScrollPane.setBorder(BorderFactory.createTitledBorder("Your Experience Entries"));

        addBtn.addActionListener(e -> {
            String company = companyField.getText();
            String role = roleField.getText();
            String duration = durationField.getText();
            String description = descriptionArea.getText();
            if (!company.isEmpty() && !role.isEmpty()) {
                experienceListModel.addElement(new Experience(company, role, duration, description));
                companyField.setText(""); roleField.setText(""); durationField.setText(""); descriptionArea.setText("");
            }
        });

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(listScrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSkillsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField skillField = new JTextField(20);
        JList<String> skillsList = new JList<>(skillsListModel);
        JScrollPane skillsScroll = new JScrollPane(skillsList);
        skillsScroll.setPreferredSize(new Dimension(200, 100));

        JButton addSkillBtn = new JButton("Add Skill");
        addSkillBtn.addActionListener(e -> {
            String skill = skillField.getText();
            if (!skill.isEmpty()) {
                skillsListModel.addElement(skill);
                skillField.setText("");
            }
        });

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Skill:"), gbc);
        gbc.gridx = 1; panel.add(skillField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(addSkillBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(skillsScroll, gbc);

        return panel;
    }

    private JPanel createProjectsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
    
        // ========= Form Panel =========
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField titleField = new JTextField(20);
        JTextArea descArea = new JTextArea(3, 20);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        JTextField linkField = new JTextField(20);
    
        // Row 1: Project Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Project Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleField, gbc);
    
        // Row 2: Description
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(descArea), gbc);
    
        // Row 3: Link
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Link/Tech Stack:"), gbc);
        gbc.gridx = 1;
        formPanel.add(linkField, gbc);
    
        // Row 4: Add Button
        JButton addProjectBtn = new JButton("Add Project");
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(addProjectBtn, gbc);
    
        // ========= Project List =========
        JList<Project> projectList = new JList<>(projectListModel);
        JScrollPane listScroll = new JScrollPane(projectList);
        listScroll.setPreferredSize(new Dimension(500, 150));
        listScroll.setBorder(BorderFactory.createTitledBorder("Project Entries"));
    
        // ========= Add Action =========
        addProjectBtn.addActionListener(e -> {
            String title = titleField.getText();
            String desc = descArea.getText();
            String link = linkField.getText();
    
            if (!title.isEmpty()) {
                projectListModel.addElement(new Project(title, desc, link));
                titleField.setText("");
                descArea.setText("");
                linkField.setText("");
            } else {
                JOptionPane.showMessageDialog(panel, "Project title is required.");
            }
        });
    
        // ========= Combine and Return =========
        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(listScroll, BorderLayout.CENTER);
    
        return panel;
    }
    
    private JPanel createCertificationsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        JTextField certField = new JTextField(20);
        JButton addCertBtn = new JButton("Add Certification");
        JList<String> certList = new JList<>(certificationsListModel);
        JScrollPane certScroll = new JScrollPane(certList);
        certScroll.setPreferredSize(new Dimension(400, 100));
        certScroll.setBorder(BorderFactory.createTitledBorder("Added Certifications"));
    
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Certification:"), gbc);
        gbc.gridx = 1;
        panel.add(certField, gbc);
    
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(addCertBtn, gbc);
    
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        panel.add(certScroll, gbc);
    
        addCertBtn.addActionListener(e -> {
            String cert = certField.getText().trim();
            if (!cert.isEmpty()) {
                certificationsListModel.addElement(cert);
                certField.setText("");
            }
        });
    
        return panel;
    }
    
    

    private ResumeData collectResumeData() {
        ResumeData data = new ResumeData();
        data.fullName = nameField.getText();
        data.email = emailField.getText();
        data.phone = phoneField.getText();
        data.address = addressField.getText();

        for (int i = 0; i < educationListModel.size(); i++) {
            data.educationList.add(educationListModel.getElementAt(i));
        }

        for (int i = 0; i < experienceListModel.size(); i++) {
            data.experienceList.add(experienceListModel.getElementAt(i));
        }
        for (int i = 0; i < certificationsListModel.size(); i++) {
            data.certifications.add(certificationsListModel.getElementAt(i));
        }
        

        for (int i = 0; i < skillsListModel.size(); i++) {
            data.skills.add(skillsListModel.getElementAt(i));
        }

        for (int i = 0; i < projectListModel.size(); i++) {
            data.projects.add(projectListModel.getElementAt(i));
        }
        
        

        return data;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainForm().setVisible(true));
    }
}
