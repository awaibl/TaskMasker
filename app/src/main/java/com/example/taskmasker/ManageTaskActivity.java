package com.example.taskmasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Database;
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
    int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        //Übung 4
        //database = MainActivity.database;

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task_db")
                .allowMainThreadQueries()
                .build();

        //Übung 2
        title = (EditText) findViewById(R.id.title_text);

        date = findViewById(R.id.date_picker);

        priority = findViewById(R.id.priority_spinner);
        TaskAdapter.itemId = 0;

        if(getIntent().hasExtra("id")){
            if(getIntent().getIntExtra("id",0) == 1){
                Task helpTaks = database.getTaskDao().getTask(getIntent().getIntExtra("taskID",0));
                taskId = helpTaks.id;
                Toast.makeText(this, ""+helpTaks.title, Toast.LENGTH_SHORT).show();

                ((EditText) findViewById(R.id.title_text)).setText(helpTaks.title);
                ((Spinner)findViewById(R.id.priority_spinner)).setSelection(helpTaks.priority);
                Calendar cal = Calendar.getInstance();
                cal.setTime(helpTaks.dueDate);
                ((DatePicker)findViewById(R.id.date_picker)).init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),null);
            }
        }
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
            if (getIntent().hasExtra("id") && getIntent().getIntExtra("id",0) == 1){

                Task task = new Task();

                task.title = title.getText().toString();
                task.dueDate = getDateFromDatePicker(date);
                task.priority = getIntPriority(priority);
                task.id = taskId;

                database.getTaskDao().updateTask(task);

            }else{

                //Übung 4
                Task task = new Task();

                task.title = title.getText().toString();
                task.dueDate = getDateFromDatePicker(date);
                task.priority = getIntPriority(priority);
                task.id = taskId;
                database.getTaskDao().addTask(task);
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);

        }
         //Simpler
            /*
            if(getIntent().hasExtra("id")){
                // Bleibpt bei beiden ja gleich, nur die Operation ist anders

                Task task = new Task();
                task.title = title.getText().toString();
                task.dueDate = getDateFromDatePicker(date);
                task.priority = getIntPriority(priority);
                // 0 ist für NeuSpeichern
                if(getIntent().getIntExtra("id",0) == 0){
                    database.getTaskDao().addTask(task);
                // 1 ist für Updaten
                }else if(getIntent().getIntExtra("id",0) == 0){
                    database.getTaskDao().updateTask(task);
                }else{
                    Toast.makeText(this, "Flasche Eingabe", Toast.LENGTH_SHORT).show();
                }
            }
             */



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
        /*
     public static void setDB(AppDatabase db){
        database = db;
     }

         */

    //Übung 6

}