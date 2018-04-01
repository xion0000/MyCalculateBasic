package nu.vitsanu.hinkla.com.mycalculatebasic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import nu.vitsanu.hinkla.com.mycalculatebasic.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Add Fragment to activity
        if(savedInstanceState == null) {
           getSupportFragmentManager().beginTransaction()
                .add(R.id.contentMainFragment, new SplashScreen()).commit();
        }


    }   //Main method

    @Override
    public void onBackPressed() { // deactivate hardware back bottom
        //super.onBackPressed();
    }
}   //Main class
