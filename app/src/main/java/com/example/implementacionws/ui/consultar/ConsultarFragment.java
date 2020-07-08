package com.example.implementacionws.ui.consultar;

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
import com.example.implementacionws.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ConsultarFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{

    private ConsultarViewModel consultarViewModel;
    private EditText etDoc;
    private TextView tvNombre, tvProfesion;
    private Button button;

    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        consultarViewModel =
                ViewModelProviders.of(this).get(ConsultarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_consultar, container, false);

        consultarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDoc = view.findViewById(R.id.editTextNumber);
        tvNombre = view.findViewById(R.id.tvNombre);
        tvProfesion = view.findViewById(R.id.textViewProfesion);
        button = view.findViewById(R.id.button);

        /**
         * instancia volley
         */
        requestQueue = Volley.newRequestQueue(getContext());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWS();
            }
        });
    }

    private void cargarWS() {

        String documento = etDoc.getText().toString();
        String url = "http://192.168.0.88/ejemploBDRemota/wsJSONConsultarUsuario.php?documento="+documento;

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "No se pudo registrar", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        User user = new User();

        /**
         * pide que se devuelva el array que esta asignado a usuario
         * ver: RESPUESTAPHP consultar usuarios
         */
        JSONArray jsonArray = response.optJSONArray("usuario");
        JSONObject jsonObject = null;

        /**
         * inicia el array de datos json
         */
        try {
            jsonObject = jsonArray.getJSONObject(0);
            /**
             * trae los nombre de los campos de la tabla
             */
            user.setNombreUser(jsonObject.optString("nombre"));
            user.setProfesion(jsonObject.optString("profesion"));
            user.setDocumento(jsonObject.optInt("documento"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        tvNombre.setText(user.getNombreUser());
        tvProfesion.setText(user.getProfesion());
    }
}