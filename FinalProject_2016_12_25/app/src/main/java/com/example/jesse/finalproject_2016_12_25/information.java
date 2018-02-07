package com.example.jesse.finalproject_2016_12_25;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class information extends AppCompatActivity {
    TextView name,phone,intro,address;
    Button back,call;
    String buffer_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        name=(TextView)findViewById(R.id.information_name);
        phone=(TextView)findViewById(R.id.information_phone);
        address=(TextView)findViewById(R.id.information_address);
        intro=(TextView)findViewById(R.id.information_intro);
        back=(Button)findViewById(R.id.information_back);
        call=(Button)findViewById(R.id.information_call);

        Intent intent=this.getIntent();
        Bundle bundle=intent.getExtras();

       name.setText(bundle.getString("name"));
        address.setText(bundle.getString("address"));
        phone.setText(bundle.getString("phone"));
        intro.setText(bundle.getString("introduce"));
        buffer_phone=bundle.getString("phone");

        //返回按鈕監聽
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                setResult(102,i);
                finish();

            }
        });
        //撥打電話按鈕監聽
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent();
                it.setAction(Intent.ACTION_VIEW);
                it.setData(Uri.parse("tel:"+buffer_phone));

                startActivity(it);


            }
        });

    }
}
