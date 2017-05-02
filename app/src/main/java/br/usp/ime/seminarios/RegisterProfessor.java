package br.usp.ime.seminarios;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class RegisterProfessor extends AppCompatActivity {

    private String user_token;
    private String user_name;
    private String user_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_professor);

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        user_name = intent.getStringExtra("user_name");
        user_pwd = intent.getStringExtra("user_pwd");
        user_token = intent.getStringExtra("user_token");

        if (response != null) {
            String new_user_name = intent.getStringExtra("new_user_name");
            String new_user_pwd = intent.getStringExtra("new_user_pwd");
            String new_user_number = intent.getStringExtra("new_user_number");

            EditText mUserName = (EditText) findViewById(R.id.user_name);
            EditText mUserNumber = (EditText) findViewById(R.id.user_number);
            EditText mUserPwd = (EditText) findViewById(R.id.user_pwd);

            String message = "";

            mUserName.setText(new_user_name);
            mUserNumber.setText(new_user_number);
            mUserPwd.setText(new_user_pwd);

            if (response.equalsIgnoreCase("request_error")) {
                message = "Ops! Tente novamente...";

                Snackbar.make(findViewById(R.id.register), message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else if (response.equalsIgnoreCase("register_error")) {
                message = "Professor já cadastrado";

            } else if (response.equalsIgnoreCase("register_success")) {
                message = "Professor cadastrado com sucesso!";
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
            Intent intent = new Intent(this, WaitRegisterProfessor.class);
            intent.putExtra("new_user_number", mUserNumber.getText().toString());
            intent.putExtra("new_user_name", mUserName.getText().toString());
            intent.putExtra("new_user_pwd", mUserPwd.getText().toString());

            intent.putExtra("user_token", user_token);
            intent.putExtra("user_name", user_name);
            intent.putExtra("user_pwd", user_pwd);
            startActivity(intent);
        }
    }
}
