package com.example.implementacionws.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.implementacionws.R;

public class ListadoFragment extends Fragment {

    private ListadoViewModel listadoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listadoViewModel =
                ViewModelProviders.of(this).get(ListadoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_listado, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        listadoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}