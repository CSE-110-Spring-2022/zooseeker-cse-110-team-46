package edu.ucsd.cse110.zooseeker46;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
<<<<<<< HEAD

import edu.ucsd.cse110.zooseeker46.search.SearchActivity;
=======
import android.view.View;
import android.widget.TextView;
>>>>>>> 271f5f7013e4789843b009fa84ac940fe387c72d

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}