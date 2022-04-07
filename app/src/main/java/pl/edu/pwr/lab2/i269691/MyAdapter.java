package pl.edu.pwr.lab2.i269691;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private OnTaskClickListener onTaskClickListener;
    private ArrayList<Task> added_tasks = new ArrayList<Task>();
    /*private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> duedate = new ArrayList<String>();
    private ArrayList<String> type = new ArrayList<String>();
    private ArrayList<String> description = new ArrayList<String>();
    private ArrayList<Boolean> status = new ArrayList<Boolean>(); */
    private int images[];
    Context context;

    public MyAdapter(OnTaskClickListener onTaskClickListener){
        this.onTaskClickListener = onTaskClickListener;
    }
    public MyAdapter(Context ct, ArrayList<Task> tasks,int img[],OnTaskClickListener onTaskClickListener){
        context = ct;
        images = img;
        added_tasks = tasks;
        this.onTaskClickListener = onTaskClickListener;
        /* for (int i=0;i<tasks.size();i++)
        {
            title.add(tasks.get(i).getTitle());
            duedate.add(tasks.get(i).getDueDate());
            type.add(tasks.get(i).getType());
            status.add(tasks.get(i).isStatus());
            description.add(tasks.get(i).getDescription());
        }*/

    }
    public void setItem(Task task) {
        added_tasks.add(task);
        /*title.add(task.getTitle());
        duedate.add(task.getDueDate());
        type.add(task.getType());
        status.add(task.isStatus());*/
        notifyDataSetChanged();
    }
    public void deleteItem(Task item)
    {
        int i = added_tasks.indexOf(item);
        //title.remove(i);
        added_tasks.remove(i);
        //duedate.remove(i);
        //type.remove(i);
        //status.remove(i);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(added_tasks.get(position).getTitle());
        holder.due.setText(added_tasks.get(position).getDueDate());
        switch(added_tasks.get(position).getType())
        {
            case "ToDo":
                holder.typeimg.setImageResource(images[0]);
                break;
            case "E-mail":
                holder.typeimg.setImageResource(images[1]);
                break;
            case "Phone Call":
                holder.typeimg.setImageResource(images[2]);
                break;
            case "Meeting":
                holder.typeimg.setImageResource(images[3]);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return added_tasks.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, due;
        ImageView typeimg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            due = itemView.findViewById(R.id.task_date);
            typeimg = itemView.findViewById(R.id.Type_img);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task chosen = added_tasks.get(getLayoutPosition());
                    onTaskClickListener.OnTaskClick(chosen);
                }
            });
        }
    }

    public interface OnTaskClickListener {
        void OnTaskClick(Task task);
    }
}
