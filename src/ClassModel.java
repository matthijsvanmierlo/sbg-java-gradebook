import java.util.ArrayList;

public class ClassModel {

    ////////////////////////////////////////////////////////////////////////////////

    ArrayList<ClassStandard> standards;
    ArrayList<Student> students;
    String name;

    ////////////////////////////////////////////////////////////////////////////////

    public ClassModel(String _className, ArrayList<ClassStandard> _standards, ArrayList<Student> _students) {
        standards = _standards;
        students = _students;
        name = _className;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getStudentNames(){
        ArrayList<String> nameStrings = new ArrayList<>();
        for(Student s: students){
            nameStrings.add(s.name);
        }
        return nameStrings;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public ArrayList<String> getStandardNames(){
        ArrayList<String> standardStrings = new ArrayList<>();
        for(ClassStandard s: standards){
            standardStrings.add(s.name);
        }
        return standardStrings;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public ClassStandard getStandard(String standardName){
        for(ClassStandard s : standards){
            if(s.name.equals(standardName)){
                return s;
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public double[] standardData(String standardName){
        double total = 0.0;
        int numAss = 0;
        double lowestGrade = 200;
        double highestGrade = -1;
        for(Student s : students){
            for(Assignment a : s.assignments){
                if(a.standard.name.equals(standardName)){
                    numAss += 1;
                    total += a.grade;
                    if(a.grade < lowestGrade){
                        lowestGrade = a.grade;
                    }
                    if(a.grade > highestGrade){
                        highestGrade = a.grade;
                    }
                }
            }
        }
        // Average, High, Low
        return new double[]{total / numAss, highestGrade, lowestGrade};
    }

    ////////////////////////////////////////////////////////////////////////////////

    public Student getStudent(String name){
        for(Student s : students){
            if(s.name.equals(name)){
                return s;
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////
}
