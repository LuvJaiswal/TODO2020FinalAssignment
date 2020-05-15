# TODO2020FinalAssignment

This is the project based on the developing own version of "TODO" adroid application using MVVM architecture components. 

<img src = "https://thumbs.gfycat.com/BogusGregariousBasil-max-1mb.gif"  width="800" height="500">

![](https://media.istockphoto.com/photos/todo-list-personal-planner-on-turquoise-office-table-picture-id925734296?k=6&m=925734296&s=612x612&w=0&h=_VB2egSpS_1RFYSknOl8v19wqT7dMbeRv8duolALfDc=)



<b>Android Architecture components</b> are a collection of libraries that help you design robust, testable, and maintainable apps with more power over lifecycle management and data persistence.

# What is MVVM?

MVVM is one of the architectural patterns which enhances the separation of concerns, it allows separating the user interface logic from the business (or the back-end) logic. Its target is to achieve the following principle “Keeping UI code simple and free of app logic in order to make it easier to manage”.

There are 3 parts to the Model-View-ViewModel architecture:-

   1. <b><u>Model</u></b> is the data layer of your app. It abstracts the data source.
   2. <b><u>View</u></b> contains the UI of your app. Most often it’s implemented as an Activity or Fragment. View informs ViewModel of user interactions and displays results received from the ViewModel. View should be lightweight and contain zero to very little business logic.
   3. <b><u>ViewModel</u></b> serves as a bridge between your View and Model. It works with the Model to get and save the data. The View observes and reacts to the data changes exposed by the ViewModel.

Here is a typical high-level MVVM app architecture:

![](https://miro.medium.com/max/1400/1*-yY0l4XD3kLcZz0rO1sfRA.png)

# Implementation steps

We will follow these steps to implement Android Architecture Components in our app:

    1. Add Room and LifeCycle Dependencies
    2. Setup Room
    3. Learn about Live Data
    4. Creating a Repository Class/Presentation Layer
    5. Implement ViewModel
    6. Add Adapter and RecyclerView
    7. Populate the Database
    8. Connect UI and Data
    9. Create AddNoteActivity

Now we discuss these steps in the mentioned order.

<b>1.Add Dependencies</b>

We need to add Room and Lifecycle components. Room is basically a database object mapping library used to access the database. Lifecycle has some good set of classes like ViewModel and LiveData which we will use to manage the lifecycle of our app.
First add version numbers and then add these libraries to build.gradle 

dependencies {

    def lifecycle_version = "1.1.1"
    def room_version = "1.1.1"
    
    
    //life cycle components
    implementation "android.arch.lifecycle:extensions:1.1.0"
    annotationProcessor "android.arch.lifecycle:compiler:1.1.0"
    testImplementation "android.arch.core:core-testing:1.1.0"

    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'

    //for room
    implementation 'android.arch.persistence.room:runtime:1.0.0-alpha1'
    annotationProcessor 'android.arch.persistence.room:compiler:1.0.0-alpha1'
    
    
    }
    
    
    
  # 
  <b>2. Setup Room</b>

There are 3 major components in Room:
<b>
    1.“Entity”
    2.“Dao”
    3.“Database”
    </b>
    
   ![](https://miro.medium.com/max/1248/1*4_teAVWTzaL5gTkEHu7gxw.png)

-----
   <b> a. “Entity” </b>
The entity is just a POJO which is also going to be the table in the database. For example, you can create a POJO class and annotate it with the “@Entity” annotation. You can also identify which field is the primary key with “@PrimaryKey”.



<b> @Entity(tableName = "myTodoList")   </b>



public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    


    private int priority;

    public Note(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }
}

Here, we have class Note, and the name of the table is “notes_table”. We had made three columns id, title, description and also made id a primary key by annotating “@PrimaryKey” and autoGenerate to true.

#

<b> b. “Dao” </b>



The Data Access Object (DAO) is an interface annotated with Dao. This is where the database CRUD (create, read, update and delete) operations are defined. Each method is annotated with “@Insert”, “@Delete”, “@Query(SELECT * FROM)”.


--

<b>@Dao
public interface TodoDao</b> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert(Note note);

    @Update
    void Update(Note note);

    @Delete
    void Delete(Note note);

    @Query("DELETE FROM myTodoList")
    void deleteAllNotes();


    @Query("SELECT * FROM myTodoList ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();

    //will be notified immediately if changes made
}


Here, we have an interface <b><u>TodoDao</u></b> and some methods which we will call to perform our queries. To insert the data we annotated “@Insert” to insert method. Room doesn’t give us annotations which can help us in deleting everything so we have “@Query” to do some custom queries.


#
<b> c. “Database” </b>



Create a class that extends from RoomDatabase and annotate it with “@Database”. This class ties together the Entities and DAOs. The database instance can be created at runtime, by calling Room.databaseBuilder() (on device) or Room.inMemoryDatabaseBuilder() (in memory).



<b> @Database(entities = {Note.class}, version = 1, exportSchema = false) </b>



public abstract class NoteDatabase extends RoomDatabase {

    //need to turn this class into singleton
    private static NoteDatabase instance;

    public abstract TodoDao todoDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;

    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


 
#
<b>3. Live Data </b>


LiveData class is from lifecycle library, for observing the data changes. It’s an observable data holder class, and it is also lifecycle aware which means that this is going to update the component which is in the active lifecycle state.


#
<b>4. Creating a Repository Class/Presentation Layer</b>

This is a class where we will check whether to fetch data from API or local database, or you can say we are putting the logic of database fetching in this class.


public class RepositoryNote {
    private TodoDao todoDao;
    private LiveData<List<Note>> allNotes;

    public RepositoryNote(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        todoDao = database.todoDao();
        allNotes = todoDao.getAllNotes();

    }

    public void insert(Note note) {
        new InsertNoteAsyncTask(todoDao).execute(note);

    }

    public void update(Note note) {
        new UpdateNoteAsyncTask(todoDao).execute(note);

    }

    public void delete(Note note) {
        new DeleteNoteAsyncTask(todoDao).execute(note);

    }

    public void deleteAllNotes() {
        new DeleteAllNoteAsyncTask(todoDao).execute();

    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;

    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private TodoDao todoDao;

        private InsertNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            todoDao.Insert(notes[0]);
            return null;
        }
    }


    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private TodoDao todoDao;

        private UpdateNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            todoDao.Update(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private TodoDao todoDao;

        private DeleteNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            todoDao.Delete(notes[0]);
            return null;
        }
    }

    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private TodoDao todoDao;

        private DeleteAllNoteAsyncTask(TodoDao todoDao) {
            this.todoDao = todoDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.deleteAllNotes();
            return null;
        }
    }

}




Here, we added wrapper for insert(), getAllNotes() and deleteAllNotes() . Room run its operations on non-UI thread/background thread, so we used AsyncTask.


#

<b>5. ViewModel</b>

This is also the part of lifecycle library; this will help you to provide data between repository and UI. This survives the data on configuration changes and gets the existing ViewModel to reconnect with the new instance of the owner.



Why use ViewModel?

    a) The ViewModel is lifecycle aware so that it will survive the configuration change.
    b) It will outlive the Activity or Fragment.
    c) Easier communications between fragments, instead of relying on the hosting Activity passing the communications.

***

 public class NoteViewModel extends AndroidViewModel {
    private RepositoryNote repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new RepositoryNote(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {
        repository.insert(note);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }


}
 
 
#
<b> 6. Add Adapter and RecyclerView </b>


Create a layout file(note_item.xml), include three TextView’s for Title and Description and priority bounded inside a Relative Layoutand also include RecyclerView in activity_home.xml. Also, don’t forget to add Recyclerview and CardView dependency in build.gradle. We also need to add an adapter class which is responsible to show our data on the screen.


---
<b><u>todo_item.xml</u><b>
#




    <RelativeLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="5dp">

        <TextView
            android:id="@+id/tv_Priority"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentEnd="true"
            android:text="1"
            android:textStyle="bold|italic"
            android:background="@drawable/et_style"
            android:textAppearance="@style/TextAppearance.AppCompat.Large" />

        <TextView
            android:id="@+id/tv_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentStart="true"
            android:layout_toStartOf="@+id/tv_Priority"
            android:ellipsize="end"
            android:maxLines="1"
            android:maxLength="25"
            android:text="Title"
            android:textStyle="bold|italic"
            android:textAppearance="@style/TextAppearance.AppCompat.Large"
            android:textSize="30dp" />

        <TextView
            android:id="@+id/tv_description"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:textSize="15dp"
            android:maxLength="80"
            android:textStyle="bold|italic"
            android:textColor="@color/colorPrimary"
            android:layout_below="@+id/tv_title"
            android:text="Description" />


    </RelativeLayout>



---
<b><u>TodoAdapter.class</u></b>
#


public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoHolder> {

    private OnItemClickListener listener;
    private List<Note> notes = new ArrayList<>();


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
        holder.mTitle.setText(currentNote.getTitle());
        holder.mDescription.setText(currentNote.getDescription());
        holder.mPriority.setText(String.valueOf(currentNote.getPriority()));

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

        private TextView mTitle, mDescription, mPriority;


        public TodoHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.tv_title);
            mDescription = itemView.findViewById(R.id.tv_description);
            mPriority = itemView.findViewById(R.id.tv_Priority);

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


---

<b>7. Fill the Database </b>

Here we populate the data when the app starts and before that we also delete existing data. We create PopulateDbAsyncTask which is an AsyncTask use to delete and insert the data.


   private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private TodoDao todoDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            todoDao = db.todoDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            todoDao.Insert(new Note("Title 1", "Description 1", 1));
            todoDao.Insert(new Note("Title 2", "Description 2", 2));
            todoDao.Insert(new Note("Title 3", "Description 3", 3));

            return null;
        }
    }

}


#
<b> 8. Connect UI and Data  </b>

To display the data from the database, we need an observer who will observe the data changes, LiveData in the ViewModel. We use ViewModelProvider which is going to create a ViewModel for us. We need to connect our ViewModel with the ViewModelProvider, and then in the onChanged method, we always get our updated data which we can display on the screen.


public class HomeFragmentActivity extends Fragment {

    private static final String TAG = HomeFragmentActivity.class.getSimpleName();

    public static final int ADD_TODO_REQUEST = 1;

    public static final int EDIT_TODO_REQUEST = 2;  //for edit

    private NoteViewModel noteViewModel;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home, null);
        setHasOptionsMenu(true);
        return root;


    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEditTodoActivityFragment addEditTodoActivityFragment = new AddEditTodoActivityFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("REQUEST_CODE", ADD_TODO_REQUEST);

                addEditTodoActivityFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, addEditTodoActivityFragment);
                fragmentTransaction.addToBackStack("Home");
                fragmentTransaction.commit();

            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final TodoAdapter adapter = new TodoAdapter();
        recyclerView.setAdapter(adapter);


        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                //update Recyclerview
                Log.d(TAG, "retrieving data from database");
                adapter.notifyDataSetChanged();
                adapter.setTodo(notes);

            }
        });


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Note tempNote = adapter.getNoteAt(viewHolder.getAdapterPosition());

                noteViewModel.delete(tempNote);

                View contextView = getView().findViewById(R.id.recyclerView);


                /*
                Snackbar with undo functionality added for delete todo
                 */

                Snackbar.make(contextView, "Todo deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        noteViewModel.insert(tempNote);
                    }
                }).show();

            }
        }).attachToRecyclerView(recyclerView);


        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getActivity(), "hi", Toast.LENGTH_SHORT).show();
                return false;
            }
            // Start ActionMode after long-click.
        });

        adapter.setOnItemClickListener(new TodoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                AddEditTodoActivityFragment addEditTodoActivityFragment = new AddEditTodoActivityFragment();

                Bundle bundle = new Bundle();
                bundle.putInt(AddEditTodoActivityFragment.EXTRA_ID, note.getId());
                bundle.putString(AddEditTodoActivityFragment.EXTRA_TITLE, note.getTitle());
                bundle.putString(AddEditTodoActivityFragment.EXTRA_DESCRIPTION, note.getDescription());
                bundle.putInt(AddEditTodoActivityFragment.EXTRA_PRIORITY, note.getPriority());
                bundle.putInt("REQUEST_CODE", EDIT_TODO_REQUEST);

                addEditTodoActivityFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, addEditTodoActivityFragment);
                fragmentTransaction.addToBackStack("Todo List");
                fragmentTransaction.commit();


            }


        });


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TODO_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivityFragment.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(getActivity(), "Todo saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_TODO_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTodoActivityFragment.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getActivity(), "Todo cannot be updated ", Toast.LENGTH_SHORT).show();
                return;

            }
            String title = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_TITLE);
            String description = data.getStringExtra(AddEditTodoActivityFragment.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditTodoActivityFragment.EXTRA_PRIORITY, 1);

            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(getActivity(), "Todo Updated", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Todo not saved", Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_All_notes:

                /*
                Delete all Notes Alert Dialogue box
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        noteViewModel.deleteAllNotes();
                        Toast.makeText(getActivity(), "All Todo notes deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();


               return true;


                /*
                     sendfeedback implicit intent
                 */


            case R.id.sendfeedback:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setData(Uri.parse("email"));
                String[] s = {"jaiswallove20@gmail.com"};
                i.putExtra(Intent.EXTRA_EMAIL, s);
                i.putExtra(Intent.EXTRA_SUBJECT, "LoveTODO : 'Write your Subject'");
                i.putExtra(Intent.EXTRA_TEXT, "This is an email body for your feedback");
                i.setType("message/rfc822");
                Intent chooser = Intent.createChooser(i, "Launch Email for Todo Feedback");
                startActivity(chooser);


            default:
                return super.onOptionsItemSelected(item);

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Home");
    }


    /*
       Activity lifecycle implemented
             */


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Started");

        NoteViewModel noteViewModel =
                ViewModelProviders.of(getActivity()).get(NoteViewModel.class);
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Paused");
    }


    @Override
    public void onStop() {
        super.onStop();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Stopped");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Destroyed");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Detached");
    }



}


##
<b>9. Create AddEditTodoActivityFragment</b>


We create an Fragment Activity where the user can input the data. Therefore we create an AddEditTodoActivityFragment.Here, we have three EditText for Title, Description and priority, and a menu to save the note.


public class AddEditTodoActivityFragment extends Fragment {

    private static final String TAG = AddEditTodoActivityFragment.class.getSimpleName();

    public static final String EXTRA_ID =
            "com.example.todo2020.EXTRA_ID";

    public static final String EXTRA_TITLE =
            "com.example.todo2020.EXTRA_TITLE";

    public static final String EXTRA_DESCRIPTION =
            "com.example.todo2020.EXTRA_DESCRIPTION";

    public static final String EXTRA_PRIORITY =
            "com.example.todo2020.EXTRA_PRIORITY";

    private NoteViewModel noteViewModel;

    private EditText todoTitle, todoDescription;
    private NumberPicker numberPicker;
    private Button button;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add_todo, null);
        setHasOptionsMenu(true);
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        System.out.println("TAG = " + TAG);
        Log.d(TAG, "Check the log here");

        todoTitle = (EditText) view.findViewById(R.id.todoTitle);
        todoDescription = (EditText) view.findViewById(R.id.todoDescription);
        numberPicker = (NumberPicker) view.findViewById(R.id.np_picker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);



        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);


        Bundle bundle = this.getArguments();

        int REQUEST_CODE = bundle.getInt("REQUEST_CODE");

        if (REQUEST_CODE == 2) {
            getActivity().setTitle("Edit Todo");
            todoTitle.setText(bundle.getString(EXTRA_TITLE, ""));
            todoDescription.setText(bundle.getString(EXTRA_DESCRIPTION, ""));
            numberPicker.setValue(bundle.getInt(EXTRA_PRIORITY, 1));

        } else {

            getActivity().setTitle("Add Todo");
        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_todo_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void saveTodo() {
        String title = todoTitle.getText().toString();
        String description = todoDescription.getText().toString();
        int priority = numberPicker.getValue();


        if (title.trim().isEmpty()) {
            todoTitle.setError("title required,please enter and save");
            todoTitle.requestFocus();
            return;
        }
        if (description.trim().isEmpty()){
            todoDescription.setError("Description required,please enter and save");
            todoDescription.requestFocus();
            return;
        }

        Bundle bundle = this.getArguments();

        int REQUEST_CODE = bundle.getInt("REQUEST_CODE", 1);

        if (REQUEST_CODE == 2) {
            int id = bundle.getInt(AddEditTodoActivityFragment.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Todo cannot be updated ", Toast.LENGTH_SHORT).show();
                return;

            }

            Note note = new Note(title, description, priority);
            note.setId(id);
            noteViewModel.update(note);

            Toast.makeText(getActivity(), "Todo Updated", Toast.LENGTH_SHORT).show();

            HomeFragmentActivity homeFragmentActivity = new HomeFragmentActivity();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragmentActivity);
            fragmentTransaction.addToBackStack("Home");
            fragmentTransaction.commit();

        } else {

            Note note = new Note(title, description, priority);
            noteViewModel.insert(note);
            Toast.makeText(getActivity(), "Note saved", Toast.LENGTH_SHORT).show();

            HomeFragmentActivity homeFragmentActivity = new HomeFragmentActivity();

            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, homeFragmentActivity);
            fragmentTransaction.addToBackStack("Home");
            fragmentTransaction.commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_todo:
                saveTodo();
                return true;
            default:


        }
        return super.onOptionsItemSelected(item);
    }



}



