package br.usp.ime.seminarios;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        if (response != null) {
            String user_name = intent.getStringExtra("user_name");
            String user_pwd = intent.getStringExtra("user_pwd");

            EditText mUser = (EditText) findViewById(R.id.user_id);
            EditText mUserPwd = (EditText) findViewById(R.id.user_pwd);

            String message = "";

            if (response.equalsIgnoreCase("request_error")) {
                mUser.setText(user_name);
                mUserPwd.setText(user_pwd);
                message = "Ops! Tente novamente...";
            } else if (response.equalsIgnoreCase("login_error")) {
                mUser.setText(user_name);
                message = "Usuário ou Senha incorretos";
            } else if (response.equalsIgnoreCase("logout")) {
                mUser.setText(user_name);
                mUserPwd.setText(user_pwd);
                message = "Logout efetuado";
            }

            Snackbar.make(findViewById(R.id.login), message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void onClickLogin(View view) {
        EditText mUser = (EditText) findViewById(R.id.user_id);
        EditText mUserPwd = (EditText) findViewById(R.id.user_pwd);

        if (mUser.getText().toString().equalsIgnoreCase("")) {
            mUser.setHint("Entre com seu Nº USP!");
        }

        if (mUserPwd.getText().toString().equalsIgnoreCase("")) {
            mUserPwd.setHint("Entre com sua senha!");
        }

        if (!mUser.getText().toString().equalsIgnoreCase("") &&
                !mUserPwd.getText().toString().equalsIgnoreCase("")) {
            Intent intent = new Intent(this, WaitLogin.class);
            intent.putExtra("user_name", mUser.getText().toString());
            intent.putExtra("user_pwd", mUserPwd.getText().toString());
            startActivity(intent);
        }
    }

    public void onClickRegister(View view) {
        Intent intent = new Intent(this, RegisterStudent.class);
        startActivity(intent);

    }
}
