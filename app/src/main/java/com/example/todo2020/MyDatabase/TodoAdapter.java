package com.example.todo2020.MyDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo2020.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {


    private OnItemClickListener listener;
    private List<mytodo> mytodos = new ArrayList<>();

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);

        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        mytodo currentMytodo = mytodos.get(position);

        /**
         * for updated date
         */
        String updatedAt = dateFormat.format(currentMytodo.getUpdatedAt());

        holder.mTitle.setText(currentMytodo.getTitle());
        holder.mDescription.setText(currentMytodo.getDescription());
        holder.mPriority.setText(String.valueOf(currentMytodo.getPriority()));


        holder.updatedAtView.setText(String.valueOf(currentMytodo.getUpdatedAt()));

    }

    @Override
    public int getItemCount() {
        return mytodos.size();
    }

    public void setTodo(List<mytodo> mytodos) {
        this.mytodos = mytodos;
        notifyDataSetChanged();

    }

    public mytodo getNoteAt(int position) {
        return mytodos.get(position);
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mDescription, mPriority,updatedAtView;


        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDescription = itemView.findViewById(R.id.tv_description);
            mPriority = itemView.findViewById(R.id.tv_Priority);
            updatedAtView = itemView.findViewById(R.id.taskUpdatedAt);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();  //position where we need to click
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(mytodos.get(position)); //acquired the position
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(mytodo mytodo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;


    }

    public void setFilter(List<mytodo> newList){
        mytodos = new ArrayList<>();
        mytodos.addAll(newList);
        notifyDataSetChanged();
    }

}


