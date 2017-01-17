package br.com.evologica.imagegallery.sample;

/**
 * Created by marcussales on 17/01/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int SELECT_FILE = 0;
    public static final int REQUEST_CAMERA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            GridFragment gridFragment = new GridFragment();
            getFragmentManager().beginTransaction().add(R.id.fragment_gallery,gridFragment).commit();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getFragmentManager().findFragmentById(R.id.fragment_gallery).onActivityResult(requestCode,resultCode,data);
    }

}
