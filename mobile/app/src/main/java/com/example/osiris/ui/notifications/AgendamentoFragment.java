package com.example.osiris.ui.notifications;

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

import com.example.osiris.R;

public class AgendamentoFragment extends Fragment {

    private AgendamentoViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {notificationsViewModel = ViewModelProviders.of(this).get(AgendamentoViewModel.class);

    View root = inflater.inflate(R.layout.fragment_agendamento, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}