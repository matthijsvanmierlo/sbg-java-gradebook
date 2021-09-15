import javax.swing.*;
import java.awt.*;

public class StartProgram {

    ////////////////////////////////////////////////////////////////////////////////

    static final int SIZE = 500;

    ////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args){
        JFrame mainWindow = new JFrame("Grades-2-Go");
        mainWindow.setSize(new Dimension(SIZE, SIZE));
        mainWindow.setPreferredSize(new Dimension(SIZE, SIZE));
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainWindow.setResizable(false);
        // Add screens and controller here...
        Controller controller = new Controller(mainWindow);
        HomeScreen homeScreen = new HomeScreen(controller);
        NewScreen newScreen = new NewScreen(controller);
        ClassInfo infoScreen = new ClassInfo(controller);
        controller.addInfoScreen(infoScreen);
        controller.addHomeScreen(homeScreen);
        controller.addNewScreen(newScreen);
    }

    ////////////////////////////////////////////////////////////////////////////////
}
