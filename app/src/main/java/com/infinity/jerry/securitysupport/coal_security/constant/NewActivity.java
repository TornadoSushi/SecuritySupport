package com.infinity.jerry.securitysupport.coal_security.constant;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.infinity.jerry.securitysupport.R;

/**
 * Created by jerry on 2017/12/12.
 */

public class NewActivity extends Activity implements View.OnClickListener { //==> View V

    private TextView fuck1;
    private TextView fuck2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity);

        fuck1 = findViewById(R.id.fuck1);
        fuck2 = findViewById(R.id.fuck2);

        fuck1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {
                Toast.makeText(NewActivity.this, "ahahahahah", Toast.LENGTH_LONG).show();
            }
        });

        fuck2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(NewActivity.this, "22222222", Toast.LENGTH_LONG).show();

    }


}
