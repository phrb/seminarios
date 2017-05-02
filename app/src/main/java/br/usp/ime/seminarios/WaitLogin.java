package br.usp.ime.seminarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class WaitLogin extends AppCompatActivity {

    public void onErrorResponse(String user_name, String user_pwd) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("response", "request_error");
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_pwd", user_pwd);
        startActivity(intent);
    }

    public void onLoginStudentError(final String user_name, final String user_pwd) {
        String url = "http://207.38.82.139:8001/login/teacher";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("\"success\":true")) {
                            WaitLogin.this.onLoginProfessorSuccess(user_name, user_pwd, response.split("\"")[5]);
                        } else {
                            WaitLogin.this.onLoginError(user_name, user_pwd);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                WaitLogin.this.onErrorResponse(user_name, user_pwd);
            }
        }
        ) {
            String body = "nusp=" + user_name + "&pass=" + user_pwd;
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void attemptStudentLogin(final String user_name, final String user_pwd) {
        String url = "http://207.38.82.139:8001/login/student";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("\"success\":true")) {
                            WaitLogin.this.onLoginStudentSuccess(user_name, user_pwd, response.split("\"")[5]);
                        } else {
                            WaitLogin.this.onLoginStudentError(user_name, user_pwd);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                WaitLogin.this.onErrorResponse(user_name, user_pwd);
            }
        }
        ) {
            String body = "nusp=" + user_name + "&pass=" + user_pwd;
            @Override
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onLoginError(String user_name, String user_pwd) {
        Intent intent = new Intent(this, Login.class);
        intent.putExtra("response", "login_error");
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_pwd", user_pwd);
        startActivity(intent);
    }

    public void onLoginStudentSuccess(String user_name, String user_pwd, String token) {
        Intent intent = new Intent(this, StudentSeminars.class);
        intent.putExtra("response", "login_success");
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_pwd", user_pwd);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    public void onLoginProfessorSuccess(String user_name, String user_pwd, String token) {
        Intent intent = new Intent(this, ProfessorSeminars.class);
        intent.putExtra("response", "login_success");
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_pwd", user_pwd);
        intent.putExtra("token", token);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_login);

        Intent intent = getIntent();
        final String user_name = intent.getStringExtra("user_name");
        final String user_pwd = intent.getStringExtra("user_pwd");

        attemptStudentLogin(user_name, user_pwd);
    }
}
