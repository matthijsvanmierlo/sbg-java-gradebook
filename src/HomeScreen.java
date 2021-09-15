import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeScreen extends JPanel {

    ////////////////////////////////////////////////////////////////////////////////

    // Reference to controller for entire application
    Controller controller;
    // Controls for this particular screen
    JButton newButton;
    JButton openButton;
    JLabel title;
    JLabel descr;

    ////////////////////////////////////////////////////////////////////////////////

    public HomeScreen(Controller _controller){
        controller = _controller;
        setLayout(null);
        setSize(new Dimension(StartProgram.SIZE, StartProgram.SIZE));
        setPreferredSize(new Dimension(StartProgram.SIZE, StartProgram.SIZE));
        setBackground(Color.LIGHT_GRAY);
        // New button
        newButton = new JButton("New");
        newButton.setBounds(50, 200, 150, 50);
        // Add action listener
        newButton.addActionListener(new NewButtonAction());
        // Open button
        openButton = new JButton("Open");
        openButton.setBounds(300, 200, 150, 50);
        // Add action listener
        openButton.addActionListener(new OpenButtonAction());
        // Title
        title = new JLabel("Grades-2-Go");
        title.setFont(new Font("Calibri", Font.BOLD, 20));
        title.setBounds(185, 50, 150, 50);
        title.setForeground(Color.BLACK);
        // Descr
        descr = new JLabel("\"New\" creates a new class file. \n\"Open\" opens an existing file...");
        descr.setFont(new Font("Calibri", Font.PLAIN, 14));
        descr.setBounds(50, 300, 400, 100);
        // Add elements on JPanel...
        add(descr);
        add(newButton);
        add(openButton);
        add(title);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class NewButtonAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("New Button Pressed");
            controller.showNew();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class OpenButtonAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(controller.mainWindow);
            if(option == JFileChooser.APPROVE_OPTION) {
                File chosen = fileChooser.getSelectedFile();
                parseCurrentFile(chosen);
                // TODO go to next screen with class information...
                // Class is read in from file... that works!
                // Make a screen that has add student, add assignment, add standard, show average
                // TODO a lot of functionality will be implemented here...
                controller.showInfo();
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void parseCurrentFile(File chosen){
        System.out.println(chosen.getAbsolutePath());
        System.out.println("Open Button Pressed");
        // Read in class with students, grades and assignments...
        try {
            Scanner classScanner = new Scanner(chosen);
            // TODO error checking for correct file format...
            String className = classScanner.nextLine();
            int numStudents = Integer.parseInt(classScanner.nextLine());
            int numStandards = Integer.parseInt(classScanner.nextLine());
            ArrayList<ClassStandard> tempStandards = new ArrayList<>();
            ArrayList<Student> tempStudents = new ArrayList<>();
            for(int i = 0; i < numStandards; i++){
                String line = classScanner.nextLine();
                int numStart = line.indexOf("(") + 1;
                int numEnd = line.indexOf(")");
                int num = Integer.parseInt(line.substring(numStart, numEnd));
                line = line.substring(numEnd + 1);
                int nameStart = line.indexOf("(") + 1;
                int nameEnd = line.indexOf(")");
                String name = line.substring(nameStart, nameEnd);
                String descr = line.substring(nameEnd + 1);
                tempStandards.add(new ClassStandard(name, descr, num));
            }
            // Consume line break...
            classScanner.nextLine();
            for(int i = 0; i < numStudents; i++){
                String sInfo = classScanner.nextLine();
                String name = sInfo.substring(0, sInfo.indexOf(","));
                String email = sInfo.substring(sInfo.indexOf(",") + 1);
                Student s = new Student(name, email);
                int numAssignments = Integer.parseInt(classScanner.nextLine());
                for(int j = 0; j < numAssignments; j++){
                    String assInfo = classScanner.nextLine();
                    // Read in standard number
                    int standardStart = assInfo.indexOf("(") + 1;
                    int standardEnd = assInfo.indexOf(")");
                    int num = Integer.parseInt(assInfo.substring(standardStart, standardEnd));
                    assInfo = assInfo.substring(standardEnd + 1);
                    // Read in assignment
                    int assStart = assInfo.indexOf("(") + 1;
                    int assEnd = assInfo.indexOf(")");
                    String assName = assInfo.substring(assStart, assEnd);
                    assInfo = assInfo.substring(assEnd + 1);
                    // Read in grade...
                    int gradeStart = assInfo.indexOf("(") + 1;
                    int gradeEnd = assInfo.indexOf(")");
                    float grade = Float.parseFloat(assInfo.substring(gradeStart, gradeEnd));
                    // Add assignment
                    for(ClassStandard c : tempStandards){
                        if(c.number == num){
                            s.addAssignment(new Assignment(c, assName, grade));

                        }
                    }

                }
                tempStudents.add(s);
                classScanner.nextLine();
            }
            ClassModel currClass = new ClassModel(className, tempStandards, tempStudents);
            controller.currClass = currClass;
            controller.currFile = chosen;
            System.out.println("Successfuly loaded class...");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
}
