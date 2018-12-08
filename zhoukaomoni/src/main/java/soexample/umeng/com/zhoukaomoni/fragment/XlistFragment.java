package soexample.umeng.com.zhoukaomoni.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.xlistviewflush.view.XListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

import soexample.umeng.com.zhoukaomoni.R;
import soexample.umeng.com.zhoukaomoni.WebViewActivity;
import soexample.umeng.com.zhoukaomoni.adapter.MyAdapter;
import soexample.umeng.com.zhoukaomoni.bean.MyData;
import soexample.umeng.com.zhoukaomoni.util.ListHttpUtil;

public class XlistFragment extends Fragment implements XListView.IXListViewListener, View.OnClickListener {


    private XListView My_XList;
    private View v;
    private String mUrl = "http://www.xieast.com/api/news/news.php?page=";
    private MyAdapter adapter;
    private ArrayList<MyData.DataBean> datas = new ArrayList<>();
    private int index = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_xlist, null);
        initView(v);
        My_XList.setPullLoadEnable(true);
        //设置监听事;
        My_XList.setXListViewListener(this);
        //创建适配器
        adapter = new MyAdapter(getActivity(), datas);
        //设置适配器
        My_XList.setAdapter(adapter);
        new MyTask().execute(mUrl + index);

        My_XList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                String url = datas.get(position - 1).getUrl();
                String img = datas.get(position - 1).getThumbnail_pic_s02();
                intent.putExtra("uu", url);
                intent.putExtra("img", img);

                startActivity(intent);


            }
        });

        return v;

    }

    private void initView(View v) {
        My_XList = v.findViewById(R.id.My_XList);
    }

    @Override
    public void onClick(View v) {

    }

    //进行异步加载
    class MyTask extends AsyncTask<String, Void, ArrayList<MyData.DataBean>> {
        @Override
        protected ArrayList<MyData.DataBean> doInBackground(String... strings) {
            //进行解析
            String jsonstr = ListHttpUtil.getStr(strings[0]);
            Gson gson = new Gson();
            MyData myData = gson.fromJson(jsonstr, MyData.class);
            return (ArrayList<MyData.DataBean>) myData.getData();
        }

        @Override
        protected void onPostExecute(ArrayList<MyData.DataBean> dataBeans) {
            super.onPostExecute(dataBeans);
            datas.addAll(dataBeans);
            //刷新适配器
            adapter.notifyDataSetChanged();
            //停止刷新动画
            stopRefresh();
        }
    }

    private void stopRefresh() {
        //停止刷新
        My_XList.stopRefresh();
        My_XList.stopLoadMore();
        //显示刷新时间
        Date date = new Date();
        String datast = (String) android.text.format.DateFormat.format("EEEE, MMMM dd日, yyyy kk:mm:ss", date);
        My_XList.setRefreshTime(datast);


    }

    @Override
    public void onRefresh() {
        //上拉刷新
        datas.clear();
        index = 1;
        new MyTask().execute(mUrl + index);
        stopRefresh();
    }

    @Override
    public void onLoadMore() {
        //下拉加载
        new MyTask().execute(mUrl + (index++));
    }
}
