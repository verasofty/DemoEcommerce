package com.onsigna.appdemoecommerce;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.onsigna.mposlib.CardDataCallback;
import com.onsigna.mposlib.CardDataConstants;
import com.onsigna.mposlib.CardDataFragment;
import com.onsigna.domain.AuthenticateData;
import com.sf.connectors.ISwitchConnector;
import com.sfmex.upos.reader.TransactionDataResult;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CardDataCallback {
    private final static String TAG = MainActivity.class.getSimpleName();
    private HashMap<String, String> data = new HashMap<String, String>();
    public static final String APLICATION_SECRET = "qs4qa1ralmgb4cna";
    public static final String APLICATION_KEY = "8z00pj9qxh3vaaggo7lfyw2xkj3rv80c7o1u";
    public static final String APLICATIONBUNDLE = "test.api.service";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        CardDataFragment cardData = new CardDataFragment();

        setServiceURL();
        setAuthenticateData();
        cardData.initParameters(this, buildHashMapForEcommerceTransaction(), this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, cardData, "Balance UP").addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setServiceURL() {
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        prefs.edit().putString(ISwitchConnector.SHARED_PREFERENCES_URL, this.getResources().getString(com.onsigna.mposlib.R.string.DEFAULT_URL))
                .commit();
    }

    private void setAuthenticateData() {
        Log.d(TAG, "== setAuthenticateData() ==");

        AuthenticateData.applicationBundle = APLICATIONBUNDLE;
        AuthenticateData.applicationKey = APLICATION_KEY;
        AuthenticateData.applicationSecret = APLICATION_SECRET;
    }

    private HashMap buildHashMapForEcommerceTransaction () {
        Log.i(TAG, "== buildHashMapForEcommerceTransaction ==");

        data.put(CardDataConstants.HMUser, "testTomaz@gmail.com");
        data.put(CardDataConstants.HMAmmount, "5.50");
        data.put(CardDataConstants.HMLatitude, "19.4257265");
        data.put(CardDataConstants.HMLongitude, "99.1696627");
        data.put(CardDataConstants.HMReferenceOne, "Referencia 1");
        data.put(CardDataConstants.HMReferenceTwo, "Referencia 2");

        return data;
    }

    @Override
    public void onCompletedTransaction(TransactionDataResult tdr) {
        Log.i(TAG, "== onCompletedTransaction() ==");

        if (tdr.getResponseCode() == 0) {
            Toast.makeText(getApplicationContext(), "¡¡¡Venta exitosa!!!\nNúmero de autorización -> " + tdr.getAuthorizationNumber(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "AuthorizationNumber --> " + tdr.getAuthorizationNumber() );
        } else {
            Toast.makeText(getApplicationContext(), "Descripción Error -> " + tdr.getResponseCodeDescription(), Toast.LENGTH_LONG).show();
            Log.i(TAG, "Error --> " + tdr.getResponseCodeDescription() );
        }

    }
}