package br.usp.ime.seminarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class WaitRegisterProfessor extends AppCompatActivity {
    String old_user_name;
    String old_user_pwd;
    String old_user_token;

    public void onErrorResponse(String user_name, String user_pwd, String user_number) {
        Intent intent = new Intent(this, RegisterProfessor.class);
        intent.putExtra("response", "request_error");
        intent.putExtra("new_user_number", user_number);
        intent.putExtra("new_user_name", user_name);
        intent.putExtra("new_user_pwd", user_pwd);
        startActivity(intent);
    }

    public void onRegisterError(String user_name, String user_pwd, String user_number, String server_response) {
        Intent intent = new Intent(this, RegisterProfessor.class);
        intent.putExtra("response", "register_error");
        intent.putExtra("server_response", server_response);
        intent.putExtra("new_user_number", user_number);
        intent.putExtra("new_user_name", user_name);
        intent.putExtra("new_user_pwd", user_pwd);
        startActivity(intent);
    }

    public void onRegisterSuccess(String user_number, String user_pwd, String server_response) {
        Intent intent = new Intent(this, RegisterProfessor.class);
        intent.putExtra("response", "register_success");
        intent.putExtra("server_response", server_response);
        intent.putExtra("user_name", old_user_name);
        intent.putExtra("user_pwd", old_user_pwd);
        intent.putExtra("user_token", old_user_token);

        System.out.println("Registered: " + user_number + " " + user_pwd);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_register_professor);

        Intent intent = getIntent();
        final String user_name = intent.getStringExtra("new_user_name");
        final String user_number = intent.getStringExtra("new_user_number");
        final String user_pwd = intent.getStringExtra("new_user_pwd");

        old_user_name = intent.getStringExtra("user_name");
        old_user_token = intent.getStringExtra("user_token");
        old_user_pwd = intent.getStringExtra("user_pwd");

        String url = "http://207.38.82.139:8001/teacher/add";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("\"success\":true") && !response.contains("\"message\"")) {
                            WaitRegisterProfessor.this.onRegisterSuccess(user_number, user_pwd, response);
                        } else {
                            WaitRegisterProfessor.this.onRegisterError(user_name, user_pwd, user_number, response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                WaitRegisterProfessor.this.onErrorResponse(user_name, user_pwd, user_number);
            }
        }
        ) {
            String body = "nusp=" + user_number + "&pass=" + user_pwd + "&name=" + user_name;
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
}
