package br.usp.ime.seminarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfessorSeminars extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<Seminar> seminarList;
    private SeminarRecyclerViewAdapter all_adapter;
    private ProgressBar progress_bar;
    private RecyclerView seminars;

    private String user_name, user_pwd, user_token;

    protected void updatedAvailable(JSONObject seminar) throws JSONException {
        Seminar new_seminar = new Seminar();
        new_seminar.setId(seminar.getString("id"));
        new_seminar.setPresenter("Linus Torvalds");
        new_seminar.setTitle(seminar.getString("name"));
        new_seminar.setShort_description("Your code sucks. Wanna know why? Come see this seminar.");

        seminarList.add(new_seminar);
    }

    protected void updateSeminarLists(String response) throws JSONException {
        JSONObject json_response = new JSONObject(response);
        JSONArray data = json_response.getJSONArray("data");

        for (int i = 0; i < data.length(); i++) {
            JSONObject new_seminar = data.getJSONObject(i);

            updatedAvailable(new_seminar);
        }
    }

    protected void requestSeminars() {
        String url = "http://207.38.82.139:8001/seminar";
        final String message = "Ops! Tente novamente...";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("\"success\":true")) {
                            try {
                                updateSeminarLists(response);
                            } catch (JSONException e) {
                                Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            }
                        } else {
                            Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(findViewById(R.id.drawer_layout), message, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
        ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        RequestQueueSingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    protected void loadSeminars(){
        seminars.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);

        requestSeminars();

        progress_bar.setVisibility(View.GONE);
        seminars.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_seminars);

        Intent intent = getIntent();
        user_name = intent.getStringExtra("user_name");
        user_pwd = intent.getStringExtra("user_pwd");
        user_token = intent.getStringExtra("user_token");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        seminars = (RecyclerView) findViewById(R.id.seminars);

        seminars.setLayoutManager(new LinearLayoutManager(this));

        seminarList = new ArrayList<>();

        seminars.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);

        loadSeminars();

        RequestQueue.RequestFinishedListener finishedListener = new RequestQueue.RequestFinishedListener() {
            @Override
            public void onRequestFinished(Request request) {
                all_adapter = new SeminarRecyclerViewAdapter(ProfessorSeminars.this, seminarList);

                seminars.setAdapter(all_adapter);

                seminars.setVisibility(View.VISIBLE);
                progress_bar.setVisibility(View.GONE);
            }
        };

        RequestQueueSingleton.getInstance(this).getRequestQueue().addRequestFinishedListener(finishedListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.professor_seminars, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            progress_bar.setVisibility(View.VISIBLE);

            seminars.setAdapter(all_adapter);

            progress_bar.setVisibility(View.GONE);
        } else if (id == R.id.nav_create_seminar) {

        } else if (id == R.id.nav_new_professor) {
            Intent intent = new Intent(this, RegisterProfessor.class);
            intent.putExtra("user_name", user_name);
            intent.putExtra("user_pwd", user_pwd);
            intent.putExtra("user_token", user_token);
            startActivity(intent);
        } else if (id == R.id.nav_preferences) {

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(this, Login.class);
            intent.putExtra("response", "logout");
            intent.putExtra("user_name", user_name);
            intent.putExtra("user_pwd", user_pwd);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
