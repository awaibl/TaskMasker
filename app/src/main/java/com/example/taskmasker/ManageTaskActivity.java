package com.example.taskmasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.room.Room;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class ManageTaskActivity extends AppCompatActivity {

    //Übung 2
    EditText title;
    DatePicker date;
    Spinner priority;

    //Übung 4
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        //Übung 4
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task_db")
                .allowMainThreadQueries()
                .build();

        //Übung 2
        title = (EditText) findViewById(R.id.title_text);

        date = findViewById(R.id.date_picker);

        priority = findViewById(R.id.priority_spinner);

        //Übung 5
        if(getIntent().hasExtra("id")){
            update();
        }

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

        if(item.getTitle() != null && item.getTitle().equals("save")){

            Task task = new Task();

            task.title = title.getText().toString();
            task.dueDate = getDateFromDatePicker(date);
            task.priority = getIntPriority(priority);
            database.getTaskDao().addTask(task);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
     }

     //Übung 4
     public Date getDateFromDatePicker(DatePicker date){

       int day = date.getDayOfMonth();
       int month = date.getMonth();
       int year = date.getYear();

       Calendar dateConverted = Calendar.getInstance();
       dateConverted.set(year, month, day);

       return dateConverted.getTime();

     }

     //Übung 4
     public int getIntPriority(Spinner priority){

        String priorityString = priority.getSelectedItem().toString();

        int priorityInt;

        if(priorityString.equals("Medium")){
            priorityInt = 2;
        }else if(priorityString.equals("Low")){
            priorityInt = 3;
         }else{
            priorityInt = 1;
        }

        return priorityInt;

     }

     //Übung 5
    public void update(){

        Task task = new Task();

        Intent intent = new Intent();
        int id = intent.getIntExtra("id",0);

        if(id == database.getTaskDao().getId()){
            TextView text = findViewById(R.id.title_text);
            text.setText(title.getText().toString());

            date = findViewById(R.id.date_picker);

            priority = findViewById(R.id.priority_spinner);
            
        }

        database.getTaskDao().updateTask(task);
    }

    //Übung 6


}