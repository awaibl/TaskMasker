package com.example.taskmasker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class ManageTaskActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TaskDao {

    //Übung 2
    EditText title;
    DatePicker date;
    String titleText;
    Spinner priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        //Übung 2
        title = (EditText) findViewById(R.id.title_text);
        titleText = title.getText().toString();

        date = findViewById(R.id.date_picker);

        priority = findViewById(R.id.priority_spinner);
        priority.setOnItemSelectedListener(this);

    }

    //Übung 3
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    //Übung 4
     @Override
     public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.task_title, R.id.task_date, R.id.task_priority:
                addTask(titleText, date, priority);
                return true;

            default:
                return super.onOptionsItemSelected(item); //returns false
        }
     }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public List<Task> getAllTasks() {
        return null;
    }

    @Override
    public void addTask(Task task) {

    }
}