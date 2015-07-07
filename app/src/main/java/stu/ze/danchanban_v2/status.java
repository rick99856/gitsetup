package stu.ze.danchanban_v2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ze on 2015/7/5.
 */
public class status extends Activity implements SearchView.OnQueryTextListener{
    final List<NameValuePair> params = new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.status);
        Bundle bundle = this.getIntent().getExtras();

        String listna = bundle.getString("listname");
        String s = bundle.getString("ky");

        params.clear();
        params.add(new BasicNameValuePair("action", "get_list_all_data"));
        params.add(new BasicNameValuePair("access_token", s.toString()));
//                Log.v("btn3",txt_ky.getText().toString());
        params.add(new BasicNameValuePair("list_name", listna.toString()));
        //Log.v("keyid", ed_cban.getText().toString());

        new LoadingDataAsyncTask().execute();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
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



//                1是用來取得LIST的

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
//                        listarr = new ArrayAdapter<String>(status.this,android.R.layout.simple_spinner_item, Brr);
//                        list.setAdapter(listarr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }



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
