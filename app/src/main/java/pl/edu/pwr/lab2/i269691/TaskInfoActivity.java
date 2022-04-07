package pl.edu.pwr.lab2.i269691;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskInfoActivity extends AppCompatActivity {
    public static final String TASK = "TaskId";
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String SIZE = "SizeArray";
    public static final String CURR = "Current";
    public static String not_done = "Status: In Progress";
    public static String done = "Status: Done";

    private ImageView img;
    private TextView title;
    private TextView date;
    private TextView status;
    private TextView description;
    public TaskModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_info);
        model = new ViewModelProvider(this).get(TaskModel.class);
        LoadData();

        img = (ImageView) findViewById(R.id.image_task);
        title = (TextView) findViewById(R.id.title_info);
        date = (TextView) findViewById(R.id.date_info);
        status = (TextView) findViewById(R.id.status_info);
        description = (TextView) findViewById(R.id.description_info);

        SetData();
    }

    public void LoadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        int s = sharedPreferences.getInt(SIZE, 0);
        model.current = sharedPreferences.getInt(CURR,0);
        if (s == 0) return;
        for (int i = 0; i < s; i++) {
            Task tmp = new Task(sharedPreferences.getString("Title" + i, ""), sharedPreferences.getString("Description" + i, ""), sharedPreferences.getString("Type" + i, ""), sharedPreferences.getString("DueTime" + i, ""));
            tmp.setStatus(sharedPreferences.getBoolean("Status" + i, true));
            model.tasks.add(tmp);
        }
    }

    private void SetData(){
        title.setText(model.tasks.get(model.current).getTitle());
        date.setText(model.tasks.get(model.current).getDueDate());
        switch(model.tasks.get(model.current).getType())
        {
            case "ToDo":
                img.setImageResource(model.images[0]);
                break;
            case "E-mail":
                img.setImageResource(model.images[1]);
                break;
            case "Phone Call":
                img.setImageResource(model.images[2]);
                break;
            case "Meeting":
                img.setImageResource(model.images[3]);
                break;
        }
        if(model.tasks.get(model.current).isStatus()) status.setText(not_done);
        else status.setText(done);
        description.setText(model.tasks.get(model.current).getDescription());
    }
}