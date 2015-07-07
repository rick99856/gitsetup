package stu.ze.danchanban_v2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ze on 2015/5/24.
 */
public class Danchanban extends Activity {
    public static int inn =0;
    final List<NameValuePair> params = new ArrayList<NameValuePair>();
    public static String[] listname;
    Spinner list;
    ArrayAdapter<String> listarr;
    JSONArray Arr;
    String [] Brr;
    String listname_inuse;
    String kkyy="";
    String date = "";
    Button btn_getlist,btn_qrcode,btn_find,btn_send,btn_status;
    EditText ed_cban,ed_site,ed_ps;
    TextView txt_name;
    String s;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.danchanban);
//        拿到key值
        Bundle bundle = this.getIntent().getExtras();
        s = bundle.getString("abc");
//        s is the key value;
        Log.v("activity 2 get key", s);
        btn_status = (Button)findViewById(R.id.btn_status);
        btn_getlist = (Button)findViewById(R.id.btn_getlist);
        btn_qrcode = (Button)findViewById(R.id.btn_qrcode);
        btn_find = (Button)findViewById(R.id.btn_find);
        btn_send = (Button)findViewById(R.id.btn_send);

        ed_cban = (EditText)findViewById(R.id.ed_cban);
        ed_ps = (EditText)findViewById(R.id.ed_ps);
        ed_site = (EditText)findViewById(R.id.ed_site);

        txt_name = (TextView)findViewById(R.id.txt_name);



        //拿清單名稱
        btn_getlist.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.v("kykykykykyky",txt_ky.getText().toString());
                if (s.toString() != null) {
                    params.clear();
                    params.add(new BasicNameValuePair("action", "get_list"));
                    params.add(new BasicNameValuePair("access_token", s.toString()));
                    inn = 1;
                    new LoadingDataAsyncTask().execute();
                }
            }
        });
        list = (Spinner)findViewById(R.id.list);
        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                if (Brr[position].equals("這是清單")) {

                } else {
                    Toast.makeText(getApplicationContext(), "你選的是" + Brr[position], Toast.LENGTH_SHORT).show();
                    listname_inuse = Brr[position];
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        btn_status.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent as = new Intent();
                as.setClass(Danchanban.this, status.class);
                as.putExtra("listname", listname_inuse);
                as.putExtra("ky",s.toString());
                startActivity(as);




            }
        });

        //qrcode 並查詢
        btn_qrcode.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
                params.clear();
                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                if (getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() == 0) {
                    // 未安裝

                    AlertDialog.Builder dialog = null;
                    dialog = new AlertDialog.Builder(Danchanban.this);
                    dialog.setTitle("Title");
                    dialog.setMessage("INFO");
                    dialog.setIcon(android.R.drawable.ic_dialog_alert);
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("去下載", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // 按下PositiveButton要做的事
                            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.zxing.client.android&hl=zh_TW");
                            Intent i = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(i);
                            Toast.makeText(Danchanban.this, "水喔", Toast.LENGTH_SHORT).show();

                        }
                    });
                    dialog.setNegativeButton("不要", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Toast.makeText(Danchanban.this, "那算了", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            Toast.makeText(Danchanban.this, "sorry", Toast.LENGTH_LONG).show();
                        }
                    });

                    dialog.show();
                    Log.v("安安安安", "No安裝");
//                    Toast.makeText(this, "請至 Play 商店安裝 ZXing 條碼掃描器", Toast.LENGTH_LONG).show();


                } else {
                    // SCAN_MODE, 可判別所有支援的條碼
                    // QR_CODE_MODE, 只判別 QRCode
                    // PRODUCT_MODE, UPC and EAN 碼
                    // ONE_D_MODE, 1 維條碼
                    intent.putExtra("SCAN_MODE", "SCAN_MODE");

                    // 呼叫ZXing Scanner，完成動作後回傳 1 給 onActivityResult 的 requestCode 參數
                    startActivityForResult(intent, 1);

                }

            }

        });

        btn_find.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub

                params.clear();
                params.add(new BasicNameValuePair("action", "get_asset"));
                params.add(new BasicNameValuePair("access_token", s.toString()));
//                Log.v("btn3",txt_ky.getText().toString());
                params.add(new BasicNameValuePair("asset_no", ed_cban.getText().toString()));
                Log.v("keyid",ed_cban.getText().toString());
                inn = 3;
                new LoadingDataAsyncTask().execute();

            }

        });


        btn_send.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ed_cban.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"沒有輸入產編",Toast.LENGTH_SHORT).show();
                }
                else{
                    params.clear();
                    params.add(new BasicNameValuePair("action", "write_checked_log"));
                    params.add(new BasicNameValuePair("access_token", s.toString()));
                    params.add(new BasicNameValuePair("list_name", listname_inuse));
                    params.add(new BasicNameValuePair("asset_no", ed_cban.getText().toString()));
                    params.add(new BasicNameValuePair("write_asset_note", "1"));
                    params.add(new BasicNameValuePair("new_location", ed_site.getText().toString()));
                    params.add(new BasicNameValuePair("comment", date.toString()));

                    inn = 2;
                    new LoadingDataAsyncTask().execute();
                }






            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode==1)
        {
            if(resultCode==RESULT_OK)
            {
                // ZXing回傳的內容
                String contents = intent.getStringExtra("SCAN_RESULT");
                if(contents.length()> 10){
                    ed_cban.setText(contents.toString().substring(0,10));
                    Log.v("txt_getqrcode",ed_cban.getText().toString());
//                    txt_id.setText(contents.toString().substring(10));

                }
                else{
                    ed_cban.setText(contents.toString());

                }

                params.clear();
                params.add(new BasicNameValuePair("action", "get_asset"));
                params.add(new BasicNameValuePair("access_token", s.toString()));
//                Log.v("btn3",txt_ky.getText().toString());
                params.add(new BasicNameValuePair("asset_no", ed_cban.getText().toString()));
                Log.v("btn3",ed_cban.getText().toString());
                inn = 3;
                new LoadingDataAsyncTask().execute();






            }
            else
            if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(Danchanban.this, "取消掃描", Toast.LENGTH_LONG).show();
            }
        }
    }

    class LoadingDataAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... param) {

            return showData();
        }

        @Override
        protected void onPostExecute(String result) {
            int a=0;
            super.onPostExecute(result);


            switch(inn){
//                1是用來取得LIST的
                case 1:
                    try {
                        JSONObject mainObject = new JSONObject(result);
                        //JSONObject uniObject = mainObject.getJSONObject("data");
                        Arr = mainObject.getJSONArray("data");
                        Brr= new String[Arr.length()];

                        for(int i =0;i<Arr.length();i++){
                            JSONObject qq = Arr.getJSONObject(i);
                            String str = qq.getString("list_name");
                            Brr[i] = str;
                            Log.v("JsonArr",str);

                        }
                        listarr = new ArrayAdapter<String>(Danchanban.this,android.R.layout.simple_spinner_item, Brr);
                        list.setAdapter(listarr);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;
////              2是做點財產的
                case 2:
                    try{
                        JSONObject mainObject = new JSONObject(result);
                        String uniString = mainObject.getString("msg");

                        if(uniString.toString().equals("Success.")){
                            Toast.makeText(getApplicationContext(),"成功了",Toast.LENGTH_SHORT).show();
                            ed_ps.setText(date.toString());

                        }
                        else{
                            Toast.makeText(getApplicationContext(),uniString.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        Log.v("123",e.toString());
                    }
                    break;
//                3是用產編去查名字、位置和備註用的
                case 3:
                    try {
                        JSONObject mainObject = new JSONObject(result);
                        Log.v("result",result);
                        JSONObject uniObject = mainObject.getJSONObject("data");
                        Log.v("AssetName",uniObject.getString("AssetName"));
                        String asname = uniObject.getString("AssetName");
                        txt_name.setText(asname);
                        Log.d("btn3的try", txt_name.getText().toString());
                        String newlocation = uniObject.getString("new_location");
                        ed_site.setText(newlocation);
                        Log.d("btn3的try", ed_site.getText().toString());
                        String comment = uniObject.getString("comment");
                        ed_ps.setText(comment);
                        Log.d("btn3的try", ed_ps.getText().toString());
                    } catch (JSONException e) {
                        Log.d("btn3的catch", txt_name.getText().toString());
                    }
                    break;
////                4是用來取得list清單名稱
//                case 4:
//                    try {
//                        JSONObject mainObject = new JSONObject(result);
//                        //JSONObject uniObject = mainObject.getJSONObject("data");
//                        Arr = mainObject.getJSONArray("data");
//                        Brr= new String[Arr.length()];
//
//                        for(int i =0;i<Arr.length();i++){
//                            JSONObject qq = Arr.getJSONObject(i);
//                            String str = qq.getString("list_name");
//                            Brr[i] = str;
//                            Log.v("JsonArr",str);
//
//                        }
//                        listarr = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_item, Brr);
//                        sp.setAdapter(listarr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//
//                    break;
            }





            Log.v("123456789", result);



        }






        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        public  String  showData() {
            String a = null;
            try {
                a=usepost.sendPost("http://assets.ecc.stu.edu.tw/api.php", getQuery(params));
                Log.d("parmes",getQuery(params));
                return a;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return a;

            }

        }

    }
    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }




}