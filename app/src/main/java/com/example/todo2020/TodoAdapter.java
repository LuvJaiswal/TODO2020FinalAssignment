package com.example.todo2020;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.provider.Settings.System.DATE_FORMAT;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {


    private OnItemClickListener listener;
    private List<Note> notes = new ArrayList<>();

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
        Note currentNote = notes.get(position);

        /**
         * for updated date
         */
        String updatedAt = dateFormat.format(currentNote.getUpdatedAt());

        holder.mTitle.setText(currentNote.getTitle());
        holder.mDescription.setText(currentNote.getDescription());
        holder.mPriority.setText(String.valueOf(currentNote.getPriority()));


        holder.updatedAtView.setText(String.valueOf(currentNote.getUpdatedAt()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setTodo(List<Note> notes) {
        this.notes = notes;
        notifyDataSetChanged();

    }

    public Note getNoteAt(int position) {
        return notes.get(position);
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
                        listener.onItemClick(notes.get(position)); //acquired the position
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;


    }

}


