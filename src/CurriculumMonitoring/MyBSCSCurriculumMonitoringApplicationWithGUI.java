package CurriculumMonitoring;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyBSCSCurriculumMonitoringApplicationWithGUI extends JFrame{
    private static Scanner fileReader;
    private Scanner kbd = new Scanner(System.in);
    private PrintWriter outputStream;
    private String source = "BSCSCurriculumData.txt";
    private String output = "BSCSCurriculumData.txt";

    private JButton firstOptionB, secondOptionB, thirdOptionB, fourthOptionB, exitB;
    private JLabel topText;
    private JFrame frame;
    private JList list1;

    private firstOptionButtonHandler firstOptionHandler;
    private secondOptionButtonHandler secondOptionHandler;
    private thirdOptionButtonHandler thirdOptionHandler;
    private fourthOptionButtonHandler fourthOptionHandler;
    private exitButtonHandler exitButtonHandler;

    private static final int WIDTH = 500;
    private static final int HEIGHT = 300;

    /**
     * Default constructor
     */
    public MyBSCSCurriculumMonitoringApplicationWithGUI(){
        //top text of the program
        topText = new JLabel("My Checklist Management", SwingConstants.CENTER);

        // creates buttons
        firstOptionB = new JButton("Show subjects for each school term");
        firstOptionHandler = new firstOptionButtonHandler();
        firstOptionB.addActionListener(firstOptionHandler);

        secondOptionB = new JButton("Show subjects with grades for each term");
        secondOptionHandler = new secondOptionButtonHandler();
        secondOptionB.addActionListener(secondOptionHandler);

        thirdOptionB = new JButton("Enter Ggrades for subjects recently finished");
        thirdOptionHandler = new thirdOptionButtonHandler();
        thirdOptionB.addActionListener(thirdOptionHandler);

        fourthOptionB = new JButton("Edit a course");
        fourthOptionHandler = new fourthOptionButtonHandler();
        fourthOptionB.addActionListener(fourthOptionHandler);

        exitB = new JButton("Exit");
        exitButtonHandler = new exitButtonHandler();
        exitB.addActionListener(exitButtonHandler);

        frame = new JFrame();
        setTitle("Group 5 Curriculum Monitoring Application"); // sets the title of the window
        Container pane = getContentPane();
        pane.setLayout(new GridLayout(6,1)); // sets the layout for the program
        // adds components of the GUI
        pane.add(topText);
        pane.add(firstOptionB);
        pane.add(secondOptionB);
        pane.add(thirdOptionB);
        pane.add(fourthOptionB);
        pane.add(exitB);
        setSize(WIDTH, HEIGHT); // sets the initial size of the GUI
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE); // sets the function of the red 'x' button to exit the program
    } /// end of constructor

    /**
     * Opens the firstOptionWindow
     *
     * 1.) Listens for the button firstOptionB to be clicked
     * 2.) Initiates a new window
     */
    private class firstOptionButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try {
                new firstOptionWindow();
            } catch (Exception exception){
                exception.printStackTrace();
            }

        }
    }

    /**
     * Outputs text in the terminal
     *
     * 1.) Listens for secondOptionB to be clicked
     * 2.) Prints "second option" in the terminal
     *
     */
    private class secondOptionButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("second option");
        }
    }

    /**
     * Outputs text in the terminal
     *
     * 1.) Listens for the thirdOptionB to be clicked
     * 2.) Prints "third option" in the terminal
     *
     */
    private class thirdOptionButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("third option");
        }
    }

    /**
     * Outputs text in the terminal
     *
     * 1.) Listens for fourthOptionB to be clicked
     * 2.) Prints "fourth option" in the terminal
     */
    private class fourthOptionButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.out.println("fourth option");
        }
    }

    /**
     * Exits the program
     */
    private class exitButtonHandler implements ActionListener{
        public void actionPerformed(ActionEvent e){ System.exit(0); }
    }

    /**
     * Displays the contents of the ArrayList in a Scroll Panel
     */
    private class firstOptionWindow{
        public firstOptionWindow() throws Exception {
            JPanel subjectsPanel = new JPanel();
            JScrollPane scrollPane = new JScrollPane();
            topText = new JLabel("Year Level/Term/Course/Title/Units/Grade", SwingConstants.LEFT);

            ArrayList<Course> subjectsList = readData(source);
            // converts the ArrayList subjectList into a regular Array to input inside a JList
            JList<Course> list = new JList<Course>(subjectsList.toArray(new Course[subjectsList.size()]));

            scrollPane.setViewportView(list);
            list.setLayoutOrientation(JList.VERTICAL);
            subjectsPanel.setLayout(new GridLayout(2,1));
            subjectsPanel.add(topText);
            subjectsPanel.add(scrollPane);
            frame.setTitle("Courses Per Term");

            frame.add(subjectsPanel);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    }


    /**
     * This method shows the courses per term with grades
     *
     * @param courses
     * @param maxYear
     * @param maxTerm
     * @throws Exception
     */
    public void showCoursesPerTermWithGrades(ArrayList<Course> courses, int maxYear, int maxTerm) throws Exception {
        double averageGrade = 0.0;
        for (int year = 1; year <= maxYear; year++) {
            for (int term = 1; term <= maxTerm; term++) {
                if (term != maxTerm || year != maxYear) {
                    double total = 0;
                    int counter = 0;
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%5s%30s%n", "Year = " + yearInWord(year) + " year", "Term = " + termInWord(term));
                    System.out.printf("%-5s%23s%57s%15s%17s%n", "Course No", "Descriptive title", "Units", "Grades","Remark");
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                    for (int index = 0; index < courses.size(); index++) {
                        if (courses.get(index).getYearLevel() == year && courses.get(index).getTerm() == term) {
                            if (courses.get(index).getGrade() == 0) {
                                System.out.printf("%-15s%-70s%-10.1f%-15s%n", courses.get(index).getNumber(), courses.get(index).getTitle(), courses.get(index).getUnits(), "Not Yet Taken");
                            } else {
                                System.out.printf("%-15s%-70s%-15.1f%-15d%-10s%n", courses.get(index).getNumber(), courses.get(index).getTitle(), courses.get(index).getUnits(), courses.get(index).getGrade(), getRemark(courses.get(index).getGrade()));
                                total += courses.get(index).getGrade();
                                counter++;
                            }
                        }
                    }
                    averageGrade = total / counter;
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                    System.out.printf("%-5s%95.2f%n", "Average: ", averageGrade);
                    System.out.println("-------------------------------------------------------------------------------------------------------------------------");
                    System.out.println();
                    System.out.print("Press enter key to see courses for the next term.");
                    kbd.nextLine();
                }
            }
        }
    }

    /**
     * This method counts the number of lines in the file
     *
     * @param file
     * @return count
     * @throws FileNotFoundException
     * @throws Exception
     */
    public int countLines(String file) throws FileNotFoundException, Exception {
        int count = 0;
        fileReader = new Scanner(new File(file));
        while (fileReader.hasNextLine()) {
            fileReader.nextLine();
            count += 1;
        }
        fileReader.close();
        return count;
    }

    /**
     * This method sorts the list of the data file
     *
     * Algorithm:
     * 1.) The program reads the file
     * 2.) Splits the values using the given delimiter
     * 3.) Store the different values in different variables
     * 4.) Create a new data and add it to the ArrayList
     * 5. If an error occurred, print a message that displays the location where the error happened
     *
     * @param file
     * @return listOfCourse
     * @throws Exception
     */
    public ArrayList<Course> readData(String file) throws Exception {
        ArrayList<Course> listOfCourses = new ArrayList<>(countLines(file));
        String lineOfText = "";
        int lineNumber = 0;
        try {
            fileReader = new Scanner(new File(file));
            while (fileReader.hasNextLine()) {
                lineNumber += 1;
                lineOfText = fileReader.nextLine();
                String[] temp = lineOfText.split("/");
                Course data = new Course(temp[2], temp[3], Double.parseDouble(temp[4]), Integer.parseInt(temp[0]), Integer.parseInt(temp[1]), Integer.parseInt(temp[5]));
                listOfCourses.add(data);
            }
        } catch (Exception exc) {
            System.out.println("There is a problem in the data read from line " + lineNumber + " of the data file.");
            System.out.println("The program needed to be terminated.");
            System.out.println("Please fix the problem in the data file.");
            exc.printStackTrace();
        }
        System.out.println(listOfCourses);
        return listOfCourses;
    }

    /**
     * This method returns the equivalent value of the year in words
     *
     * @param year
     * @return yearInWords
     */
    public String yearInWord(int year) {
        String yearInWord = "";
        switch (year) {
            case 1:
                yearInWord = "First";
                break;
            case 2:
                yearInWord = "Second";
                break;
            case 3:
                yearInWord = "Third";
                break;
            case 4:
                yearInWord = "Fourth";
                break;
        }
        return yearInWord;
    }

    /**
     * This method returns the equivalent value of the term in words
     *
     * @param term
     * @return termInWord
     */
    public String termInWord(int term) {
        String termInWord = "";
        switch (term) {
            case 1:
                termInWord = "First Semester";
                break;
            case 2:
                termInWord = "Second Semester";
                break;
            case 3:
                termInWord = "Short Term";
                break;
        }
        return termInWord;
    }

    /**
     * This method finds the course number from the file that matches the search term
     *
     * Algorithm:
     * 1. Search the file with the search word
     * 2. Returns true if the course number exist
     * 3. Returns false if coyrse number does not exist
     *
     * @param courses
     * @param searchCourse
     * @return courseFound
     */
    public Boolean courseExist(ArrayList<Course> courses, String searchCourse) {
        boolean courseFound = false;
        for (int index = 0; index < courses.size(); index++) {
            if (courses.get(index).getNumber().equalsIgnoreCase(searchCourse)) {
                courseFound = true;
            }
        }
        return courseFound;
    }

    /**
     * This method allows user to edit the course
     *
     * Algorithm:
     * 1. Search for the course that matches the key word in the file.
     * 2. If found, store the location in getLocation variable.
     * 3. Invoke editCourseNumber method to let the user input the new course number
     * 4. Invoke editCourseDesc method to let the user input the new course description
     * 5. Invoke editUnits method to let the user input the new course number
     * 6. Creates a new file with the updated course
     *
     * @param courses
     * @param searchCourse
     */
    public void editCourse(ArrayList<Course> courses, String searchCourse) {
        boolean courseFound = false;
        int getLocation = 0;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(output));
            for (int index = 0; index < courses.size(); index++) {
                if (courses.get(index).getNumber().equalsIgnoreCase(searchCourse)) {
                    courseFound = true;
                    if (courseFound) {
                        getLocation = index;
                    }
                }
            }
            for (int index = 0; index < courses.size(); index++) {
                if (index == getLocation) {
                    courses.get(index).setNumber(editCourseNumber());
                    courses.get(index).setTitle(editCourseDesc());
                    courses.get(index).setUnits(editUnits());
                    printWriter.println(courses.get(index).toString());
                } else {
                    printWriter.println(courses.get(index).toString());
                }
            }
            fileReader.close();
            printWriter.flush();
            printWriter.close();
            System.out.print("Updated successfully. Press Enter to Continue");
            kbd.nextLine();
            main(null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method allows user to edit the course grade
     *
     * Algorithm:
     * 1. Search for the course that matches the key word in the file.
     * 2. If found, store the location in getLocation variable.
     * 3. Invoke editGrades method to let the user input the grade for the course
     * 4. Creates a new file that replaces the old file with the updated grade for the course searched.
     *
     * @param courses
     * @param searchCourse
     * @throws IOException
     */
    public void editCourseGrade(ArrayList<Course> courses, String searchCourse) throws IOException {
        boolean courseFound = false;
        int getLocation = 0;
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(output));
            for (int index = 0; index < courses.size(); index++) {
                if (courses.get(index).getNumber().equalsIgnoreCase(searchCourse)) {
                    courseFound = true;
                    if (courseFound) {
                        getLocation = index;
                    }
                }
            }
            for (int index = 0; index < courses.size(); index++) {
                if (index == getLocation) {
                    courses.get(index).setGrade(updateGrade());
                    printWriter.println(courses.get(index).toString());
                } else {
                    printWriter.println(courses.get(index).toString());
                }
            }
            fileReader.close();
            printWriter.flush();
            printWriter.close();
            System.out.print("Updated successfully. Press Enter to Continue");
            kbd.nextLine();
            main(null);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * This method sets the inputted course number as newCourseNumber
     *
     * Algorithm:
     * Ask the user to input a new course number
     *
     * @return newCourseNumber
     */
    public String editCourseNumber() {
        String newCourseNumber = "";
        System.out.print("Enter the new course number: ");
        newCourseNumber = kbd.nextLine();
        return newCourseNumber;
    }

    /**
     * This method sets the inputted course description as newCourseDesc
     *
     * Algorithm:
     * Ask the user to input a new course description
     *
     * @return newCourseDesc
     */
    public String editCourseDesc() {
        String newCourseDesc = "";
        System.out.print("Enter the new course description: ");
        newCourseDesc = kbd.nextLine();
        return newCourseDesc;
    }

    /**
     * This method sets the inputted number of units to newUnits
     *
     * Algorithm:
     * Ask the user to input a number to be used as newUnits
     *
     * @return newUnits
     */
    public double editUnits() {
        double newUnits = 0.0;
        System.out.print("Enter how many units is in the course: ");
        newUnits = Double.parseDouble(kbd.nextLine());
        return newUnits;
    }

    /**
     * This method involves exception to determine if the inputted updateGrade is a valid input
     *
     * Algorithm:
     * 1.) Asks the user for input grade
     * 2.) If the inputted grade is greater than 99 or less than 65, print message.
     * 3.) If the inputted grade is another type of variable, print message.
     *
     * @return updatedGrade
     */
    public int updateGrade() {
        int updatedGrade = 0;
        do {
            try {
                System.out.print("Input the grade for the course: ");
                updatedGrade = Integer.parseInt(kbd.nextLine());
                if (updatedGrade > 99 || updatedGrade < 65) {
                    System.out.print("The only valid grades are from 65 to 99.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please Try again.");
                exception.printStackTrace();
            }
        } while (updatedGrade > 99 || updatedGrade < 65);
        return updatedGrade;
    }

    /**
     * This method returns whether the user passed or failed a certain subject
     *
     * 1. If the user's grade for a subject is greater than or equal to 75, return passed
     * 2. If the user's grade for a subject is lower than 75, return failed
     * @param grades
     * @return remark
     */
    public String getRemark(double grades){
        String remark = "";
        if (grades >= 75){
            remark = "Passed";
        } else if (grades < 75){
            remark = "Failed";
        }
        return remark;
    }

    public static void main(String[] args) { new MyBSCSCurriculumMonitoringApplicationWithGUI(); }
}
