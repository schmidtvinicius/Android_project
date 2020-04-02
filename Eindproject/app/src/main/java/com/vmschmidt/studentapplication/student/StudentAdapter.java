package com.vmschmidt.studentapplication.student;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.vmschmidt.studentapplication.R;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {

    private LayoutInflater studentInflater;

    public StudentAdapter(@NonNull Context context, @NonNull List<Student> objects) {
        super(context, R.layout.studentlist_item, objects);
        studentInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            Log.d("TEST", "Creating a new view for a position " + position);
            convertView = studentInflater.inflate(R.layout.studentlist_item, parent, false);
        }else{
            Log.d("TEST", "Reusing view for position " + position);
        }

        TextView fullNameView = convertView.findViewById(R.id.view_fullname);
        TextView emailView = convertView.findViewById(R.id.view_email);

        fullNameView.setText(getItem(position).getFullName());
        emailView.setText(getItem(position).getEmailAddress());

        return convertView;
    }
}
