package pl.edu.pwr.lab2.i269691;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> implements ItemTouchHelperAdapter {
    private OnTaskClickListener onTaskClickListener;
    private ArrayList<Task> added_tasks = new ArrayList<Task>();
    private int images[];
    Context context;

    public MyAdapter(Context ct, ArrayList<Task> tasks,int img[],OnTaskClickListener onTaskClickListener){
        context = ct;
        images = img;
        added_tasks = tasks;
        this.onTaskClickListener = onTaskClickListener;
    }
    public void setItem(Task task) {
        added_tasks.add(task);
        notifyDataSetChanged();
    }
    public void onItemDismiss(int position)
    {
        added_tasks.remove(position);
        Intent intent = new Intent("Item was deleted");
        intent.putExtra("Deleted",position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        notifyItemRemoved(position);
    }

    public void Taskdone(int position)
    {
        added_tasks.get(position).setStatus(false);
        Intent intent = new Intent("Task is done");
        intent.putExtra("Done",position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate(R.layout.task_row,parent,false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        //final RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(itemView);
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
        if(added_tasks.get(position).isStatus()){
            holder.title.setTextColor(Color.parseColor("#000000"));
            holder.due.setTextColor(Color.parseColor("#000000"));
        }
        else{
            holder.title.setTextColor(Color.parseColor("#ECECEC"));
            holder.due.setTextColor(Color.parseColor("#ECECEC"));
            holder.typeimg.setImageResource(images[4]);
        }
        int pos = holder.getAdapterPosition();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task chosen = added_tasks.get(pos);
                Log.i("TAG1","I will send task number "+ Integer.toString(chosen.getId()));
                onTaskClickListener.OnTaskClick(chosen);
            }
        });

    }

    @Override
    public int getItemCount() {
        return added_tasks.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, due;
        ImageView typeimg;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            due = itemView.findViewById(R.id.task_date);
            typeimg = itemView.findViewById(R.id.Type_img);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Task chosen = added_tasks.get(getAdapterPosition());
                    Log.i("TAG1","I will send task number "+ Integer.toString(chosen.getId()));
                    onTaskClickListener.OnTaskClick(chosen);
                }
            });*/

        }

        @Override
        public void onClick(View view) {

        }
    }

    public interface OnTaskClickListener {
        void OnTaskClick(Task task);
    }

}
