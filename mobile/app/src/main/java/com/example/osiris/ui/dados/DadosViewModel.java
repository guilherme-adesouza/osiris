package com.example.osiris.ui.dados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DadosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DadosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento dos Agendamentos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}