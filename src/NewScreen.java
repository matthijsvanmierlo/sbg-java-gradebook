import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NewScreen extends JPanel {

    ////////////////////////////////////////////////////////////////////////////////

    Controller controller;
    // Label for name
    JLabel name;
    // Text field for name
    JTextField classNameInput;
    // Button for student list...
    JButton saveStudentsButton;
    // Button for back...
    JButton backButton;
    // Text Area for name entry
    JTextArea studentEntry;

    ////////////////////////////////////////////////////////////////////////////////

    public NewScreen(Controller _controller){
        controller = _controller;
        setSize(new Dimension(StartProgram.SIZE, StartProgram.SIZE));
        setPreferredSize(new Dimension(StartProgram.SIZE, StartProgram.SIZE));
        setBackground(Color.LIGHT_GRAY);
        setLayout(null);
        // Name...
        name = new JLabel("Class Name: ");
        name.setBounds(50, 50, 100, 50);
        // Class name entry...
        classNameInput = new JTextField();
        classNameInput.setBounds(200, 50, 100, 50);
        // Save class button...
        saveStudentsButton = new JButton("Save Class");
        saveStudentsButton.setBounds(350, 50, 100, 50);
        saveStudentsButton.addActionListener(new SaveStudentsAction());
        // Back button...
        backButton = new JButton("Back");
        backButton.setBounds(50, 400, 100, 50);
        backButton.addActionListener(new BackButtonAction());
        // Student entry box...
        studentEntry = new JTextArea();
        studentEntry.setBounds(50, 150, 400, 200);
        studentEntry.setText("Please enter one student per line... e.g.\nStudent 1, a@b.com\nStudent 2, a@b.com\nStudent 3, a@b.com");
        add(name);
        add(classNameInput);
        add(saveStudentsButton);
        add(backButton);
        add(studentEntry);
        setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class BackButtonAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Back Button Pressed");
            controller.showHome();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////

    private class SaveStudentsAction implements  ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("Save Students Button Pressed");
            // Get preferred save location from user...
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(controller.mainWindow);
            // If the user correctly selects a folder...
            if(option == JFileChooser.APPROVE_OPTION){
                File folder = fileChooser.getSelectedFile();
                String directoryName = folder.getAbsolutePath();
                File classFile = new File(directoryName + "/" + classNameInput.getText() + ".txt");
                controller.currFile = classFile;
                System.out.println(classFile.getAbsolutePath());
                try {
                    // Create a new file on the disk at that location
                    // If a file already exists, then that file is overwritten
                    classFile.createNewFile();
                    FileWriter fw = new FileWriter(classFile.getAbsolutePath());
                    Scanner lineScanner = new Scanner(studentEntry.getText());
                    ArrayList<String> tempList = new ArrayList<String>();
                    lineScanner.useDelimiter("\n");
                    // While there are still students to be stored in that list...
                    while(lineScanner.hasNext()){
                        String line = lineScanner.next();
                        tempList.add(line);
                        // fw.write(""+ line + "\n");
                    }
                    // Write class name to top of file
                    fw.write(String.format("%s\n", classNameInput.getText()));
                    // Write number of students next in file
                    fw.write(String.format("%d\n", tempList.size()));
                    // Write number of standards next...
                    fw.write(String.format("%d\n", 0));
                    // Write each student on each line with email...
                    // Update model in controller with new class information...
                    fw.write(String.format("%s\n", "-----------------------------"));
                    for(String tempStudent : tempList){
                        // Student info written to line...
                        fw.write(String.format("%s\n", tempStudent));
                        // Number of assignments for student...
                        fw.write(String.format("%d\n", 0));
                        // Separator for students...
                        fw.write(String.format("%s\n", "-----------------------------"));
                    }
                    fw.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }else {

            }
            controller.showHome();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
}
