package com.example.covidmeter;

import androidx.appcompat.app.AppCompatActivity;
//dinkarTaneja
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
//dinkarTaneja
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;
//dinkarTaneja
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView textViewCases,textViewActive,textViewRecovered,textViewCritical,textViewTotalTest,
    textViewTodayCases,textViewTodayDeath,textViewTotalDeath,textViewAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();

    }

    private void initialize() {
        textViewCases=findViewById(R.id.textViewCases);
        textViewActive=findViewById(R.id.textViewActive);
        textViewRecovered=findViewById(R.id.textViewRecovered);
        textViewCritical=findViewById(R.id.textViewCritical);
        textViewTodayCases=findViewById(R.id.textViewTodayCases);
        textViewTodayDeath=findViewById(R.id.textViewTodayDeath);
        textViewTotalDeath=findViewById(R.id.textViewTotalDeaths);
        textViewTotalTest=findViewById(R.id.textViewTotalTests);
        textViewAffectedCountries=findViewById(R.id.textViewAffectedCountries);


        simpleArcLoader=findViewById(R.id.arcLoader);
        scrollView=findViewById(R.id.scrollViewStats);
        pieChart=findViewById(R.id.piechart);


        fetchData();
    }

    private String convertText(String text){
        StringBuilder stringBuilder = new StringBuilder(text);
        for(int i = stringBuilder.length()-3;i>0;i-=3){
            stringBuilder.insert(i,",");
        }
        return stringBuilder.toString();
    }


    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/all";

        simpleArcLoader.start();;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response.toString());

                    String Active = jsonObject.getString("active");
                    String Recovered = jsonObject.getString("recovered");
                    String Critical = jsonObject.getString("critical");
                    String Deaths = jsonObject.getString("deaths");



                    textViewCases.setText(convertText(jsonObject.getString("cases")));
                    textViewActive.setText(convertText(jsonObject.getString("active")));
                    textViewRecovered.setText(convertText(jsonObject.getString("recovered")));
                    textViewCritical.setText(convertText(jsonObject.getString("critical")));
                    textViewTodayCases.setText(convertText(jsonObject.getString("todayCases")));
                    textViewTodayDeath.setText(convertText(jsonObject.getString("todayDeaths")));
                    textViewTotalDeath.setText(convertText(jsonObject.getString("deaths")));
                    textViewTotalTest.setText(convertText(jsonObject.getString("tests")));
                    textViewAffectedCountries.setText(convertText(jsonObject.getString("affectedCountries")));


                    pieChart.addPieSlice(new PieModel("Critical",Integer.parseInt(Critical), Color.parseColor("#F89D29")));
                    pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(Recovered), Color.parseColor("#66bb6a")));
                    pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(Active), Color.parseColor("#29b6f6")));
                    pieChart.addPieSlice(new PieModel("Total Deaths",Integer.parseInt(Deaths), Color.parseColor("#ff0000")));

                    pieChart.startAnimation();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);



                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,"Internet Issues",Toast.LENGTH_SHORT).show();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,"Internet Issues",Toast.LENGTH_SHORT).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void TrackCountriesButton(View view) {
        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
    }


}
