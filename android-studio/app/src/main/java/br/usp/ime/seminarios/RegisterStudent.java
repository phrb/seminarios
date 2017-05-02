package br.usp.ime.seminarios;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterStudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_student);

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        if (response != null) {
            String user_name = intent.getStringExtra("user_name");
            String user_number = intent.getStringExtra("user_number");
            String user_pwd = intent.getStringExtra("user_pwd");

            EditText mUserName = (EditText) findViewById(R.id.user_name);
            EditText mUserNumber = (EditText) findViewById(R.id.user_number);
            EditText mUserPwd = (EditText) findViewById(R.id.user_pwd);

            String message = "";

            if (response.equalsIgnoreCase("request_error")) {
                mUserName.setText(user_name);
                mUserNumber.setText(user_number);
                mUserPwd.setText(user_pwd);
                message = "Ops! Tente novamente...";
            } else if (response.equalsIgnoreCase("register_error")) {
                mUserName.setText(user_name);
                mUserNumber.setText(user_number);
                mUserPwd.setText(user_pwd);
                message = "Usuário já cadastrado";
            }

            Snackbar.make(findViewById(R.id.register), message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void onClickRegister(View view) {
        EditText mUserName = (EditText) findViewById(R.id.user_name);
        EditText mUserNumber = (EditText) findViewById(R.id.user_number);
        EditText mUserPwd = (EditText) findViewById(R.id.user_pwd);

        if (mUserName.getText().toString().equalsIgnoreCase("")) {
            mUserName.setHint("Entre com seu nome!");
        }

        if (mUserNumber.getText().toString().equalsIgnoreCase("")) {
            mUserNumber.setHint("Entre com seu Nº USP!");
        }

        if (mUserPwd.getText().toString().equalsIgnoreCase("")) {
            mUserPwd.setHint("Entre com sua senha!");
        }

        if (!mUserName.getText().toString().equalsIgnoreCase("") &&
                !mUserPwd.getText().toString().equalsIgnoreCase("") &&
                !mUserName.getText().toString().equalsIgnoreCase("")) {
            Intent intent = new Intent(this, WaitRegisterStudent.class);
            intent.putExtra("user_number", mUserNumber.getText().toString());
            intent.putExtra("user_name", mUserName.getText().toString());
            intent.putExtra("user_pwd", mUserPwd.getText().toString());
            startActivity(intent);
        }
    }
}
