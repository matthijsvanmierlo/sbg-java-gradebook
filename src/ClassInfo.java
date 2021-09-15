import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class ClassInfo extends JPanel {
    ////////////////////////////////////////////////////////////////////////////////

    // Reference to the main controller
    Controller controller;
    // Class average pane on left...
    // Individual student view on right...
    // Button for back navigation... (100 x 50)
    JButton backButton;
    // Button for add assignment...
    JButton addAssignmentButton;
    // Button for add standard...
    JButton addStandardButton;
    // Button for edit assignment for each student...
    // Button for delete assignment for each student...
    // Font for all buttons on this screen...
    // Combo Box to select student...
    JComboBox<String> students;
    // Combo Box to select standard
    JComboBox<String> standards;
    // Combo Box to select assignment
    JComboBox<String> assignments;
    // Combo Box for selected standard by student...
    JComboBox<String> studentStandards;
    // Label for Title
    JLabel classStats;
    JLabel selectStudent;
    JLabel classAverage;
    JLabel classHigh;
    JLabel classLow;
    JLabel standardAverage;
    JLabel assignmentGrade;
    JButton updateAssignment;
    JLabel newTitleLabel;
    JTextField newGrade;
    JTextField newTitle;
    Font buttonFont = new Font("Calibri", Font.PLAIN, 13);
    Font headerFont = new Font("Calibri", Font.BOLD, 18);

    ////////////////////////////////////////////////////////////////////////////////

    public ClassInfo(Controller _controller){
        //////////////////////////////////////
        setSize(new Dimension(StartProgram.SIZE, StartProgram.SIZE));
        setPreferredSize(new Dimension(StartProgram.SIZE, StartProgram.SIZE));
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        controller = _controller;
        //////////////////////////////////////
        backButton = new JButton("Back");
        backButton.setFont(buttonFont);
        backButton.setBounds(300, 315, 150, 50);
        backButton.addActionListener(new BackButtonClicked());

        addAssignmentButton = new JButton("Add Assignment");
        addAssignmentButton.setFont(buttonFont);
        addAssignmentButton.setBounds(300, 255, 150, 50);
        addAssignmentButton.addActionListener(new AddAssignmentClicked());

        addStandardButton = new JButton("Add Standard");
        addStandardButton.setFont(buttonFont);
        addStandardButton.setBounds(300, 200, 150, 50);
        addStandardButton.addActionListener(new AddStandardClicked());

        updateAssignment = new JButton("Update");
        updateAssignment.setFont(buttonFont);
        updateAssignment.setBounds(150, 300, 100, 30);
        updateAssignment.addActionListener(new UpdateAssignmentButton());

        newGrade = new JTextField();
        newGrade.setFont(buttonFont);
        newGrade.setBounds(150, 335, 100, 30);

        //////////////////////////////////////
        students = new JComboBox<>(controller.currClass.getStudentNames().toArray(new String[0]));
        students.setBounds(25, 200, 250, 20);
        students.setFont(buttonFont);

        standards = new JComboBox<>(controller.currClass.getStandardNames().toArray(new String[0]));
        standards.setBounds(25, 100, 250, 30);
        standards.setFont(buttonFont);

        studentStandards = new JComboBox<>();
        studentStandards.setBounds(25, 223, 250, 30);
        studentStandards.setFont(buttonFont);

        assignments = new JComboBox<>();
        assignments.setBounds(25, 250, 250, 30);
        assignments.setFont(buttonFont);

        //////////////////////////////////////

        classStats = new JLabel("Class Statistics");
        classStats.setFont(headerFont);
        classStats.setBounds(25, 50, 200, 30);

        selectStudent = new JLabel("Student Statistics");
        selectStudent.setFont(headerFont);
        selectStudent.setBounds(25, 150, 200, 30);

        classAverage = new JLabel("Class Average: ");
        classAverage.setFont(buttonFont);
        classAverage.setBounds(320, 50, 150, 30);

        classHigh = new JLabel("Class High: ");
        classHigh.setFont(buttonFont);
        classHigh.setBounds(320, 85, 150, 30);

        classLow = new JLabel("Class Low: ");
        classLow.setFont(buttonFont);
        classLow.setBounds(320, 120, 150, 30);

        standardAverage = new JLabel("Average: ");
        standardAverage.setFont(buttonFont);
        standardAverage.setBounds(25, 300, 100, 30);

        assignmentGrade = new JLabel("Grade: ");
        assignmentGrade.setFont(buttonFont);
        assignmentGrade.setBounds(25, 335, 100, 30);

        //////////////////////////////////////

        add(standardAverage);
        add(newGrade);
        add(updateAssignment);
        add(assignmentGrade);
        add(backButton);
        add(addStandardButton);
        add(addAssignmentButton);
        add(assignments);
        add(standards);
        add(students);
        add(classStats);
        add(selectStudent);
        add(classHigh);
        add(classLow);
        add(classAverage);
        add(studentStandards);

        //////////////////////////////////////

        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void updateClassSettings() {
        remove(students);
        remove(standards);
        remove(assignments);
        remove(studentStandards);
        studentStandards = new JComboBox<>(controller.currClass.getStandardNames().toArray(new String[0]));
        studentStandards.setBounds(25, 223, 250, 30);
        studentStandards.setFont(buttonFont);
        studentStandards.addActionListener(new StudentSelected());
        students = new JComboBox<>(controller.currClass.getStudentNames().toArray(new String[0]));
        students.setBounds(25, 200, 250, 25);
        students.setFont(buttonFont);
        students.addActionListener(new StudentSelected());
        standards = new JComboBox<>(controller.currClass.getStandardNames().toArray(new String[0]));
        standards.setBounds(25, 100, 250, 25);
        standards.setFont(buttonFont);
        standards.addActionListener(new StandardSelected());
        add(studentStandards);
        add(students);
        add(standards);
        add(assignments);
        updateStudentStatistics();
        refreshView();
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void updateClassStatistics(){
        double[] stats = controller.currClass.standardData(standards.getItemAt(standards.getSelectedIndex()));
        classAverage.setText(String.format("Class Average: %.2f", stats[0]));
        classAverage.setFont(buttonFont);
        classAverage.setBounds(320, 50, 150, 30);
        classHigh.setText(String.format("Class High: %.2f", stats[1]));
        classHigh.setFont(buttonFont);
        classHigh.setBounds(320, 85, 150, 30);
        classLow.setText(String.format("Class Low: %.2f", stats[2]));
        classLow.setFont(buttonFont);
        classLow.setBounds(320, 120, 150, 30);
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void updateStudentStatistics(){
        // Update visible components on the screen based on the selected student
        remove(assignments);
        String currStandard = studentStandards.getItemAt(studentStandards.getSelectedIndex());
        Student currStudent = controller.currClass.getStudent(students.getItemAt(students.getSelectedIndex()));
        assignments = new JComboBox<>(currStudent.getAssignmentNames(currStandard));
        assignments.setBounds(25, 250, 250, 30);
        assignments.setFont(buttonFont);
        assignments.addActionListener(new AssignmentSelected());
        String currStudentName = students.getItemAt(students.getSelectedIndex());
        String currAssignment = assignments.getItemAt(assignments.getSelectedIndex());
        standardAverage.setText(String.format("Average:\t %.2f", controller.currClass.getStudent(currStudentName).getStandardAverage(currStandard)));
        assignmentGrade.setText(String.format("Grade: \t\t\t%.2f", controller.currClass.getStudent(currStudentName).getAssignmentGrade(currAssignment)));
        add(assignments);
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void refreshView(){
        revalidate();
        repaint();
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class StudentSelected implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateStudentStatistics();
            refreshView();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class UpdateAssignmentButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Update Assignment...");
            double newStudentGrade = Double.parseDouble(newGrade.getText());
            String currAssignment = assignments.getItemAt(assignments.getSelectedIndex());
            String currStudentName = students.getItemAt(students.getSelectedIndex());
            for(Assignment a : controller.currClass.getStudent(currStudentName).assignments){
                if(a.descr.equals(currAssignment)){
                    a.grade = (float) newStudentGrade;
                }
            }
            updateStudentStatistics();
            refreshView();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class StandardSelected implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            updateClassStatistics();
            // Update visible components on the screen...
            refreshView();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class AssignmentSelected implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Do something here
            // Update visible components on the screen...
            System.out.println("Assignment Selected");
            refreshView();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class BackButtonClicked implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Save all data back to the file...
            // TODO save file name when opened to automatically save...
            try {
                FileWriter fw = new FileWriter(controller.currFile.getAbsolutePath());
                fw.write(String.format("%s\n", controller.currClass.name));
                fw.write(String.format("%d\n", controller.currClass.students.size()));
                fw.write(String.format("%d\n", controller.currClass.standards.size()));
                for(ClassStandard s : controller.currClass.standards){
                    fw.write(String.format("(%d) (%s) (%s)\n", s.number, s.name, s.descr));
                }
                fw.write(String.format("____________________________________\n"));
                for(Student s : controller.currClass.students){
                    fw.write(String.format("%s, %s\n", s.name, s.email));
                    fw.write(String.format("%d\n", s.assignments.size()));
                    for(Assignment a : s.assignments){
                        fw.write(String.format("(%d) (%s) (%f)\n", a.standard.number, a.descr, a.grade));
                    }
                    fw.write(String.format("____________________________________\n"));
                }
                fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            controller.showHome();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class AddStandardClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String s = (String) JOptionPane.showInputDialog(controller.mainWindow, "Please enter new standard: ",
                    "Enter here...");
            if(s != null && s.length() > 0){
                controller.currClass.standards.add(new ClassStandard(s, s, controller.currClass.standards.size() + 1));
                updateStudentStatistics();
                updateClassStatistics();
                updateClassSettings();
                refreshView();
            }

        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class AddAssignmentClicked implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            ClassStandard s = controller.currClass.getStandard(studentStandards.getItemAt(studentStandards.getSelectedIndex()));
            boolean wholeClass = false;
            boolean answered = false;
            int result = JOptionPane.showConfirmDialog(controller.mainWindow,
                    String.format("Is this assignment in - %s - for the entire class?", s.name),
                    "Add Assignment",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                System.out.println("YES PRESSED");
                wholeClass = true;
                answered = true;
            }else if (result == JOptionPane.NO_OPTION){
                System.out.println("NO PRESSED");
                wholeClass = false;
                answered = true;
            }

            String newAssName = (String) JOptionPane.showInputDialog(controller.mainWindow, "Please enter new standard: ",
                    "Enter here...");

            if(wholeClass && answered && newAssName != null) {
                for(Student a : controller.currClass.students){
                    a.addAssignment(new Assignment(s, newAssName, 0));
                }
            }else if(!wholeClass && answered && newAssName != null){
                Student currStudent = controller.currClass.getStudent(students.getItemAt(students.getSelectedIndex()));
                currStudent.addAssignment(new Assignment(s, newAssName, 0));
            }else{

            }
            updateStudentStatistics();
            updateClassStatistics();
            updateClassSettings();
            refreshView();
            // Assignment Screen for Individual Student
            // Assignment Screen for Entire Class
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
}
