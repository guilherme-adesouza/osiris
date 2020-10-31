package com.example.osiris.ui.agendamentos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AgendmaneotsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AgendmaneotsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento das irrigações");

    }

    public LiveData<String> getText() {
        return mText;
    }
}