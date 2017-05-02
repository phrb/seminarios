package br.usp.ime.seminarios;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class WaitRegisterStudent extends AppCompatActivity {

    public void onErrorResponse(String user_name, String user_pwd, String user_number) {
        Intent intent = new Intent(this, RegisterStudent.class);
        intent.putExtra("response", "request_error");
        intent.putExtra("user_number", user_number);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_pwd", user_pwd);
        startActivity(intent);
    }

    public void onRegisterError(String user_name, String user_pwd, String user_number, String server_response) {
        Intent intent = new Intent(this, RegisterStudent.class);
        intent.putExtra("response", "register_error");
        intent.putExtra("server_response", server_response);
        intent.putExtra("user_number", user_number);
        intent.putExtra("user_name", user_name);
        intent.putExtra("user_pwd", user_pwd);
        startActivity(intent);
    }

    public void onRegisterSuccess(String user_number, String user_pwd, String server_response) {
        Intent intent = new Intent(this, WaitLogin.class);
        intent.putExtra("response", "register_success");
        intent.putExtra("server_response", server_response);
        intent.putExtra("user_name", user_number);
        intent.putExtra("user_pwd", user_pwd);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_register_student);

        Intent intent = getIntent();
        final String user_name = intent.getStringExtra("user_name");
        final String user_number = intent.getStringExtra("user_number");
        final String user_pwd = intent.getStringExtra("user_pwd");

        String url = "http://207.38.82.139:8001/student/add";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("\"success\":true")) {
                            WaitRegisterStudent.this.onRegisterSuccess(user_number, user_pwd, response);
                        } else {
                            WaitRegisterStudent.this.onRegisterError(user_name, user_pwd, user_number, response);
                        }
                    }
                }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            WaitRegisterStudent.this.onErrorResponse(user_name, user_pwd, user_number);
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
