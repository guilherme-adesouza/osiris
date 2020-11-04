package com.example.osiris.ui.dashboard;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.osiris.Class.ApiConnection;
import com.example.osiris.Models.Dado;
import com.example.osiris.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private ArrayList<Dado> listaDados = new ArrayList<>();
    private LineChart chartLuminosidade;
    private TextView labelLuminosidade;
    private BarChart chartUmidade;
    private TextView labelUmidade;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        chartLuminosidade = root.findViewById(R.id.chart_luminosidade);
        labelLuminosidade = root.findViewById(R.id.lblLuminosidade_infLuminosidade);
        chartUmidade = root.findViewById(R.id.chart_umidade);
        labelUmidade = root.findViewById(R.id.lblUmidade_infUmidade);

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });



        return root;
    }




    @Override
    public void onResume() {
        super.onResume();
        montaGraficoLuminosidade();
        montaGraficoUmidade(chartUmidade);
    }

    private void montaGraficoUmidade(BarChart grafico){
        try {

            ArrayList<BarEntry> values1 = new ArrayList<>();

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            BarDataSet barDataSet1 = null;
            int size = 0;

            //consulta dos agendamentos cadastrados e adição a lista
            JSONArray dados = ApiConnection.makeGet(null, ApiConnection.TABLE_DATA);
            if (dados.length() > 15) {
                size = 15;
            } else {
                size = dados.length();
            }
            listaDados.clear();
            try {
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = dados.getJSONObject(i);
                    Dado dado = new Dado();

                    dado.setLuminosity(jsonObject.optString("luminosity").toString());
                    dado.setHumidity(jsonObject.optString("humidity").toString());
                    listaDados.add(dado);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            labelUmidade.setText("Últimos " + size + " dados");


            grafico.setFitBars(true);

            BarData barData = new BarData(dataSets);
            barData.setValueTextSize(12f);
            //barData.setBarWidth(0.5f);
            barData.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    //return super.getFormattedValue(value);

                    String retorno;
                    retorno =value+"";
                    return retorno;
                }
            });

            grafico.setExtraOffsets(0, 0, 0, 10);
            grafico.setData(barData);

            XAxis xAxis = grafico.getXAxis();
            //xAxis.setTypeface(tfLight);
            String[] periodos = new String[size];
            for (int i = 0; i < periodos.length; i++) {
                periodos[i] = i + 1 +"";
            }
            xAxis.setValueFormatter(new IndexAxisValueFormatter(periodos));

            //xAxis.setCenterAxisLabels(true);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            //xAxis.setTextSize(12f);
            xAxis.setTextColor(Color.BLACK);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);

            YAxis yAxis = grafico.getAxisLeft();
            yAxis.setAxisMinValue(0f); //deixa as colunas proporcionais
            yAxis.setDrawGridLines(false); // no grid lines
            yAxis.setDrawZeroLine(true); // draw a zero line
            grafico.getAxisRight().setEnabled(false); // no right axis
            //grafico.setVisibleXRangeMinimum(6);
            grafico.setVisibleXRangeMaximum(7);
            //grafico.moveViewToX(mesAtual-1);

            grafico.setDragEnabled(true);
            //grafico.setVisibleXRangeMaximum(2);

            grafico.getLegend().getCalculatedLineSizes();
            grafico.animateY(1000);
            grafico.setTouchEnabled(true);
            grafico.setPinchZoom(true);
            grafico.setDoubleTapToZoomEnabled(true);
            grafico.getDescription().setEnabled(false);

            Legend l = grafico.getLegend();
            l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
            l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
            l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            l.setDrawInside(false);
            l.setTextSize(13f);
            l.setEnabled(true);
            grafico.invalidate();

        } catch (Exception ex) {
            Log.e("GRAFICOS", ex.getMessage());
        }
    }

    private void montaGraficoLuminosidade(){
        try{

            //PREENCHE DADOS
            ArrayList<Entry> entries = new ArrayList<Entry>();
            int size = 0;
            Entry item;

            //consulta dos agendamentos cadastrados e adição a lista
            JSONArray dados = ApiConnection.makeGet(null, ApiConnection.TABLE_DATA);
            if (dados.length() > 15) {
                size = 15;
            } else {
                size = dados.length();
            }
            listaDados.clear();
            try {
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = dados.getJSONObject(i);
                    Dado dado = new Dado();

                    dado.setLuminosity(jsonObject.optString("luminosity").toString());
                    dado.setHumidity(jsonObject.optString("humidity").toString());
                    listaDados.add(dado);
                    item = new Entry( i + 1, Float.parseFloat(listaDados.get(i).getLuminosity()));
                    entries.add(item);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            LineDataSet lineDataSet = new LineDataSet(entries, "Luminosidade");
            lineDataSet.setDrawFilled(true);
            lineDataSet.setDrawValues(false);
            lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
            lineDataSet.setHighlightEnabled(true);
            lineDataSet.setLineWidth(3);
            lineDataSet.setColor(R.color.colorPrimary);
            lineDataSet.setCircleColor(R.color.colorPrimary);
            lineDataSet.setCircleRadius(5);
            lineDataSet.setCircleHoleRadius(3);
            lineDataSet.setDrawHighlightIndicators(true);
            lineDataSet.setHighLightColor(R.color.azul);
            lineDataSet.setValueTextSize(14);
            lineDataSet.setValueTextColor(R.color.colorPrimaryDark);

            LineData lineData = new LineData(lineDataSet);

            chartLuminosidade.getDescription().setText("");
            chartLuminosidade.getDescription().setTextSize(14);
            chartLuminosidade.setDrawMarkers(true);
            chartLuminosidade.getXAxis().setAxisMinimum(1);
            chartLuminosidade.getAxisLeft().setAxisMinimum(1);
            chartLuminosidade.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            chartLuminosidade.getXAxis().setGranularityEnabled(true);
            chartLuminosidade.getXAxis().setGranularity(1.0f);
            //chartDados.getXAxis().setLabelCount(lineDataSet.getEntryCount());
            chartLuminosidade.getXAxis().setDrawGridLines(false);
            chartLuminosidade.getAxisLeft().setDrawGridLines(false);
            chartLuminosidade.getAxisRight().setDrawGridLines(false);
            chartLuminosidade.getAxisRight().setEnabled(false); // no right axis

            //chartLactacao.setData(lineData);
            chartLuminosidade.setData(lineData);

            //Desativa o ZOOM do Touch
            chartLuminosidade.setDoubleTapToZoomEnabled(false);
            chartLuminosidade.setPinchZoom(false);

            //EFEITO DE ANIMAÇÃO
            chartLuminosidade.animateXY(500, 500);
            chartLuminosidade.invalidate();

            labelLuminosidade.setText("Últimos " + size + " dados");


        }catch (Exception ex){
            Log.e("CURVA_LACTA", ex.getMessage());
        }
    }
}