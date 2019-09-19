package com.example.logindemo2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "login_info.db";
    private static final int DATABASE_VERSION = 3;
    private final Context context;
    SQLiteDatabase db;

    private static String DATABASE_PATH = "";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        if (android.os.Build.VERSION.SDK_INT >= 17)
            DATABASE_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DATABASE_PATH = "/data/data/" + context.getPackageName() + "/databases/";

        this.context = context;
        createDb();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion>oldVersion) {
            copyDatabase();
        }
    }

    public void createDb(){
        boolean dbExist = checkDbExist();

        if(!dbExist){
            this.getReadableDatabase();
            this.close();
            copyDatabase();

        }
    }


    private boolean checkDbExist(){
        File databasePath = context.getDatabasePath(DATABASE_NAME);
        return databasePath.exists();
    }

    private void copyDatabase(){
        try {
            InputStream inputStream = context.getAssets().open(DATABASE_NAME);

            String outFileName = DATABASE_PATH + DATABASE_NAME;

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] b = new byte[1024];
            int length;

            while ((length = inputStream.read(b)) > 0){
                outputStream.write(b, 0, length);
            }

            outputStream.flush();
            outputStream.close();
            inputStream.close();
            Log.e("DataBaseHelper", "createDatabase database created");

        } catch (IOException e) {
            throw new Error("ErrorCopyingDataBase");
        }

    }

    private SQLiteDatabase openDatabase(){
        String path = DATABASE_PATH + DATABASE_NAME;
        db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return db;
    }

    public void close(){
        if(db != null){
            db.close();
        }
    }

    public boolean checkUserExist(String username, String password, String typeOfUser){

        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(username);
        boolean matchFound = matcher.matches();

        String[] columns = {"username", "email"};
        db = openDatabase();

        String select1 = "username=? and password = ?";
        String select2 = "email=? and password = ?";

        String[] selectionArgs = {username, password};
        Cursor cursor;

        if(username.equals("admin")){
            cursor = db.query(username, new String[] {"username"}, select1, selectionArgs,null,null,null);

        }
        else {
            if(matchFound){
                cursor = db.query(typeOfUser, columns, select2, selectionArgs,null,null,null);
            }
            else {
                cursor = db.query(typeOfUser, columns, select1, selectionArgs,null,null,null);

            }
        }

        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }


}