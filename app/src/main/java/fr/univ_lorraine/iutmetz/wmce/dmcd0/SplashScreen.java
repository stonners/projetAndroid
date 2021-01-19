package fr.univ_lorraine.iutmetz.wmce.dmcd0;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 3000;
    private ArrayList listeCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);

        final int SPLASH_TIME_OUT = 3000;
//a denoté aquand le volleye st fini

/*        this.listeCategories = new ArrayList<>();
        new Handler().postDelayed(
            ()-> CategorieDAO.findAll(this),
            SPLASH_TIME_OUT);
*/

        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
