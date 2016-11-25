package com.tacademy.chemi.common;

import android.app.ProgressDialog;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tacademy.chemi.model.Product;
import com.tacademy.chemi.model.ProductStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.tacademy.chemi.common.Common.URL;
import static com.tacademy.chemi.common.GlobalApplicationClass.TAG;

/**
 * Created by yoon on 2016. 11. 25..
 */

public class HttpRequest {

    // Progress dialog
    private static ProgressDialog pDialog;

    public static String jsonResponse;

    public static ArrayList<Product> products;

    public static void makeJsonObjectRequest() {

//        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                URL+"products/", new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
//                    String name = response.getString("name");
//                    String email = response.getString("email");
//                    JSONObject phone = response.getJSONObject("phone");
//                    String home = phone.getString("home");
//                    String mobile = phone.getString("mobile");
                    JSONArray dataArray = response.getJSONArray("data");
                    ArrayList<JSONObject> productObjects = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        productObjects.add(dataArray.getJSONObject(i));
                        Log.w("productObjects", dataArray.getJSONObject(i).toString());
                    }
                    products = new ArrayList<>();
                    for (int i = 0; i < dataArray.length(); i++) {
                        Product product = new Product();
                        product.setTitle(productObjects.get(i).getString("name"));
                        products.add(product);
                    }

                    for (Product product : products) {
                        Log.w("product name ", product.getTitle());
                    }

//                    jsonResponse = "";
//                    jsonResponse += "Name: " + name + "\n\n";
//                    jsonResponse += "Email: " + email + "\n\n";
//                    jsonResponse += "Home: " + home + "\n\n";
//                    jsonResponse += "Mobile: " + mobile + "\n\n";

                    ProductStorage productStorage = ProductStorage.get();
                    productStorage.setProducts(products);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("Error: ", e.getMessage());
                }
//                hidepDialog();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Log.e("onErrorResponse", error.getMessage());
                // hide the progress dialog
//                hidepDialog();
            }
        });

        // Adding request to request queue
        GlobalApplicationClass.getInstance().addToRequestQueue(jsonObjReq);

    }

    private static void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private static void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
