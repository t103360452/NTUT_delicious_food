package com.example.jesse.finalproject_2016_12_25;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Button main_information, main_map, main_add_new, main_choose;
    ListView list;
    TextView output;
    static final String DB_name = "DBfood";
    static final String TB_name = "TBfood";
    static final String[] FROM = new String[]{"name", "phone", "address", "latitude", "longitude", "menu"};
    String buffer_name=" ",buffer_phone=" null",buffer_address="null ",buffer_latitude=" null",buffer_longitude="null ",buffer_introduce="null ";
    SQLiteDatabase db;

    TextView location_test;
    double Buffer_latitude,Buffer_longitude;
    LocationManager mgr;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
        location_test=(TextView)findViewById(R.id.location_test);


        //取得畫面上的元件
        main_information = (Button) findViewById(R.id.main_information);
        main_map = (Button) findViewById(R.id.main_map);
        main_add_new = (Button) findViewById(R.id.main_new);
        main_choose = (Button) findViewById(R.id.main_choose);
        list=(ListView) findViewById(R.id.listview);
        output=(TextView)findViewById(R.id.textView2);
        Cursor cur;  //存放查詢結果的Cursor

        //開啟或建立資料庫
        db = openOrCreateDatabase(DB_name, Context.MODE_PRIVATE, null);

        //建立資料表
        String createTable = "CREATE TABLE IF NOT EXISTS " +TB_name+ "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name VARCHAR(32), " +
                "phone VARCHAR(16), " +
                "address VARCHAR(64), " +
                "latitude VARCHAR(10), " +
                "longitude VARCHAR(10),"+
                "introduce VARCHAR(500),"+
                "distance VARCHAR(20))";
        db.execSQL(createTable);

        //查詢資料
        cur = db.rawQuery("SELECT * FROM " + TB_name, null);

        //若是空的寫入資料
        if (cur.getCount() == 0) {
            addData("摩斯漢堡", "0223924364", "台北市中正區忠孝東路二段132號", "25.042415", "121.531899","摩斯漢堡是一知名日本連鎖速食餐廳，創辦人是櫻田慧，營運者是摩斯食品服務公司。是一間在日本東京地區起家的連鎖速食餐廳，至今已是間國際性的跨國連鎖餐廳，但其主要的分布點還是集中在於亞洲地區。","0");
            addData("老德記手工拉麵店", "0223510929", "台北市中正區八德路一段82巷9弄3號", "25.043635", "121.531647","所謂的AB麵就是炸醬+辣醬的雙醬組合，「老德記手工拉麵」的炸醬是眷村口味的，有小豆乾跟肉末，撲鼻而來的香氣十分迷人，獨家辣醬，香辣夠味，一旁的黃瓜絲增加爽脆的口感。","0");
            addData("咖食堂", "0223965252", "台北市中正區八德路一段82巷9弄13號", "25.043568", "121.531448","在食材等級與售價間，做一個完美的平衡，讓許多的朋友都可以用實惠的價格吃到高級餐廳的美味，咖食堂的新產品馬來西亞咖哩更是嚴選當地出產的咖哩粉、製作工法講究，咖食堂想要呈現的就是道地的味道；我們沒有高級的裝潢、華麗的包裝，有的是顧客的支持、廚師的用心；咖食堂誠摯的邀請您來品嘗正統道地的異國美味。","0");
            addData("原凱養生精緻便當","0223435552","台北市中正區八德路一段82巷9弄","25.043561","121.531278","是一個超級好吃的便當，有烏骨雞便當，茶鵝便當，這裡的便當都很不同，很推薦來吃","0");
            addData("森本家拉麵","0223931511","台北市中正區忠孝東路二段134巷20號","25.040788","121.531664","森本家拉麵。免費加麵！客製化麵體湯頭。無服務費的美味日式拉麵。忠孝新生站附近雨天夜晚的暖心選擇","0");
            addData("麥當勞","0223958275","台北市中正區濟南路二段66號","25.040086","121.532622","麥當勞是一家總部設於美國的跨國連鎖快餐店，也是全球最大的快餐連鎖店，主要販售漢堡包及薯條、炸雞、碳酸飲料、冰品、沙拉、水果、美式热咖啡等快餐食品。","0");
            addData("甘泉魚麵","0223224030","台北市中正區台北市大安區新生南路一段112之1","25.039369","121.53263","大陸湖北省(武漢)是魚麵發展的故鄉，湖北省的雲夢縣更是眾家魚麵發展的起始店。近年來隨著需求的提升與經濟環境的變遷，雲夢魚麵已逐步發展成為行銷全國著名麵食。本公司引進魚麵製作調理技術後，為適應國內消費者的口味需求，乃憑藉調理師對日本料理、西餐、東南亞及中國料理的實務操作經驗，調配出融合各大餐系的口味菁華的創作。","0");

        }
        cur = db.rawQuery("SELECT * FROM " + TB_name, null);


        //建立ListView清單內容
        myAdapter adapter = new myAdapter(cur);
        list.setAdapter(adapter);


        //建立ListView監聽
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cur_onClick=db.rawQuery("SELECT * FROM " + TB_name, null);
                cur_onClick.moveToPosition(i);
                String str ="名稱:"+cur_onClick.getString(1)+
                        "\n電話:"+cur_onClick.getString(2)+
                        "\n地址:"+cur_onClick.getString(3);
                output.setText(str);
                //把資料存到buffer
                buffer_name=cur_onClick.getString(1);
                buffer_phone=cur_onClick.getString(2);
                buffer_address=cur_onClick.getString(3);
                buffer_latitude=cur_onClick.getString(4);
                buffer_longitude=cur_onClick.getString(5);
                buffer_introduce=cur_onClick.getString(6);

            }
        });

        //按鈕監聽:新增美食
        main_add_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.setClass(MainActivity.this,Add_new.class);
                startActivityForResult(i,0);
            }
        });

        //按鈕監聽:路線規劃
        main_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,     Uri.parse("http://maps.google.com/maps?saddr="+String.format("%f",Buffer_latitude)+", "+String.format("%f",Buffer_longitude)+"&daddr="+buffer_latitude+","+buffer_longitude));
                startActivity(intent);
            }
        });

        //按鈕監聽:店家詳細資料
        main_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();

                Bundle bundle=new Bundle();
                bundle.putString("name",buffer_name);
                bundle.putString("phone",buffer_phone);
                bundle.putString("address",buffer_address);
                bundle.putString("introduce",buffer_introduce);
                i.putExtras(bundle);

                i.setClass(MainActivity.this,information.class);
                startActivityForResult(i,0);

            }
        });

        //按鈕監聽:電腦自動配對
        main_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cur_choose=db.rawQuery("SELECT * FROM " + TB_name, null);
                int ramdom=(int)(Math.random()*cur_choose.getCount()); //隨機取數從1開始

                cur_choose.moveToPosition(ramdom);
                String str ="名稱:"+cur_choose.getString(1)+
                        "\n電話:"+cur_choose.getString(2)+
                        "\n地址:"+cur_choose.getString(3);
                output.setText(str);
                //把資料存到buffer
                buffer_name=cur_choose.getString(1);
                buffer_phone=cur_choose.getString(2);
                buffer_address=cur_choose.getString(3);
                buffer_latitude=cur_choose.getString(4);
                buffer_longitude=cur_choose.getString(5);
                buffer_introduce=cur_choose.getString(6);

            }
        });


    }

    private void addData(String name, String phone, String address, String latitude, String longitude, String introduce,String d) {
        ContentValues cv = new ContentValues(7);
        cv.put(FROM[0], name);
        cv.put(FROM[1], phone);
        cv.put(FROM[2], address);
        cv.put(FROM[3], latitude);
        cv.put(FROM[4], longitude);
        cv.put("introduce",introduce);
        cv.put("distance",d);

        db.insert(TB_name, null, cv);
    }

//取得GPS的實做

    @Override
    protected void onResume() {
        super.onResume();
        String best = mgr.getBestProvider(new Criteria(), true);
        if (best != null) {
            location_test.setText("取得定位資訊中...");

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            mgr.requestLocationUpdates(best, 5000, 5, this);

        } else {
            location_test.setText("請確認已開啟定位功能");
        }
    }
    @Override
    public void onLocationChanged(Location location) {

        location_test.setText("已改變位置"+(++count)+"次");
        //buffer_longitude=String.format("%f",location.getLongitude());
        //buffer_latitude=String.format("%f",location.getLatitude());
        Buffer_latitude=location.getLatitude();
        Buffer_longitude=location.getLongitude();

        //改變公里數

        list=(ListView)findViewById(R.id.listview);
        Cursor cur = db.rawQuery("SELECT * FROM " + TB_name, null);

        //設定各個距離
        for(int i=1;i<=cur.getCount();i++)
        {
            cur.moveToPosition(i-1);
           ContentValues cv = new ContentValues(7);
            cv.put(FROM[0], cur.getString(1));
            cv.put(FROM[1], cur.getString(2));
            cv.put(FROM[2], cur.getString(3));
            cv.put(FROM[3], cur.getString(4));
            cv.put(FROM[4], cur.getString(5));
            cv.put("introduce",cur.getString(6));
            cv.put("distance",String.format("%.1f",Distance(Buffer_latitude,Buffer_longitude,Double.parseDouble(cur.getString(4)),Double.parseDouble(cur.getString(5)))));

            db.update(TB_name,cv,"_id="+cur.getInt(0),null);
        }
        //可能有新增/刪除資料，所以要重新設定listView
        cur = db.rawQuery("SELECT * FROM " + TB_name, null);
        myAdapter adapter = new myAdapter(cur);
        list.setAdapter(adapter);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    //自訂Adapter並引入資料庫
    public class myAdapter extends BaseAdapter {
        Cursor myAdapterCursor;
        boolean[] myAdapterCB;

        public myAdapter(Cursor c )
        {
            myAdapterCursor = c;
            myAdapterCursor.moveToFirst();
        }


        @Override
        public int getCount() {
            return myAdapterCursor.getCount();
        }

        @Override
        public Object getItem(int i) {
            myAdapterCursor.moveToPosition(i);
            return myAdapterCursor.getString(1);
        }

        @Override
        public long getItemId(int i) {
            myAdapterCursor.moveToPosition(i);
            return myAdapterCursor.getLong(0);

        }

        @Override
        public View getView(int i, View rowView, ViewGroup parent) {

            View rowview = rowView;

            if (rowview == null)
            {
                LayoutInflater inflater = getLayoutInflater();
                rowview = inflater.inflate(R.layout.activity_spinner_unit, parent, false);
            }


            TextView listview_name = (TextView) rowview.findViewById(R.id.spinner_name);
            TextView listview_address=(TextView)rowview.findViewById(R.id.spinner_address);
            TextView listview_distance=(TextView)rowview.findViewById(R.id.spinner_distance);

            myAdapterCursor.moveToPosition(i);
            listview_name.setText(myAdapterCursor.getString(1));
            listview_address.setText(myAdapterCursor.getString(3));
            listview_distance.setText(myAdapterCursor.getString(7));


            return rowview;
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        db.execSQL("DROP TABLE "+TB_name);
        db.close();
    }
//Intent回來要做的事情
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode == 0){
            if(resultCode == 101){
                //可能有新增/刪除資料，所以要重新設定listView
                list=(ListView)findViewById(R.id.listview);
                Cursor cur = db.rawQuery("SELECT * FROM " + TB_name, null);
                myAdapter adapter = new myAdapter(cur);
                list.setAdapter(adapter);
            }
            else if(resultCode == 102){

            }
        }

    }

    //帶入使用者及景點店家經緯度可計算出距離
    public double Distance(double longitude1, double latitude1, double longitude2,double latitude2)
    {
        double radLatitude1 = latitude1 * Math.PI / 180;
        double radLatitude2 = latitude2 * Math.PI / 180;
        double l = radLatitude1 - radLatitude2;
        double p = longitude1 * Math.PI / 180 - longitude2 * Math.PI / 180;
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(l / 2), 2)
                + Math.cos(radLatitude1) * Math.cos(radLatitude2)
                * Math.pow(Math.sin(p / 2), 2)));
        distance = distance * 6378137.0;
        distance = Math.round(distance * 10000) / 10000;

        return distance ;
    }
}
