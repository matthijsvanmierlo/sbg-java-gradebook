import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class Controller {

    ////////////////////////////////////////////////////////////////////////////////

    // Keep reference to initial mainWindow (JFrame)
    JFrame mainWindow;
    JPanel currScreen;

    ////////////////////////////////////////////////////////////////////////////////

    // Keep reference to each individual screen (JPanel)
    HomeScreen homeScreen;
    NewScreen newScreen;
    ClassInfo infoScreen;

    ////////////////////////////////////////////////////////////////////////////////

    // Keep reference to the current class that has been opened...
    ClassModel currClass;
    File currFile = null;
    static final int HOME = 0;
    static final int NEW = 1;
    static final int SUMMARY = 2;
    static final int MODIFY = 3;

    ////////////////////////////////////////////////////////////////////////////////

    public Controller(JFrame _mainWindow){
        mainWindow = _mainWindow;
        currClass = new ClassModel("", new ArrayList<ClassStandard>(), new ArrayList<Student>());
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void addHomeScreen(HomeScreen _homeScreen){
        homeScreen = _homeScreen;
        mainWindow.add(homeScreen);
        currScreen = homeScreen;
        mainWindow.pack();
        mainWindow.setVisible(true);
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void addNewScreen(NewScreen _newScreen){
        newScreen = _newScreen;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void addInfoScreen(ClassInfo _infoScreen){
        infoScreen = _infoScreen;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public boolean showHome(){
        removeCurrent();
        currScreen = homeScreen;
        mainWindow.add(homeScreen);
        mainWindow.pack();
        mainWindow.revalidate();
        mainWindow.repaint();
        mainWindow.setVisible(true);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public boolean showNew(){
        removeCurrent();
        currScreen = newScreen;
        mainWindow.add(newScreen);
        mainWindow.pack();
        mainWindow.revalidate();
        mainWindow.repaint();
        mainWindow.setVisible(true);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public boolean showInfo(){
        removeCurrent();
        currScreen = infoScreen;
        infoScreen.updateClassSettings();
        infoScreen.updateClassStatistics();
        mainWindow.add(currScreen);
        mainWindow.pack();
        mainWindow.revalidate();
        mainWindow.repaint();
        mainWindow.setVisible(true);
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public boolean showSummary(){
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public boolean showModify(){
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public boolean removeCurrent(){
        mainWindow.remove(currScreen);
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
}
