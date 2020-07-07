package com.example.implementacionws;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.implementacionws.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private EditText mUser, mPassword;
    private Button button;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        iniciarComponentes();
    }

    private void iniciarComponentes() {

        mUser = findViewById(R.id.editTextTextPersonName);
        mPassword = findViewById(R.id.editTextTextPassword);
        button = findViewById(R.id.buttonLogin);
        requestQueue = Volley.newRequestQueue(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ejecutarWS();
            }
        });
    }

    private void ejecutarWS() {

        String nombre = mUser.getText().toString();
        String password = mPassword.getText().toString();

        String urlLogin = "http://192.168.0.88/ejemploBDRemota/wsJSONLoginUsuario.php?nombre="+nombre+
                "&password="+password;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,urlLogin,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());

    }

    @Override
    public void onResponse(JSONObject response) {

        User user = new User();

        try {
            JSONArray jsonArray = response.optJSONArray("datos");
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            user.setNombreUser(jsonObject.optString("nombre"));
            user.setPassword(jsonObject.optString("password"));
            user.setDocumento(jsonObject.optInt("documento"));
            user.setProfesion(jsonObject.optString("profesion"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Toast.makeText(this, "Bienvenido "+ user.toString(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}