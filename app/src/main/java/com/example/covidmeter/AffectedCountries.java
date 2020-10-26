package com.example.covidmeter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//dinkarTaneja
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
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
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AffectedCountries extends AppCompatActivity implements AdapterView.OnItemClickListener {

    EditText editTextSearch;
    ListView listViewCountries;
    SimpleArcLoader simpleArcLoader;

    public static List<CountryModel> countryModelList = new ArrayList<>();
    CountryModel countryModel;
    myAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);
        initiliaze();
        getSupportActionBar().setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void initiliaze() {


        editTextSearch = findViewById(R.id.editTextSearch);
        listViewCountries = findViewById(R.id.listViewCountries);
        simpleArcLoader = findViewById(R.id.arcLoaderCountries);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myAdapter.getFilter().filter(s);
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fetchData();

        listViewCountries.setOnItemClickListener(this);
    }

    private void fetchData() {
        String url = "https://corona.lmao.ninja/v2/countries";

        simpleArcLoader.start();;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){

                        JSONObject jsonObject= jsonArray.getJSONObject(i);



                        String countryName= jsonObject.getString("country");
                        String cases= jsonObject.getString("cases");
                        String todayCases= jsonObject.getString("todayCases");
                        String deaths= jsonObject.getString("deaths");
                        String todayDeaths= jsonObject.getString("todayDeaths");
                        String recovered= jsonObject.getString("recovered");
                        String active= jsonObject.getString("active");
                        String critical= jsonObject.getString("critical");
                        String casesPerOneMillion= jsonObject.getString("casesPerOneMillion");
                        String deathsPerOneMillion= jsonObject.getString("deathsPerOneMillion");
                        String tests= jsonObject.getString("tests");

                        JSONObject object = jsonObject.getJSONObject("countryInfo");
                        String flagUrl = object.getString("flag");


                        countryModel = new CountryModel(flagUrl,countryName,cases,todayCases,deaths,
                                todayDeaths,recovered,active,critical,casesPerOneMillion,deathsPerOneMillion,tests);
                        countryModelList.add(countryModel);

                    }
                    myAdapter = new myAdapter(AffectedCountries.this,countryModelList);
                    listViewCountries.setAdapter(myAdapter);
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);



                } catch (JSONException e) {
                    e.printStackTrace();
                    simpleArcLoader.stop();
                    simpleArcLoader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AffectedCountries.this,error.getMessage() ,Toast.LENGTH_SHORT).show();
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(getApplicationContext(),DetailActivity.class).putExtra("position",position));
    }
}
