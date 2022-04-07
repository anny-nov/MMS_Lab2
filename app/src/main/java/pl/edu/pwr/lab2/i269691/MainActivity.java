package pl.edu.pwr.lab2.i269691;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner TypeChoise;
    private MyAdapter myAdapter;
    public TaskModel model;
    public CardView CardView;
    private Button button;
    private Button cancel_button;
    private EditText new_title;
    private EditText new_description;
    private EditText new_duedate;
    private String type;
    RecyclerView recyclerView;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SIZE = "SizeArray";
    public static final String TASK = "TaskId";
    public static final String CURR = "Current";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = new ViewModelProvider(this).get(TaskModel.class);
        CardView = findViewById(R.id.adding);
        button = (Button) findViewById(R.id.button);
        cancel_button = (Button) findViewById(R.id.cancel_button);
        new_title = (EditText) findViewById(R.id.enter_title);
        new_description = (EditText) findViewById(R.id.enter_description);
        new_duedate = (EditText) findViewById(R.id.enter_date);
        TypeChoise = (Spinner) findViewById(R.id.TypeChoise);

        CardView.setVisibility(View.GONE);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TypeChoise.setAdapter(adapter);
        TypeChoise.setOnItemSelectedListener(this);
        LoadData();
        initRecyclerView();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = new_title.getText().toString();
                String desc = new_description.getText().toString();
                String date = new_duedate.getText().toString();
                Task tmp = new Task(title,desc,type,date);
                model.tasks.add(tmp);
                CardView.setVisibility(View.GONE);
                SaveData();
                myAdapter.setItem(tmp);
            }

        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CardView.setVisibility(View.GONE);
            }
        });
    }

    public void SaveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(CURR,model.current);
        editor.putInt(SIZE,model.tasks.size());
        for(int i=0;i<model.tasks.size();i++) {
            editor.putString("Title" + i, model.tasks.get(i).getTitle());
            editor.putString("Description" + i, model.tasks.get(i).getDescription());
            editor.putString("Type" + i, model.tasks.get(i).getType());
            editor.putString("DueTime" + i, model.tasks.get(i).getDueDate().toString());
            editor.putBoolean("Status" + i, model.tasks.get(i).isStatus());
        }
        editor.apply();
    }
    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int s = sharedPreferences.getInt(SIZE, 0);
        model.current = 0;
        if (s == 0) return;
        for (int i = 0; i < s; i++) {
            Task tmp = new Task(sharedPreferences.getString("Title" + i, ""), sharedPreferences.getString("Description" + i, ""), sharedPreferences.getString("Type" + i, ""), sharedPreferences.getString("DueTime" + i, ""));
            tmp.setStatus(sharedPreferences.getBoolean("Status" + i, true));
            model.tasks.add(tmp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                CardView.setVisibility(View.VISIBLE);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemSelected(@NonNull AdapterView<?> adapterView, View view, int i, long l) {
        type = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    private void initRecyclerView(){
        MyAdapter.OnTaskClickListener onTaskClickListener = new MyAdapter.OnTaskClickListener() {
            @Override
            public void OnTaskClick(Task task) {
                Intent intent = new Intent(MainActivity.this,TaskInfoActivity.class);
                model.current = task.getId();
                SaveData();
                startActivity(intent);
            }
        };
        recyclerView = (RecyclerView) findViewById(R.id.list);
        myAdapter = new MyAdapter(this,model.tasks,model.images,onTaskClickListener);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}