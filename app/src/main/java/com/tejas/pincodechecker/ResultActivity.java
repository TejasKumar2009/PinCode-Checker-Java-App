package com.tejas.pincodechecker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    LinearLayout pbLinearLayout;
    TextView statusTxt, districtTxt, regionTxt, stateTxt, countryTxt, pincodeTxt, titlePlacesTv;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ArrayList<String> placesNames = new ArrayList<String>();

        pbLinearLayout = findViewById(R.id.pbLinearLayout);
        listView = findViewById(R.id.listView);
        statusTxt = findViewById(R.id.statusTxt);
        districtTxt = findViewById(R.id.districtTxt);
        regionTxt = findViewById(R.id.regiontTxt);
        stateTxt = findViewById(R.id.stateTxt);
        countryTxt = findViewById(R.id.countryTxt);
        pincodeTxt = findViewById(R.id.pincodeTxt);
        titlePlacesTv = findViewById(R.id.titlePlacesTv);

        String pin_code = getIntent().getStringExtra("pin_code");
        String url = "https://api.postalpincode.in/pincode/"+pin_code;

        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        try {
                           String status = jsonArray.getJSONObject(0).getString("Status");

                           if (status.equals("Success")){
                               statusTxt.setVisibility(View.GONE);

                               JSONArray postOffices = jsonArray.getJSONObject(0).getJSONArray("PostOffice");
                               String district = postOffices.getJSONObject(0).getString("District");
                               String region = postOffices.getJSONObject(0).getString("Region");
                               String state = postOffices.getJSONObject(0).getString("State");
                               String country = postOffices.getJSONObject(0).getString("Country");
//
                               districtTxt.setText("DISTRICT - " + district);
                               regionTxt.setText("REGION - " + region);
                               stateTxt.setText("STATE - " + state);
                               countryTxt.setText("COUNTRY - " + country);
                               pincodeTxt.setText("PIN CODE - " + pin_code);

                               for (int i=0; i<postOffices.length(); i++){
                                   JSONObject postOfficeObj = postOffices.getJSONObject(i);
                                   placesNames.add(postOfficeObj.getString("Name"));
                               }

                               ArrayAdapter arrayAdapter = new ArrayAdapter<String>(ResultActivity.this, android.R.layout.simple_list_item_1, placesNames);
                               listView.setAdapter(arrayAdapter);

                               pbLinearLayout.setVisibility(View.GONE);
                               districtTxt.setVisibility(View.VISIBLE);
                               regionTxt.setVisibility(View.VISIBLE);
                               stateTxt.setVisibility(View.VISIBLE);
                               countryTxt.setVisibility(View.VISIBLE);
                               pincodeTxt.setVisibility(View.VISIBLE);
                               titlePlacesTv.setVisibility(View.VISIBLE);
                               listView.setVisibility(View.VISIBLE);

                               Log.d("msg", String.valueOf(placesNames));
                           }
                           else{
                               statusTxt.setVisibility(View.VISIBLE);
                               Log.d("msg", "Indirect Success");
                               
                           }

                        } catch (JSONException e) {
                            Log.e("err", "Error Happend" +e);
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("err", anError.toString());
                    }
                });

    }
}