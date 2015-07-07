package stu.ze.danchanban_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    EditText ed_id,ed_pwd;
    Button btn_login;
    String key = "";
    final List<NameValuePair> params = new ArrayList<NameValuePair>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_id = (EditText)findViewById(R.id.ed_id);
        ed_pwd = (EditText)findViewById(R.id.ed_pwd);
        btn_login =(Button)findViewById(R.id.btn_login);

        ed_id.setText("s13113241");
        ed_pwd.setText("zx0956950105");
        btn_login.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                key = "";

                if (ed_id.getText().length() == 0 || ed_pwd.getText().length() == 0) {
                    Toast.makeText(getApplication(), "帳號或密碼是空的", Toast.LENGTH_SHORT).show();
                } else {
                    params.add(new BasicNameValuePair("action", "get_access_token"));
                    params.add(new BasicNameValuePair("user", ed_id.getText().toString()));
                    params.add(new BasicNameValuePair("pwd", ed_pwd.getText().toString()));

                    new LoadingDataAsyncTask().execute();


                }


            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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


            try{
                JSONObject mainObject = new JSONObject(result);
                JSONObject uniObject = mainObject.getJSONObject("data");
                String uniName = uniObject.getString("token");
                //Log.e("12345", uniName);
                key = uniName;

                    Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
                    Log.v("key value ",key);
                Intent intent = new Intent(MainActivity.this, Danchanban.class);
                intent.putExtra("abc", key.toString());
                //把字串傳到第二個Activity
                startActivity(intent);



//                        txt_ky.setText(uniName);
                Log.v("123",key.toString());




            }catch(JSONException e){
                Log.v("123", e.toString());
                Toast.makeText(MainActivity.this,"帳號或密碼錯誤",Toast.LENGTH_SHORT).show();


            }



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
