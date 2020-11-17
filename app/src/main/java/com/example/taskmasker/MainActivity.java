package com.example.taskmasker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AppDatabase database;
    RecyclerView taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "task_db")
                .allowMainThreadQueries()
                .build();

        if(database.getTaskDao().getAllTasks().size() == 0){
            for(int i = 0; i < 20; i++){
                Task t = new Task();
                t.title = "Task title " + i;
                t.dueDate = Calendar.getInstance().getTime();
                t.priority = (int) Math.ceil(Math.random() * 3); //1 = high, 2 = medium, 3 = keines
                database.getTaskDao().addTask(t);
            }
        }

        taskList = findViewById(R.id.task_list);
        taskList.setLayoutManager(new LinearLayoutManager(this));
        taskList.setAdapter(new TaskAdapter(database.getTaskDao()));

        //Übung 1 - Trennlinie
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        taskList.addItemDecoration(itemDecoration);

    }

    //Übung 1
    @Override
    public void onClick(View v) {

        Intent intent = new Intent(this, ManageTaskActivity.class);

        //Übung 5
        //intent.putExtra("id", getTaskId());

        startActivity(intent);

    }

    public void onListClick(View v){
        Intent intent = new Intent(this, ManageTaskActivity.class);

        intent.putExtra("id", getTaskId());

        //Toast.makeText(this, getTaskId(), Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }

}