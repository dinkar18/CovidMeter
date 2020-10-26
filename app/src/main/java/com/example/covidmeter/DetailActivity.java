package com.example.covidmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//dinkarTaneja
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView textViewCountryName,textViewCases,textViewActive,textViewRecovered,textViewCritical,textViewTotalTest,
            textViewTodayCases,textViewTodayDeath,textViewTotalDeath,textViewcasespermillion
            ,textViewdeathpermillion;

    private int positionCountry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent =getIntent();
        positionCountry = intent.getIntExtra("position",0);


        getSupportActionBar().setTitle("Details of "+AffectedCountries.countryModelList.get(positionCountry).getCountry());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);





        initiliaze();
    }

    private String convertText(String text){
        StringBuilder stringBuilder = new StringBuilder(text);
        for(int i = stringBuilder.length()-3;i>0;i-=3){
            stringBuilder.insert(i,",");
        }
        return stringBuilder.toString();
    }


    private void initiliaze() {
        textViewCountryName=findViewById(R.id.textViewCountryNameDetail);
        textViewCases=findViewById(R.id.textViewCasesDetail);
        textViewActive=findViewById(R.id.textViewActiveDetail);
        textViewRecovered=findViewById(R.id.textViewRecoveredDetail);
        textViewCritical=findViewById(R.id.textViewCriticalDetail);
        textViewTodayCases=findViewById(R.id.textViewTodayCasesDetail);
        textViewTodayDeath=findViewById(R.id.textViewTodayDeathsDetail);
        textViewTotalDeath=findViewById(R.id.textViewDeathsDetail);
        textViewTotalTest=findViewById(R.id.textViewTestDetail);
        textViewcasespermillion=findViewById(R.id.textViewcasesPerOneMillionDetail);
        textViewdeathpermillion=findViewById(R.id.textViewdeathsPerOneMillionDetails);




        textViewCountryName.setText(AffectedCountries.countryModelList.get(positionCountry).getCountry());
        textViewCases.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getCases()));
        textViewRecovered.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getRecovered()));
        textViewActive.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getActive()));
        textViewTotalDeath.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getDeaths()));
        textViewCritical.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getCritical()));
        textViewTodayCases.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getTodayCases()));
        textViewTodayDeath.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getTodayDeaths()));
        textViewdeathpermillion.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getDeathsPerOneMillion()));
        textViewcasespermillion.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getCasesPerOneMillion()));
        textViewTotalTest.setText(convertText(AffectedCountries.countryModelList.get(positionCountry).getTests()));


    }





    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
