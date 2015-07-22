package stu.ze.danchanban_v2;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

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
 * Created by ze on 2015/7/5.
 */
public class status extends Activity implements SearchView.OnQueryTextListener{
    ListView listView;
    SearchView searchView;
    Object[] names;
    ArrayAdapter<String> adapter;
    ArrayList<String> mAllList = new ArrayList<String>();
    JSONArray Arr;
    String [] Brr;



    final List<NameValuePair> params = new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.status);
        Bundle bundle = this.getIntent().getExtras();

        String listna = bundle.getString("listname");
        String s = bundle.getString("ky");
        Log.v("get the key", s.toString());

        initActionbar();
        //names = loadData();
        listView = (ListView) findViewById(R.id.list);
//        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
//                android.R.layout.simple_expandable_list_item_1, names));

        //listView.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);

        params.clear();
        params.add(new BasicNameValuePair("action", "get_list_all_data"));
        params.add(new BasicNameValuePair("access_token", s.toString()));
        params.add(new BasicNameValuePair("list_name",listna.toString()));

        new LoadingDataAsyncTask().execute();

    }

    public void initActionbar() {
        // 自定义标题栏
        getActionBar().setDisplayShowHomeEnabled(false);
        getActionBar().setDisplayShowTitleEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);
        LayoutInflater mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mTitleView = mInflater.inflate(R.layout.custom_action_bar_layout,
                null);
        getActionBar().setCustomView(
                mTitleView,
                new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        searchView = (SearchView) mTitleView.findViewById(R.id.search_view);
    }
    public Object[] loadData() {
        mAllList.add("aa");
        mAllList.add("ddfa");
        mAllList.add("qw");
        mAllList.add("sd");
        mAllList.add("fd");
        mAllList.add("cf");
        mAllList.add("re");
        mAllList.add("我");
        return mAllList.toArray();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Object[] obj = searchItem(newText);
        updateLayout(obj);

        return false;
    }
    public Object[] searchItem(String name) {
        ArrayList<String> mSearchList = new ArrayList<String>();
        for (int i = 0; i < mAllList.size(); i++) {
            int index = mAllList.get(i).indexOf(name);

            if (index != -1) {
                mSearchList.add(mAllList.get(i));
            }
        }
        return mSearchList.toArray();
    }
    public void updateLayout(Object[] obj) {
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, obj));
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





                    try {
                        JSONObject mainObject = new JSONObject(result);
                        Log.v("getjson values",mainObject.toString());
                        //JSONObject uniObject = mainObject.getJSONObject("data");
                        Arr = mainObject.getJSONArray("data");
                        Brr= new String[Arr.length()];

                        for(int i =0;i<Arr.length();i++){
                            JSONObject qq = Arr.getJSONObject(i);
                            String str = qq.getString("AssetNo");
                            Brr[i] = str;
                            Log.v("JsonArr",str);

                        }
                        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
                            android.R.layout.simple_expandable_list_item_1, Brr));
                    } catch (JSONException e) {
                        e.printStackTrace();
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
                a=usepost.sendPost("http://keroro.stu.edu.tw/~s11113257/stu-assets/api.php", getQuery(params));
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
