package com.vmschmidt.studentapplication.dataprovider;

import android.content.Context;
import android.util.Log;

import com.vmschmidt.studentapplication.R;
import com.vmschmidt.studentapplication.classroom.Classroom;
import com.vmschmidt.studentapplication.student.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class DataProvider {

    private static Map<String, Classroom> classrooms = null;
    private static HashMap<Integer, Student> studentNumbers = null;

    private static void addStudentList(List<JSONObject> studentListToAdd) {

        Log.d("TEST", "Current list size: " + studentListToAdd.size());
        try {
            for(JSONObject jsonObject : studentListToAdd){
                Student newStudent = new Student(jsonObject);
                String classroomCode = newStudent.getClassroom();
                Classroom classroom = classrooms.get(classroomCode);
                if(classroom == null){
                    classroom = new Classroom(classroomCode);
                    classroom.addStudent(newStudent);
                    classrooms.put(classroomCode, classroom);
                    studentNumbers.put(newStudent.getStudentNumber(), newStudent);
                }else{
                    classroom.addStudent(newStudent);
                    studentNumbers.put(newStudent.getStudentNumber(), newStudent);
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public static void addStudent(Student student){
        studentNumbers.put(student.getStudentNumber(), student);
        Classroom classroom = classrooms.get(student.getClassroom());
        classroom.addStudent(student);
    }

    public static void addClassroom(String classroomCode){
        Classroom newClassroom = new Classroom(classroomCode);
        classrooms.put(classroomCode, newClassroom);
    }

    public static Classroom getClassroom(String classroomCode){
        return classrooms.get(classroomCode);
    }

    public static Student findStudent(int studentNumber){
        return studentNumbers.get(studentNumber);
    }

    public static void removeClassroom(String classroomCode){
        classrooms.remove(classroomCode);
        ArrayList<Student> studentsToRemove = new ArrayList<>();
        for(Integer studentNumber : studentNumbers.keySet()){
            if(studentNumbers.get(studentNumber).getClassroom().equals(classroomCode)){
                studentsToRemove.add(studentNumbers.get(studentNumber));
            }
        }
        for(Student studentToRemove : studentsToRemove){
            studentNumbers.remove(studentToRemove.getStudentNumber());
        }
    }

    public static void removeStudent(Student student){
        String classroomCode = student.getClassroom();
        Classroom classroom = classrooms.get(classroomCode);
        classroom.removeStudent(student);
        studentNumbers.remove(student.getStudentNumber());
    }

    public static Classroom checkExistingClassroom(String codeToCheck){

        for(String classroomCode : classrooms.keySet()){
            if(classroomCode.equals(codeToCheck)){
                return classrooms.get(classroomCode);
            }
        }
        return null;
    }

    public static Set<String> getClassroomsKeys(Context context){

        if(classrooms == null){
            classrooms = new HashMap<>();
            studentNumbers = new HashMap<>();
            readJSONResourceFile(context);
            addStudentList(readJSONResourceFile(context));
        }

        return classrooms.keySet();
    }

    public static Set<Integer> getStudentKeys(Context context){
        if(studentNumbers == null){
            classrooms = new HashMap<>();
            studentNumbers = new HashMap<>();
            readJSONResourceFile(context);
            addStudentList(readJSONResourceFile(context));
        }
        return studentNumbers.keySet();
    }

    private static ArrayList<JSONObject> readJSONResourceFile(Context context){

        InputStream inputStream = context.getResources().openRawResource(R.raw.current_students);
        Scanner scanner = new Scanner(inputStream);

        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()){
            stringBuilder.append(scanner.nextLine());
        }
        try{
            JSONArray jsonArray = new JSONArray(stringBuilder.toString());
            ArrayList<JSONObject> jsonObjects = new ArrayList<>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                jsonObjects.add(jsonObject);
            }
            return jsonObjects;
        }catch (JSONException e){
            e.printStackTrace();
            return null;
        }
    }
}
