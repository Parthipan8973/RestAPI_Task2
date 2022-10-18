package com.example.restapitask2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.restapitask2.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    String setText1,setText2,id,firstName,lastName, API = "https://mocki.io/v1/212d8621-fe4e-4507-a201-868c5e7a7505", API2 = "https://reqres.in/api/users?page=2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      activityMainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        activityMainBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Astnctsk astnctsk = new Astnctsk();
                astnctsk.execute();

            }
        });

    }

    private class Astnctsk extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            HTTPsClass httPsClass = new HTTPsClass();

           String result = httPsClass.makeServiceCall(API);
           String resultAPI2 = httPsClass.makeServiceCall(API2);

            try {
                JSONObject jsonObject = new JSONObject(result);
               JSONObject jsonObject1 = jsonObject.getJSONObject("glossary");
              JSONObject jsonObject2 =  jsonObject1.getJSONObject("GlossDiv");

             setText1 = jsonObject2.getString("title");
             JSONObject jsonObject3 = jsonObject2.getJSONObject("GlossList");
             JSONObject jsonObject4 = jsonObject3.getJSONObject("GlossEntry");
             setText2 = jsonObject4.getString("ID");

             JSONObject jsonObject5 = new JSONObject(resultAPI2);
             JSONArray jsonArray = jsonObject5.getJSONArray("data");
             JSONObject jsonObject6 = jsonArray.getJSONObject(1);
             id = jsonObject6.getString("id");
             firstName = jsonObject6.getString("first_name");
             lastName = jsonObject6.getString("last_name");


            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            activityMainBinding.textView2.setText(setText1);
            activityMainBinding.textView3.setText(setText2);
            activityMainBinding.newApiTextView.setText("ID Number "+id+" is "+firstName+" "+lastName);
        }
    }
}