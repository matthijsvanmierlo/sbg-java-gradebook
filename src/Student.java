import java.util.ArrayList;

public class Student {

    ////////////////////////////////////////////////////////////////////////////////

    ArrayList<Assignment> assignments;
    String name;
    String email;

    ////////////////////////////////////////////////////////////////////////////////

    public Student(String _name, String _email){
        name = _name;
        email = _email;
        assignments = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void addAssignment(Assignment _assignment){
        assignments.add(_assignment);
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void changeEmail(String _email){
        email = _email;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void changeName(String _name){
        name = _name;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public String[] getAssignmentNames(String standard){
        // TODO refactor into array list and return as array...
        ArrayList<String> assNames = new ArrayList<>();
        for(Assignment a: assignments){
            if(a.standard.name.equals(standard)){
                assNames.add(a.descr);
            }
        }
        return assNames.toArray(new String[0]);
    }

    ////////////////////////////////////////////////////////////////////////////////

    // TODO change mode to decaying average eventually...
    public double getStandardAverage(String standardName){
        double total = 0;
        double num = 0;
        for(Assignment a : assignments){
            if(a.standard.name.equals(standardName)){
                total += a.grade;
                num += 1;
            }
        }
        return total / num;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public double getAssignmentGrade(String assignmentName){
        for(Assignment a : assignments){
            if(a.descr.equals(assignmentName)){
                return a.grade;
            }
        }
        return 0;
    }

    ////////////////////////////////////////////////////////////////////////////////

}
