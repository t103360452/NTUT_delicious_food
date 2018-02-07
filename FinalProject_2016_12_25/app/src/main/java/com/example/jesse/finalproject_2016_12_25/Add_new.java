package com.example.jesse.finalproject_2016_12_25;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add_new extends AppCompatActivity {
    static final String DB_name = "DBfood";
    static final String TB_name = "TBfood";
    static final String[] FROM = new String[]{"name", "phone", "address", "latitude", "longitude", "menu"};

    Button back,sure;
    EditText textPhone,textAddress,textName;
    TextView output;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        back=(Button)findViewById(R.id.addNew_back);
        sure=(Button)findViewById(R.id.addNew_sure);

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textName=(EditText)findViewById(R.id.addNew_name);
                textAddress=(EditText)findViewById(R.id.addNew_address);
                textPhone=(EditText)findViewById(R.id.addNew_phone);

                String name=textName.getText().toString();
                String phone=textPhone.getText().toString();
                String address=textAddress.getText().toString();

                db = openOrCreateDatabase(DB_name, Context.MODE_PRIVATE, null);
                Cursor cur = db.rawQuery("SELECT * FROM " + TB_name, null);  //存放查詢結果的Cursor
                ContentValues cv = new ContentValues(5);
                cv.put(FROM[0], name);
                cv.put(FROM[1], phone);
                cv.put(FROM[2], address);
                cv.put(FROM[3],"25.042427");
                cv.put(FROM[4],"121.537846");

                Toast.makeText(Add_new.this, "成功建立" , Toast.LENGTH_SHORT).show();


                db.insert(TB_name, null, cv);
                db.close();

            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                setResult(101,i);
                finish();
            }
        });
    }
}
