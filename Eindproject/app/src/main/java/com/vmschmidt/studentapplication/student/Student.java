package com.vmschmidt.studentapplication.student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

public class Student {

    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int studentNumber;
    private String classroom;
    private HashMap<String, Double> subjects;

    public Student(JSONObject jsonObject) throws JSONException {
        this.firstName = jsonObject.getString("First Name");
        this.middleName = jsonObject.getString("Middle Name");
        this.lastName = jsonObject.getString("Last Name");
        this.studentNumber = jsonObject.getInt("Student Number");
        this.emailAddress = jsonObject.getString("E-mail Address");
        this.classroom = jsonObject.getString("Classroom");
        subjects = new HashMap<>();
    }

    public Student(String firstName, String middleName, String lastName, String emailAddress, int studentNumber, String classroom) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.studentNumber = studentNumber;
        this.classroom = classroom;
        subjects = new HashMap<>();
    }

    public void addSubject(String subjectName, double grade){
        subjects.put(subjectName, grade);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        if(this.middleName.equalsIgnoreCase("")){
            return this.firstName + " " + this.lastName;
        }else if(!this.firstName.equalsIgnoreCase("")){
            return this.firstName + " " + this.middleName + " " + this.lastName;
        }
        return "";
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public String getClassroom(){
        if(this.classroom != null){
            return this.classroom;
        }
        return "Student has no known class (probably quit the study)";
    }

    public boolean hasSubject(String subjectName){
        return subjects.containsKey(subjectName);
    }

    public Double getGrade(String subjectName){
        return subjects.get(subjectName);
    }

    public Set<String> getSubjectKeys(){
        return subjects.keySet();
    }

    public void setClassroom(String classroom){
        this.classroom = classroom;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGrade(String subjectName, double newGrade){
        subjects.put(subjectName, newGrade);
    }

    public void removeSubject(String subjectName){
        subjects.remove(subjectName);
    }

}

