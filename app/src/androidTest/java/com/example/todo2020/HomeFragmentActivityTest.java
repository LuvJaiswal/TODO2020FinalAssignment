//package com.example.todo2020;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class HomeFragmentActivityTest {
//
//    @Rule
//    public ActivityTestRule mActivityRule = new ActivityTestRule<>(
//            MainActivity.class);
//
//
//    @Before
//    public void setUp() throws Exception {
//    }
//
//    @Test
//    public void testLaunch(){
//        //test for fragment launching
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//    }
//}

//package com.example.todo2020;
//
//import android.content.Context;
//
//import androidx.room.Room;
//import androidx.test.core.app.ApplicationProvider;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.List;
//
//public class DatabaseTesting {
//    private TodoDao todoDao;
//    private NoteDatabase db;
//
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        db = Room.inMemoryDatabaseBuilder(context, NoteDatabase.class).build();
//        todoDao = db.getUserDao();
//    }
//
//    @After
//    public void closeDb() throws IOException {
//        db.close();
//    }
//
//    @Test
//    public void writeUserAndReadInList() throws Exception {
//        User user = TestUtil.createUser(3);
//        user.setName("george");
//        userDao.insert(user);
//        List<User> byName = userDao.findUsersByName("george");
//        assertThat(byName.get(0), equalTo(user));
//    }
//}
