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



    final List<NameValuePair> params = new ArrayList<NameValuePair>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.status);
        Bundle bundle = this.getIntent().getExtras();

        String listna = bundle.getString("listname");
        String s = bundle.getString("ky");
        Log.v("get the key",s.toString());

        initActionbar();
        names = loadData();
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<Object>(getApplicationContext(),
                android.R.layout.simple_expandable_list_item_1, names));

        //listView.setTextFilterEnabled(true);
        searchView.setOnQueryTextListener(this);
        searchView.setSubmitButtonEnabled(false);
        //SearchView�h���]�ק�^�j���ت��I�� �ק��?
        //setSearchViewBackground(searchView);



//        params.clear();
//        params.add(new BasicNameValuePair("action", "get_list_all_data"));
//        params.add(new BasicNameValuePair("access_token", s.toString()));
////                Log.v("btn3",txt_ky.getText().toString());
//        params.add(new BasicNameValuePair("list_name", listna.toString()));
//        //Log.v("keyid", ed_cban.getText().toString());
//
//        new LoadingDataAsyncTask().execute();

    }

    public void initActionbar() {
        // �۩w????
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
        mAllList.add("��");
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
            // �s�b�ǰt��?�u
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


//    public void setSearchViewBackground(SearchView searchView) {
//        try {
//            Class<?> argClass = searchView.getClass();
//            // ���w�Y?�p��?��
//            Field ownField = argClass.getDeclaredField("mSearchPlate"); // �`�NmSearchPlate���I���OstateListDrawable(���P??���P��?��)
//            // �ҥH�����BitmapDrawable
//            // setAccessible ���O��??�m�O�_��?��??�Ϯg?�����p��?�ʪ��A�u��?�m?true?�~�i�H??�A�q??false
//            ownField.setAccessible(true);
//            View mView = (View) ownField.get(searchView);
//            mView.setBackgroundDrawable(getResources().getDrawable(
//                    R.drawable.ic_menu_search));
//
//            // ���w�Y?�p��?��
//            Field mQueryTextView = argClass.getDeclaredField("mQueryTextView");
//            mQueryTextView.setAccessible(true);
//            Class<?> mTextViewClass = mQueryTextView.get(searchView).getClass()
//                    .getSuperclass().getSuperclass().getSuperclass();
//
//            // mCursorDrawableRes��??��Id��?��
//            // ???�ʬOTextView��?�ʡA�ҥH�n��mQueryTextView�]SearchAutoComplete�^����?�]AutoCompleteTextView�^����
//            // ?( EditText�^����?(TextView)
//            Field mCursorDrawableRes = mTextViewClass
//                    .getDeclaredField("mCursorDrawableRes");
//
//            // setAccessible ���O��??�m�O�_��?��??�Ϯg?�����p��?�ʪ��A�u��?�m?true?�~�i�H??�A�q??false
//            mCursorDrawableRes.setAccessible(true);
//            mCursorDrawableRes.set(mQueryTextView.get(searchView),
//                    R.drawable.ic_action_search);// �`�N�Ĥ@???����???��(mQueryTextView)��?�H(mSearchView)
//            // ��?��?�O�@??������O?��A�]?��?��???���A�@?�O�Ĥ@��?�o�J?��?�Ԫ�??��?���A�@?�O�Z?��?�e?�Ԫ�?���A�p�G��?���R��?�A�N?���h??����??���A?���R��??�u��r�M��?���Z�á]�Y�Ǧr��?�I��?��?�@�����^�C
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
    class LoadingDataAsyncTask extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... param) {

            return showData();
        }

        @Override
        protected void onPostExecute(String result) {
            int a=0;
            super.onPostExecute(result);



//                1�O�ΨӨ��oLIST��

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
