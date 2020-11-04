package com.example.osiris.Class;

import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.List;

public class MyBarDataSet extends BarDataSet {

    private List<BarEntry> yVals;
    public MyBarDataSet(List<BarEntry> yVals, String label) {
        super(yVals, label);
        this.yVals = yVals;
    }

    @Override
    public int getColor(int index) {

        if(yVals.get(index).getY() < 0.5)
            return mColors.get(2);
        else if(yVals.get(index).getY() <= 0.7)
            return mColors.get(1);
        else
            return mColors.get(0);

        /*float indexVal = getEntryForXIndex(index).getVal();
        if(getEntryForXIndex(index).getVal() <= 0)
            return mColors.get(0);
        else if(getEntryForXIndex(index).getVal() <= 0.7)
            return mColors.get(1);
        else
            return mColors.get(2);*/

    }

}
