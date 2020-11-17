package com.example.taskmasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    }

    //Übung 3
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    //Übung 4/5
     @Override
     public boolean onOptionsItemSelected(MenuItem item){

        if(item.getTitle() != null && item.getTitle().equals("save")){

            //Übung 5
            if (getIntent().hasExtra("id")){

                Toast.makeText(this, title + " " + date + priority, Toast.LENGTH_SHORT).show();

                TextView text = findViewById(R.id.title_text);
                text.setText(title.getText().toString());

                Date dateDate = new Date();

                Calendar calendar = new GregorianCalendar();
                calendar.setTime(dateDate);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                date.updateDate(year, month, day);

                int index = getIntPriority(priority);
                priority.setSelection(index);

                Task task = new Task();

                database.getTaskDao().updateTask(task);

            }else{

                //Übung 4
                Task task = new Task();

                task.title = title.getText().toString();
                task.dueDate = getDateFromDatePicker(date);
                task.priority = getIntPriority(priority);

                database.getTaskDao().addTask(task);
            }

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


    //Übung 6

}