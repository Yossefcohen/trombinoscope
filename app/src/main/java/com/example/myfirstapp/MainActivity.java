package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.hipay.fullservice.core.client.config.ClientConfig;
import com.hipay.fullservice.core.errors.Errors;
import com.hipay.fullservice.core.errors.exceptions.ApiException;
import com.hipay.fullservice.core.models.Transaction;
import com.hipay.fullservice.core.requests.order.PaymentPageRequest;
import com.hipay.fullservice.screen.activity.PaymentScreenActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new NukeSSLCerts().nuke();

        ClientConfig.getInstance().setConfig(

                ClientConfig.Environment.Stage,
                "94676452.stage-secure-gateway.hipay-tpp.com",
                "Test_VJXgVC9lk6qbRPiybNr3M2xH"
        );
        Button paybutton=findViewById(R.id.pay_button);
        paybutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Random r = new Random();
        final String orderId =  "TEST_" + r.nextInt(100000);
        final Float amount = 10.00f;

        String server_url = "https://bits.hipay.org/ycohen/public_html/passphrase.php?amount=%1$s&order_id=%2$s";

        String url = String.format(server_url,amount, orderId);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // Assuming that the server's response is in JSON format
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            String signature = response.getString("signature");
                            String currency = response.getString("currency");

                            /* Once we get the signature, we can instantiate
                             * and present the payment screen */

                            PaymentPageRequest request = new PaymentPageRequest();

                            request.setOrderId(orderId);
                            request.setAmount(amount);
                            request.getCustomer().setFirstname("Martin");
                            request.getCustomer().setLastname("Dupont");
                            request.getCustomer().setEmail("tests@hipay.com");
                            request.getCustomer().setCity("PARIS");
                            request.getCustomer().setCountry("FR");

                            request.getCustomer().setStreetAddress("67 RUE CHABROL");
                            request.getCustomer().setZipCode("75019");
                            request.setShortDescription("Ma description");
                            request.getShippingAddress().setFirstname("Martin");
                            request.getShippingAddress().setLastname("Dupont");
                            request.setCurrency(currency);

                            PaymentScreenActivity.start(MainActivity.this,
                                    request,
                                    signature,
                                    null);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Eror", "onErrorResponse: Error");
                        // handle the error
                    }
                });

        requestQueue.add(jsObjRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PaymentPageRequest.REQUEST_ORDER) {

            Button payButton = findViewById(R.id.pay_button);

            if (resultCode == R.id.transaction_succeed) {
                Snackbar snackbar = Snackbar.make(payButton, "Transaction Succeed",
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.show();

                Bundle transactionBundle = data.getBundleExtra(Transaction.TAG);
                Transaction transaction = Transaction.fromBundle(transactionBundle);
                // Transaction object received, check its "state" property to know if the transaction was completed

            }else if (resultCode == R.id.transaction_failed) {
                Snackbar snackbar = Snackbar.make(payButton, "Transaction refused",
                        Snackbar.LENGTH_INDEFINITE);
                snackbar.show();

                Bundle exceptionBundle = data.getBundleExtra(Errors.TAG);
                ApiException exception = ApiException.fromBundle(exceptionBundle);
                // Payment workflow did fail, check the exception object for more info
            }
        }
    }

// permet de forcer le HTTPS sur le serveur bits

    public static class NukeSSLCerts {
        protected static final String TAG = "NukeSSLCerts";

        public static void nuke() {
            try {
                TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                                return myTrustedAnchors;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } catch (Exception e) {
            }
        }
    }

}