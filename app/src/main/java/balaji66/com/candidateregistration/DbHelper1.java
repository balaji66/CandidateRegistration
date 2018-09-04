package balaji66.com.candidateregistration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper1 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Student.db";
    // User table name
    private static final String TABLE_CAN = "stu";
    private static final String COLUMN_CANDIDATE_IMAGE= "candidate_image";
    private static final String COLUMN_CANDIDATE_ID = "candidate_id";
    private static final String COLUMN_CANDIDATE_FIRST_NAME = "candidate_firstname";
    private static final String COLUMN_CANDIDATE_LAST_NAME = "candidate_lastname";
    private static final String COLUMN_CANDIDATE_DATE_OF_BIRTH = "candidate_dateofbirth";
    private static final String COLUMN_CANDIDATE_EMAIL = "candidate_email";
    private static final String COLUMN_CANDIDATE_PASSWORD = "candidate_password";

    public DbHelper1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CAN_TABLE = "CREATE TABLE " + TABLE_CAN + "("
                + COLUMN_CANDIDATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_CANDIDATE_FIRST_NAME + " TEXT," +
                COLUMN_CANDIDATE_LAST_NAME + " TEXT," +
                COLUMN_CANDIDATE_DATE_OF_BIRTH + " TEXT,"
                + COLUMN_CANDIDATE_EMAIL + " TEXT," +
                COLUMN_CANDIDATE_PASSWORD + " TEXT," +
                COLUMN_CANDIDATE_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_CAN_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_CAN_TABLE = "DROP TABLE IF EXISTS " + TABLE_CAN;
        db.execSQL(DROP_CAN_TABLE);
        onCreate(db);
    }

    public void addCandidate(User candidate, byte[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CANDIDATE_FIRST_NAME, candidate.getFirstname());
        values.put(COLUMN_CANDIDATE_LAST_NAME, candidate.getLastname());
        values.put(COLUMN_CANDIDATE_DATE_OF_BIRTH, candidate.getDateofbirth());
        values.put(COLUMN_CANDIDATE_EMAIL, candidate.getEmail());
        values.put(COLUMN_CANDIDATE_PASSWORD, candidate.getPassword());
        values.put(COLUMN_CANDIDATE_IMAGE, data);
        // Inserting Row
        db.insert(TABLE_CAN, null, values);
        db.close();
    }
    public boolean checkCandidate(String email) {

        // array of columns to fetch
        String[] columns = {COLUMN_CANDIDATE_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_CANDIDATE_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_CAN,columns,selection,selectionArgs,null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public boolean checkCandidate(String email, String password) {

        // array of columns to fetch
        String[] columns = {COLUMN_CANDIDATE_ID};
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_CANDIDATE_EMAIL + " = ?" + " AND " + COLUMN_CANDIDATE_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_CAN, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
    public void updateCandidate(User candidate, byte[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(COLUMN_CANDIDATE_ID,candidate.getId());
        values.put(COLUMN_CANDIDATE_FIRST_NAME, candidate.getFirstname());
        values.put(COLUMN_CANDIDATE_LAST_NAME, candidate.getLastname());
        values.put(COLUMN_CANDIDATE_DATE_OF_BIRTH, candidate.getDateofbirth());
        values.put(COLUMN_CANDIDATE_EMAIL, candidate.getEmail());
        values.put(COLUMN_CANDIDATE_PASSWORD, candidate.getPassword());
        values.put(COLUMN_CANDIDATE_IMAGE,data);
        // updating row
        db.update(TABLE_CAN, values,null,null);
        db.close();
    }

}
