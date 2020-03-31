package com.vmschmidt.studentapplication.student;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

public class Student {

    private String firstName;
    private String middleName;
    private String lastName;
    private String emailAddress;
    private int studentNumber;
    private String classroom;
    private ArrayList<Student> friends;

    public Student(JSONObject jsonObject) throws JSONException {
        this.firstName = jsonObject.getString("First Name");
        this.middleName = jsonObject.getString("Middle Name");
        this.lastName = jsonObject.getString("Last Name");
        this.studentNumber = jsonObject.getInt("Student Number");
        this.emailAddress = jsonObject.getString("E-mail Address");
        this.classroom = jsonObject.getString("Classroom");
        this.friends = new ArrayList<>();
    }

    public Student(String firstName, String middleName, String lastName, String emailAddress, int studentNumber, String classroom) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.studentNumber = studentNumber;
        this.classroom = classroom;
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

    public JSONObject toJSONObject() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("First Name", this.firstName);
        jsonObject.put("Middle Name", this.middleName);
        jsonObject.put("Last Name", this.lastName);
        jsonObject.put("Student Number", this.studentNumber);
        jsonObject.put("E-mail Address", this.emailAddress);
        jsonObject.put("Classroom", this.classroom);

        return jsonObject;
    }

    @Override
    public String toString(){
        return "\n\n" + getFullName() + ":" + "\n\tStudent number: " + this.studentNumber + "\n\tE-mail address: " + this.emailAddress +
                "\n\tClass: " + getClassroom();
    }
}

