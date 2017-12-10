package com.miast.guessthenumerus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int randomNumber;
    int maxNumber = 99;
    int minNumber = 1;
    int lowestNumber = minNumber;
    int highestNumber = maxNumber;
    private EditText inputNumber;

    AdView mAdView;

    public void guessButton (View view)
    {
        inputNumber = (EditText) findViewById(R.id.editTextNumber);

       // makeToast("R:" +randomNumber);

        if(TextUtils.isEmpty(inputNumber.getText().toString()))
        {
            makeToast("Please input a number.");
        }
        else {
            int finalValue = Integer.parseInt(inputNumber.getText().toString());

            if (finalValue == minNumber || finalValue == maxNumber) {
                makeToast("Please input a number within the range of: " + lowestNumber + " to " + highestNumber);
            } else if (finalValue < randomNumber) {
                if (finalValue <= lowestNumber) {
                    makeToast("Please input a number within the range of: " + lowestNumber + " to " + highestNumber);
                } else {
                    lowestNumber = finalValue;
                    textUpdate("The number is between " + lowestNumber + " to " + highestNumber);
                    makeToast("Number guess: " + finalValue);
                }
            } else if (finalValue > randomNumber) {
                if (finalValue >= highestNumber) {
                    makeToast("Please input a number within the range of: " + lowestNumber + " to " + highestNumber);
                } else {
                    highestNumber = finalValue;
                    textUpdate("The number is between " + lowestNumber + " to " + highestNumber);
                    makeToast("Number guess: " + finalValue);
                }
            } else {
                textUpdate("You've guessed it! The number is " + finalValue);
                resetButton();
            }
            inputNumber.getText().clear();
        }
    }

    private void resetButton() {
        Random random = new Random();
        randomNumber = random.nextInt(maxNumber) +1;
        lowestNumber = minNumber;
        highestNumber = maxNumber;
        Toast.makeText(MainActivity.this, "A new number is generated." ,Toast.LENGTH_SHORT).show();
    }

    public void resetButton(View view){
        Random random = new Random();
        randomNumber = random.nextInt(maxNumber) +1;
        lowestNumber = minNumber;
        highestNumber = maxNumber;
        EditText inputNumber = (EditText) findViewById(R.id.editTextNumber);
        inputNumber.getText().clear();
        textUpdate("The number is between "+ minNumber + " to " + maxNumber);
        Toast.makeText(MainActivity.this, "A new number is generated." ,Toast.LENGTH_SHORT).show();
    }

    public void makeToast(String string){
        Toast.makeText(MainActivity.this, string ,Toast.LENGTH_SHORT).show();
    }

    public void textUpdate(String string){
        TextView textViewUpdate = (TextView) findViewById(R.id.updateText);
        textViewUpdate.setText(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textUpdate("The number is between "+ lowestNumber + " to " + highestNumber);
        resetButton();// Prepare the Interstitial Ad

        MobileAds.initialize(getApplicationContext(),
                getString(R.string.banner_ad_unit_id));
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                // Check the LogCat to get your test device ID
                .addTestDevice("7B5C90BA62D0D8B942FD8FF7225A1038")
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
