package com.example.osiris.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IrrigacoesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public IrrigacoesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Fragmento das irrigações");
    }

    public LiveData<String> getText() {
        return mText;
    }
}