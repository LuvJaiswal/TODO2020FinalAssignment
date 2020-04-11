package com.example.todo2020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private List<Note> notes = new ArrayList<>();
    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item,parent,false);

        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Note currentNote = notes.get(position);
        holder.mTitle.setText(currentNote.getTitle());
        holder.mDescription.setText(currentNote.getDescription());
        holder.mPriority.setText(String.valueOf(currentNote.getPriority()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setTodo(List<Note>notes){
        this.notes = notes;
        notifyDataSetChanged();

    }

    class TodoHolder extends RecyclerView.ViewHolder{

       private TextView mTitle,mDescription,mPriority;


       public TodoHolder(@NonNull View itemView) {
           super(itemView);
           mTitle = itemView.findViewById(R.id.tv_title);
           mDescription =itemView.findViewById(R.id.tv_description);
           mPriority = itemView.findViewById(R.id.tv_Priority);

       }
   }
}

