package com.vmschmidt.studentapplication.classroom;

import android.provider.ContactsContract;

import com.vmschmidt.studentapplication.dataprovider.DataProvider;
import com.vmschmidt.studentapplication.student.Student;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Classroom {

    private String classCode;
    private ArrayList<Student> studentList;

    public Classroom(String classCode){
        this.classCode = classCode;
        studentList = new ArrayList<>();
    }

    public boolean addStudent(Student student){
        DataProvider.studentNumbers.add(student.getStudentNumber());
        return studentList.add(student);
    }

    public void deleteStudentList(){
        studentList.removeAll(studentList);
        studentList = null;
    }

    public void removeStudent(Student student){
        studentList.remove(student);
    }

    public int totalStudents(){
        if(studentList != null){
            return studentList.size();
        }
        return 0;
    }

    public void addStudentList(ArrayList<Student> studentsToAdd){
        this.studentList.addAll(studentsToAdd);
    }

    public String getClassCode(){
        return this.classCode;
    }

    public ArrayList<Student> getStudentList(){
        return this.studentList;
    }

}
