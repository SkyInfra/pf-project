import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Managment_System {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("******************");
        System.out.println("Welcome To University Management System  ");
        System.out.println("1 : Login as Admin");
        System.out.println("2 : Login as Student");
        System.out.print("Enter your choice :");
        choice = scanner.nextInt();

        if (choice == 1) {
            adminLogin(scanner);
        } else if (choice == 2) {
            studentLogin();
            //testFileWrite();
        } else
            System.out.println("Enter a valid option  !!");

    }

    public static void adminLogin(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter your admin username: ");
        String username = scanner.nextLine();

        System.out.println("Enter your admin password: ");
        String password = scanner.nextLine();
        boolean loginSuccess = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Admin.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println("Read from file:" + line );
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String fileUsername = parts[0].trim();
                    String filePassword = parts[1].trim();
                    //System.out.println("Comparing with :" +fileUsername + "/" +filePassword);
                    if (username.equals(fileUsername) && password.equals(filePassword)) {
                        loginSuccess = true;
                        break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading admin file : " + e.getMessage());
        }
        if (loginSuccess) {
            System.out.println("Login successful ! Welcome,Admin.");
            while (true) {
                System.out.println("***************");
                System.out.println("Admin Menu ");
                System.out.println("1.Add Student");
                System.out.println("2.View Students ");
                System.out.println("3.Enroll Students in Courses ");
                System.out.println("4.Enter Marks ");
                System.out.println("5.Update OR Delete Course ");
                System.out.println("6.Top Performance By Course  ");
                System.out.println("7.Search Students");
                System.out.println("8. Logout");
                System.out.println("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                if (choice == 1) {
                    addStudent(scanner);
                } else if (choice==2) {
                    viewStudents();
                } else if (choice==3) {
                    enrollStudentsInCourse(scanner );
                } else if (choice==4) {
                    enterMarks(scanner);
                } else if (choice==5) {
                    manageCourses(scanner);
                } else if (choice==6) {
                    topPerformerByCourse(scanner);
                } else if (choice==7) {
                    searchStudents(scanner);
                } else if (choice==8) {
                    System.out.println("Logging out.....");
                    break;
                } else
                    System.out.println("Enter a valid choice. Try again");
            }
             } else {
            System.out.println("Login failed. Invalid credentials. ");
            }

    }
    public static void enterMarks(Scanner scanner) {
        System.out.println("Debug : entering enterMarks() ");
        //String file ="marks";
        System.out.println("Enter student registration number :");
        String regNo= scanner.nextLine().trim();
        System.out.println("Enter course name :");
        String course = scanner.nextLine().trim();
        System.out.println("Enter  marks obtained (Out of 100): ");
        int marks  = scanner.nextInt();
        scanner.nextLine();
        String grade;
        if (marks>=85){
            grade ="A";
        } else if (marks>=70) {
            grade="B";
        }
        else if (marks>=60) {
            grade="C";
        }
        else if (marks>=50) {
            grade="D";
        }
        else   {
            grade="F";
        }
        try {
            File file = new File("marks.txt");
            BufferedWriter writer = new BufferedWriter( new FileWriter(file,true));

            writer.write(regNo + "," + course + "," + marks + ",100," + grade );
            writer.newLine();
            writer.close();

            System.out.println("Marks added successfully!  to :" + file.getAbsolutePath());
        }catch (IOException e){
            System.out.println("Error writing marks !" + e.getMessage());
        }

    }
    public static void studentLogin() {
        String fileName ="student";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter student username: ");
        String username = scanner.nextLine().trim();
        System.out.println("Enter student password: ");
        String password = scanner.nextLine().trim();
        boolean loginSuccess = false;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line =reader.readLine())!= null){
                String [] parts =line.split(",");
                if(parts.length == 6){
                    String fileUsername =parts[4].trim();
                    String filePassowrd =parts[5].trim();
                    if(username.equals(fileUsername) && password.equals(filePassowrd)){
                        loginSuccess =true;
                        System.out.println("\nLogin successful ! Welcome, " + parts[1]);
                        studentsMenu(parts);
                        break;
                    }

                }

            }
            reader.close();
        }catch (IOException e){
            System.out.println("Error reading students file :" +e.getMessage());
        }
        if(!loginSuccess){
            System.out.println("Login Failed. Invalid Credentials.");
        }
    }
    public static void viewStudents(){
        String fileName ="student";
        System.out.println("*************");
        System.out.println("List of students...... ");
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            int count =0;
            while ((line = reader.readLine()) != null){
                String [] parts = line.split(",");
                if(parts.length == 6 ){
                    count++;
                System.out.println("\n Student " + count);
                    System.out.println("Reg No: " + parts[0]);
                    System.out.println("Name: " + parts[1]);
                    System.out.println("Department: " + parts[2]);
                    System.out.println("Semester: " + parts[3]);
                    System.out.println("Username: " + parts[4]);
                    System.out.println("Password: " + parts[5]);

                }
            }
            reader.close();
            if(count==0){
                System.out.println("No students record found. ");
            }

        }catch (IOException e){
            System.out.println("Error reading students file: " + e.getMessage());
        }

    }
    public static void enrollStudentsInCourse(Scanner scanner){
         String file = "enrollments";
         System.out.print("Enter student's Registration number :");
         String regNo = scanner.nextLine().trim();
         System.out.print("Enter the courses names (separated by semicolon ';') :");
         String courses = scanner.nextLine();
         try {
             BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
             writer.write(regNo +","+courses);
             writer.newLine();
             writer.close();
             System.out.println("Students enrolled in courses successfully !");
         }catch (IOException e){
             System.out.println("Error writing enrollments: " + e.getMessage());
         }

    }
    public static void viewEnrolledCourses(String regNO){
        String file = "enrollments";
        System.out.println("\n --- Enrolled Courses ---");
        try{
            BufferedReader reader =new BufferedReader(new FileReader(file));
            String line;
            boolean found = false;
            while((line =reader.readLine()) !=null){
                String [] parts = line.split(",",2);
                if(parts.length == 2 && parts[0].equals(regNO)){
                    String[] courses =parts[1].split(";");
                    for (String course : courses){
                        System.out.println("-"+course);
                    }
                    found =true;
                    break;
                }

            }
        }catch (IOException e ){
            System.out.println("Error reading enrollments: " + e.getMessage());
        }


    }
    public static void topPerformerByCourse(Scanner input) {
        System.out.print("Enter course name: ");
        String courseName = input.nextLine().trim();

        String topRegNo = "";
        int topMarks = -1;

        try {
            BufferedReader marksReader = new BufferedReader(new FileReader("marks.txt"));
            String line;

            while ((line = marksReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5 && parts[1].equalsIgnoreCase(courseName)) {
                    int marks = Integer.parseInt(parts[2].trim());
                    if (marks > topMarks) {
                        topMarks = marks;
                        topRegNo = parts[0];
                    }
                }
            }
            marksReader.close();

            if (topRegNo.equals("")) {
                System.out.println("No marks found for this course.");
                return;
            }

            BufferedReader studentReader = new BufferedReader(new FileReader("student.txt"));
            String studentName = "Unknown";

            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2 && parts[0].equals(topRegNo)) {
                    studentName = parts[1];
                    break;
                }
            }
            studentReader.close();

            System.out.println("\nTop Performer in " + courseName + ":");
            System.out.println("Name: " + studentName);
            System.out.println("Reg No: " + topRegNo);
            System.out.println("Marks: " + topMarks);

        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }
    public static void searchStudents(Scanner input) {
        System.out.println("\n--- Search Students ---");
        System.out.println("1. By Semester");
        System.out.println("2. By Department");
        System.out.println("3. By Course");
        System.out.print("Enter your choice: ");
        String option = input.nextLine().trim();

        if (option.equals("1")) {
            System.out.print("Enter semester: ");
            String semester = input.nextLine().trim();
            searchStudentsByField(3, semester);
        } else if (option.equals("2")) {
            System.out.print("Enter department: ");
            String dept = input.nextLine().trim();
            searchStudentsByField(2, dept);
        } else if (option.equals("3")) {
            System.out.print("Enter course name: ");
            String course = input.nextLine().trim();
            searchStudentsByCourse(course);
        } else {
            System.out.println("Invalid choice.");
        }
    }
    public static void searchStudentsByField(int index, String value) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("student.txt"));
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > index && parts[index].equalsIgnoreCase(value)) {
                    System.out.println("RegNo: " + parts[0] + ", Name: " + parts[1] + ", Dept: " + parts[2] + ", Sem: " + parts[3]);
                    found = true;
                }
            }

            reader.close();
            if (!found) {
                System.out.println("No matching students found.");
            }

        } catch (IOException e) {
            System.out.println("Error reading student file.");
        }
    }
    public static void searchStudentsByCourse(String courseName) {
        List<String> matchedRegNos = new ArrayList<>();

        try {
            BufferedReader enrollReader = new BufferedReader(new FileReader("enrollments.txt"));
            String line;

            while ((line = enrollReader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                if (parts.length == 2) {
                    String regNo = parts[0];
                    String[] courses = parts[1].split(";");
                    for (String course : courses) {
                        if (course.trim().equalsIgnoreCase(courseName)) {
                            matchedRegNos.add(regNo);
                            break;
                        }
                    }
                }
            }
            enrollReader.close();

            if (matchedRegNos.isEmpty()) {
                System.out.println("No students enrolled in this course.");
                return;
            }

            BufferedReader studentReader = new BufferedReader(new FileReader("student.txt"));
            while ((line = studentReader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && matchedRegNos.contains(parts[0])) {
                    System.out.println("RegNo: " + parts[0] + ", Name: " + parts[1] + ", Dept: " + parts[2] + ", Sem: " + parts[3]);
                }
            }
            studentReader.close();

        } catch (IOException e) {
            System.out.println("Error reading files.");
        }
    }

    public static void manageCourses(Scanner input) {
        List<String> courses = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("courses.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                courses.add(line.trim());
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading courses: " + e.getMessage());
            return;
        }

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }

        System.out.println("\n------ Existing Courses ------");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + ". " + courses.get(i));
        }

        System.out.println("\n1. Update Course");
        System.out.println("2. Delete Course");
        System.out.print("Enter your choice: ");

        int action;
        try {
            action = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Must be a number.");
            return;
        }

        if (action == 1) {
            System.out.print("Enter course number to update: ");
            try {
                int index = Integer.parseInt(input.nextLine().trim()) - 1;
                if (index >= 0 && index < courses.size()) {
                    System.out.print("Enter new course name: ");
                    String newCourse = input.nextLine().trim();
                    courses.set(index, newCourse);
                    System.out.println("Course updated successfully.");
                } else {
                    System.out.println("Invalid course number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }

        } else if (action == 2) {
            System.out.print("Enter course number to delete: ");
            try {
                int index = Integer.parseInt(input.nextLine().trim()) - 1;
                if (index >= 0 && index < courses.size()) {
                    System.out.println("Deleting: " + courses.get(index));
                    courses.remove(index);
                    System.out.println("Course deleted successfully.");
                } else {
                    System.out.println("Invalid course number.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }

        } else {
            System.out.println("Invalid choice.");
            return;
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("courses.txt"));
            for (String course : courses) {
                writer.write(course);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to courses file: " + e.getMessage());
        }
    }
    public static void studentsMenu(String [] studentData){
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("\n**** Student Menu ****");
            System.out.println("1. View Profile ");
            System.out.println("2.View Enrolled Courses ");
            System.out.println("3.View Marks");
            System.out.println("4.Generate Report Card");
            System.out.println("5. Logout ");
            System.out.println("Enter your choice");
            int choice = scanner.nextInt();
            scanner.nextLine();
            if(choice==1){
                System.out.println("**** Your Profile ***** ");
                System.out.println("RegNo :" + studentData[0]);
                System.out.println("Name  : " + studentData[1]);
                System.out.println("Department :" + studentData[2]);
                System.out.println("Semester :" + studentData[3]);
                System.out.println("Username :" + studentData[4]);
            }else if (choice==2){
                viewEnrolledCourses(studentData[0]);
            } else if (choice==3) {
                viewMarks(studentData[0]);
            } else if (choice==4) {
                generateResultCard(studentData[0],studentData[1],studentData[3] );
            } else if (choice==5) {
                System.out.println("Logging out ..... ");
                break;
            }
            else{
                System.out.println("Enter a valid option .");
            }
        }
    }

    public static void viewMarks(String regNo){
        System.out.println("\n --- Your Marks ---");
        try {
            BufferedReader reader = new BufferedReader(new FileReader("marks.txt "));
            String line;
            boolean found = false;
            while ((line =reader.readLine())!=null){
                String[] parts = line.split(",");
                if(parts.length == 5 && parts[0].equals(regNo) ){
                    System.out.println("Course: " + parts[1]);
                    System.out.println("Marks:  "+ parts[2]+"/" + parts[3]);
                    System.out.println("Grade:" + parts[4] );
                    found=true;
                }
            }
            reader.close();
            if(!found){
                System.out.println("No Marks found for your registration number. ");
            }
        }catch (IOException e){
            System.out.println("Error Reading marks file :" + e.getMessage());
        }
    }
    public static void addStudent(Scanner scanner) {
        String fileName ="student";
       try{
           System.out.println("*********");
           System.out.println("Add Students");
           System.out.println("Enter your registration number :");
           String regNo = scanner.nextLine();
           System.out.println("Enter your name :");
           String name = scanner.nextLine();
           System.out.println("Enter your department :");
           String deptName = scanner.nextLine();
           System.out.println("Enter your semester :");
           String semester = scanner.nextLine();
           System.out.println("Set username for student :");
           String userName = scanner.nextLine();
           System.out.println("Set password for student :");
           String password = scanner.nextLine();
           //System.out.println("Writing to  ..... "+fileName);
                             BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true ));
                              writer.write(regNo + "," + name + "," + deptName +"," + semester + "," + userName + "," + password + ",");
                              writer.newLine();
                              writer.close();
                             System.out.println("Student added successfully!  ");


       }catch (IOException e){
           System.out.println("Error writing to student file:  " + e.getMessage());
       }
    }
    public static void generateResultCard(String regNo,String name,String semester){
        String fileName ="C:\\Desktop\\" +regNo+"-report.txt";
        String enroll = "enrollments";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName) );
            writer.write("-----Report Card-----");
            writer.newLine();
            writer.write("Name : " + name+"\n");
            writer.write("Reg No : " + regNo+"\n");
            writer.newLine();
            writer.write("semester: " + semester);
            writer.newLine();
            writer.write("----------------------");
            writer.newLine();
            writer.write("Enrolled Courses:");
            writer.newLine();
            BufferedReader enrollReader = new BufferedReader(new FileReader(enroll));
            String line;
            while((line =enrollReader.readLine()) !=null){
                String [] parts =line.split(",",2);
                if(parts.length==2 && parts[0].equals(regNo) ){
                    String[] courses =parts[1].split(";");
                    for (String course : courses){
                        writer.write("-" + course);
                        writer.newLine();
                    }
                    break;
                }
            }
            enrollReader.close();
            writer.write("----------------");
            writer.newLine();
            writer.write("Marks: ");
            writer.newLine();
            BufferedReader marksReader = new BufferedReader(new FileReader("marks.txt "));
            int total=0;
            int count=0;
            while ((line = marksReader.readLine()) !=null ){
                String[] parts = line.split(",");
                if(parts.length == 5 && parts[0].equals(regNo)){
                    writer.write(parts[1] + ":" + parts[2] +"/"+ parts[3]+ "\nGrade :" +parts[4] );
                    writer.newLine();
                   try{
                    int marks = Integer.parseInt(parts[2].trim());
                    total+=marks;
                    count++;
                   }catch (NumberFormatException ex){
                       System.out.println("Skipping Invalid  mark:" + parts[2]);
                   }
                }
            }
            marksReader.close();
            if (count>0){
                double percentage = total/(double)(count);
                writer.write("---------------------");
                writer.newLine();
                writer.write( String.format("Percentage: %.2f%%",percentage ));
            }
            else {
                writer.write("No Marks found ");
            }
            writer.close();
            System.out.println("Report Card saved to Desktop as : " + regNo + "-report.txt");
        }catch (IOException e){
            System.out.println("Error writing report card" + e.getMessage());
        }
    }

}