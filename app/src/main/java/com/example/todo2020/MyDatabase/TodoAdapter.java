package com.example.todo2020.MyDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo2020.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> implements Filterable {

    //    final Context context;
    private ArrayList<Todo> listItems;
    private ArrayList<Todo> filterList;


    private OnItemClickListener listener;
    private List<Todo> Todos = new ArrayList<>();

    // Date formatter
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    // Constant for date format
    private static final String DATE_FORMAT = "dd/MM/yyy";

//    public TodoAdapter() {
//        this.listItems = listItems;
//        this.filterList = new ArrayList<>();
//        this.filterList.addAll(listItems);
//        this.context = context;
//    }

    @NonNull
    @Override
    public TodoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item, parent, false);

        return new TodoHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoHolder holder, int position) {
        Todo currentTodo = Todos.get(position);

        /**
         * for updated date
         */
        String updatedAt = dateFormat.format(currentTodo.getUpdatedAt());

        holder.mTitle.setText(currentTodo.getTitle());
        holder.mDescription.setText(currentTodo.getDescription());
        holder.mPriority.setText(String.valueOf(currentTodo.getPriority()));


        holder.updatedAtView.setText(String.valueOf(currentTodo.getUpdatedAt()));

    }

    @Override
    public int getItemCount() {
        return Todos.size();
    }

    public void setTodo(List<Todo> Todos) {
        this.Todos = Todos;
        notifyDataSetChanged();

    }

    public Todo getNoteAt(int position) {
        return Todos.get(position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString().toLowerCase();
                ArrayList<Todo> myList = new ArrayList<>();
                if (charString.isEmpty()) {
                    myList.addAll(Todos);
                } else {
                    for (Todo Todo : Todos) {
                        if (Todo.toString().toLowerCase().contains(charString)) {
                            myList.add(Todo);
                        }

                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = myList;
                return filterResults;
            }

            //runs on a Ui thread
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterList.clear();
                filterList.addAll((ArrayList<Todo>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    class TodoHolder extends RecyclerView.ViewHolder {

        private TextView mTitle, mDescription, mPriority, updatedAtView;


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
                        listener.onItemClick(Todos.get(position)); //acquired the position
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(Todo Todo);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;


    }

        /*
        first attempt for search
         */

    public void setFilter(List<Todo> newList) {
        Todos = new ArrayList<>();
        Todos.addAll(newList);
        notifyDataSetChanged();
    }


}


