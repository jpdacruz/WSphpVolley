package com.example.implementacionws.ui.registrar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.implementacionws.R;

import org.json.JSONObject;

public class RegistrarFragment extends Fragment implements Response.ErrorListener, Response.Listener<JSONObject> {

    private RegistrarViewModel registrarViewModel;
    private EditText edDoc, edNombre, edProfesion;
    private Button registrar;
    private ProgressDialog progressDialog;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        registrarViewModel =
                ViewModelProviders.of(this).get(RegistrarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_registrar, container, false);

        registrarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registrar = view.findViewById(R.id.buttonRegister);
        edDoc = view.findViewById(R.id.editTextDoc);
        edNombre = view.findViewById(R.id.editTextNombre);
        edProfesion = view.findViewById(R.id.editTextProfesion);

        requestQueue = Volley.newRequestQueue(getContext());
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarWS();
            }
        });

    }

    private void cargarWS() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String documento = edDoc.getText().toString();
        String nombre = edNombre.getText().toString();
        String profesion = edProfesion.getText().toString();

        /**
         * insert
         */
        String url = "http://192.168.0.88/ejemploBDRemota/wsJSONRegistro.php?documento="+documento+
                "&nombre="+nombre+
                "&profesion="+profesion;

        url = url.replace(" ","%20");

        /**
         * realizar el llamado
         */
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {
        progressDialog.hide();
        Toast.makeText(getContext(), "Correctamente", Toast.LENGTH_SHORT).show();
        edDoc.setText("");
        edNombre.setText("");
        edProfesion.setText("");

    }

    @Override
    public void onErrorResponse(VolleyError error) {

        progressDialog.hide();
        Toast.makeText(getContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }
}