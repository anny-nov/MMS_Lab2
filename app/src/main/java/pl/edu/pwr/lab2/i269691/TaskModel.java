package pl.edu.pwr.lab2.i269691;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TaskModel extends ViewModel {
    ArrayList<Task> tasks = new ArrayList<Task>();
    int current = 0;
    int images[] = {R.drawable.todo,R.drawable.email,R.drawable.phone,R.drawable.meeting};
}
