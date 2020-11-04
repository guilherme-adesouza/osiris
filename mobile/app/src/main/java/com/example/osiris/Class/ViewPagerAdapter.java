package com.example.osiris.Class;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.osiris.ui.agendamentos.AgendamentosFragment;
import com.example.osiris.ui.dados.DadosFragment;
import com.example.osiris.ui.dashboard.DashboardFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new DadosFragment(); //ChildFragment1 at position 0
            case 1:
                return new AgendamentosFragment(); //ChildFragment2 at position 1
            case 2:
                return new DashboardFragment(); //ChildFragment3 at position 2
        }
        return null; //does not happen
    }

    @Override
    public int getCount() {
        return 3; //three fragments
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
