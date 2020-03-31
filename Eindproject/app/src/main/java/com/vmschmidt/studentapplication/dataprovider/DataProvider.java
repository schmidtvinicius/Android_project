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

    public static Map<String, Classroom> classrooms = null;
    public static Set<Integer> studentNumbers = null;
//    public static ArrayList<Classroom> classrooms = null;

    public static void addStudentList(List<JSONObject> studentListToAdd) {

        Log.d("TEST", "Current list size: " + studentListToAdd.size());

//        try {
//            for (JSONObject jsonObject : studentListToAdd) {
//                Student newStudent = new Student(jsonObject);
//                String newClassroomCode = newStudent.getClassroom();
//                Classroom classroom = checkExistingClassroom(newClassroomCode);
//                if(classroom == null){
//                    Classroom newClassroom = new Classroom(newClassroomCode);
//                    newClassroom.addStudent(newStudent);
//                    classrooms.add(newClassroom);
//                }else{
//                    classroom.addStudent(newStudent);
//                }
//            }
//            Log.d("CLASSROOMS", classrooms.toString());
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        try {
            for(JSONObject jsonObject : studentListToAdd){
                Student newStudent = new Student(jsonObject);
                String classroomCode = newStudent.getClassroom();
                Classroom classroom = classrooms.get(classroomCode);
                if(classroom == null){
                    classroom = new Classroom(classroomCode);
                    classroom.addStudent(newStudent);
                    classrooms.put(classroomCode, classroom);
                    studentNumbers.add(newStudent.getStudentNumber());
                }else{
                    classroom.addStudent(newStudent);
                    studentNumbers.add(newStudent.getStudentNumber());
                }
            }
        }catch(JSONException e){
            e.printStackTrace();
        }
////
//        for(JSONObject jsonObject : studentListToAdd){
//            try{
//                if(bla.isEmpty()) {
//                    Student newStudent = new Student(jsonObject);
//                    String classroomCode = jsonObject.getString("Classroom");
//                    Classroom newClassroom = new Classroom(classroomCode);
//                    newClassroom.addStudent(newStudent);
//                    bla.add(newClassroom);
//                }
//                Iterator<Classroom> classroomIterator = bla.iterator();
//                String classroomCode = jsonObject.getString("Classroom");
//                boolean classRoomExists = false;
//                Classroom existingClassroom = null;
//                while(classroomIterator.hasNext()  && !classRoomExists){
//                    existingClassroom = classroomIterator.next();
//                    if(existingClassroom.getClassCode().equals(classroomCode)){
//                        classRoomExists = true;
//                    }
//                }
//                //Classroom newClassroom = new Classroom(classroomCode);
//                Student newStudent = new Student(jsonObject);
//                if(classRoomExists){
//                    existingClassroom.addStudent(newStudent);
//                }else{
//                    existingClassroom = new Classroom(classroomCode);
//                    existingClassroom.addStudent(newStudent);
//                    bla.add(existingClassroom);
//                }
//                Log.d("ADD STUDENT", "Student " + newStudent.getFullName() + " added to " + classroomCode);
//            }catch (JSONException e){
//                e.printStackTrace();
//                Log.d("JSONException", e.getMessage());
//            }
//        }
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

    public static Set<String> getKeys(Context context){

        if(classrooms == null){
            classrooms = new HashMap<>();
            studentNumbers = new HashSet<>();
            readJSONResourceFile(context);
            addStudentList(readJSONResourceFile(context));
        }

        Set<String> classroomCodes = new HashSet<>();

        classroomCodes.addAll(classrooms.keySet());
        return classroomCodes;
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
