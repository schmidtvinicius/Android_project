package com.vmschmidt.studentapplication.student;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

public class Student implements Parcelable {

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
        this.friends = new ArrayList<>();
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

    public ArrayList<Student> getFriends() {
        return friends;
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

    public void addFriend(Student friendToAdd){
        if(!friends.contains(friendToAdd)){
            friends.add(friendToAdd);
        }
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

//    @Override
//    public String toString(){
//        return "\n\n" + getFullName() + ":" + "\n\tStudent number: " + this.studentNumber + "\n\tE-mail address: " + this.emailAddress +
//                "\n\tClass: " + getClassroom();
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.middleName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailAddress);
        dest.writeInt(this.studentNumber);
        dest.writeString(this.classroom);
        dest.writeList(this.friends);
    }

    protected Student(Parcel in) {
        this.firstName = in.readString();
        this.middleName = in.readString();
        this.lastName = in.readString();
        this.emailAddress = in.readString();
        this.studentNumber = in.readInt();
        this.classroom = in.readString();
        this.friends = new ArrayList<Student>();
        in.readList(this.friends, Student.class.getClassLoader());
    }

    public static final Parcelable.Creator<Student> CREATOR = new Parcelable.Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}

