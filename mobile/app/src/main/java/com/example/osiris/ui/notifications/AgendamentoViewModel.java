package com.example.osiris.ui.notifications;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgendamentoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AgendamentoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento dos Agendamentos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}