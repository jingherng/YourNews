package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Login extends Activity {

    private EditText username, password;
    private Intent homeIntent;
    private UserDB UserDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        UserDB = new UserDB(this);

        // Begin the transaction
        Fragment logo = new logo();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        // Replace the contents of the container with the new fragment
        ft.replace(R.id.placeHolder, logo);
        ft.addToBackStack(null);
        // or ft.add(R.id.your_placeholder, new FooFragment());
        // Complete the changes added above
        ft.commit();

        // for orientation change
        String user = "" ,pass = "";
        if ( savedInstanceState != null ){
            user = savedInstanceState.getString("username");
            pass = savedInstanceState.getString("password");
        }
        if (user != null)
            username.setText(user);
        if (pass != null)
            password.setText(pass);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("username", username.getText().toString());
        savedInstanceState.putString("password", password.getText().toString());
    }

    public void Login(View view) {
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            Intent homeIntent = new Intent(Login.this, MainApp.class);
            startActivity(homeIntent);
        }
        else if (UserDB.CheckIsDataAlreadyInDBorNot("USER_DB","USERNAME",username.getText().toString())||
                UserDB.CheckIsDataAlreadyInDBorNot("USER_DB","PASSWORD",password.getText().toString())){
            Intent homeIntent = new Intent(Login.this, MainApp.class);
            startActivity(homeIntent);
        }
        else
            showToast();
    }

    private void showToast(){
        CharSequence error = "Wrong Username/Password";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, error, duration);
        toast.show();
    }

    public void loginHelp(View v){
        Toast t = Toast.makeText(this,"Click on Sign Up if you don't have an account\nClick on Login if you have an account", Toast.LENGTH_LONG);
        t.show();
    }

    public void SignUp(View v){
        if (username.getText().toString().equals("") || password.getText().toString().equals("")){
            Toast toast = Toast.makeText(this, "Please insert a Username/Password", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {

            if (!UserDB.CheckIsDataAlreadyInDBorNot("USER_DB","USERNAME",username.getText().toString())||
                    !UserDB.CheckIsDataAlreadyInDBorNot("USER_DB","PASSWORD",password.getText().toString())) {
                ContentValues newAcc = new ContentValues();
                newAcc.put("USERNAME", username.getText().toString());
                newAcc.put("PASSWORD", password.getText().toString());
                SQLiteOpenHelper dbHelper = new UserDB(this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.insert("USER_DB", null, newAcc);
                db.close();
                Toast toast = Toast.makeText(this, "Account Created\nLogin again", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                Toast toast = Toast.makeText(this, "Account Not Created\nPlease try again", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}