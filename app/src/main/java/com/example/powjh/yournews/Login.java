package com.example.powjh.yournews;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Login extends Activity {

    private EditText username, password;
    private Intent homeIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

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
        else
            showToast();
    }

    public void returnBack(View v){
        Fragment fragment = getFragmentManager().findFragmentById(R.id.placeHolder);
        if(fragment != null)
            getFragmentManager().beginTransaction().remove(fragment).commit();
    }

    private void showToast(){
        CharSequence error = "Wrong Username/Password";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(this, error, duration);
        toast.show();
    }
}