package com.example.implementacionws.ui.listado;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.implementacionws.R;
import com.example.implementacionws.entity.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListadoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ListadoViewModel listadoViewModel;
    private RecyclerView recyclerView;
    private ArrayList<User> usuarios;
    private ListadoAdapter listadoAdapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;
    private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listadoViewModel =
                ViewModelProviders.of(this).get(ListadoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listado, container, false);

        listadoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        usuarios = new ArrayList<>();
        context = getContext();
        recyclerView = root.findViewById(R.id.recyclerListadoUser);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        requestQueue = Volley.newRequestQueue(getContext());

        cargarWS();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void cargarWS() {

        String url = "http://192.168.0.88/ejemploBDRemota/wsJSONConsultarListaUsuarios.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "Error Volley", Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response) {

        User user = null;
        JSONArray jsonArray = response.optJSONArray("usuario");

        try {

            for (int i =0; i < jsonArray.length(); i++){
                user = new User();
                JSONObject jsonObject = null;
                /**
                 * comienza desde la posicion i
                 */
                jsonObject = jsonArray.getJSONObject(i);
                user.setDocumento(jsonObject.optInt("documento"));
                user.setNombreUser(jsonObject.optString("nombre"));
                user.setProfesion(jsonObject.optString("profesion"));
                usuarios.add(user);
            }

            listadoAdapter = new ListadoAdapter(usuarios, context);
            recyclerView.setAdapter(listadoAdapter);

        }catch (Exception e){

            e.printStackTrace();
            Toast.makeText(getContext(), "No se pudo Conectar", Toast.LENGTH_SHORT).show();
        }
    }
}