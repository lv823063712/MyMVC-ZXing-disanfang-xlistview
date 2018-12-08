package soexample.umeng.com.zhoukaomoni.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.pulltolibrary.PullToRefreshBase;
import com.example.pulltolibrary.PullToRefreshListView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import soexample.umeng.com.zhoukaomoni.R;
import soexample.umeng.com.zhoukaomoni.adapter.MyAdapter;
import soexample.umeng.com.zhoukaomoni.bean.MyData;
import soexample.umeng.com.zhoukaomoni.util.HttpUtil;
import soexample.umeng.com.zhoukaomoni.util.ListHttpUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShouYeFragment extends Fragment {

    private PullToRefreshListView My_Pull;
    private ArrayList<MyData.DataBean> datas = new ArrayList<>();
    private String mUrl = "http://www.xieast.com/api/news/news.php?page=";
    private MyAdapter adapter;
    private int index = 1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                datas.addAll((List<MyData.DataBean>) msg.obj);
                //刷新适配器
                adapter.notifyDataSetChanged();
                //取消适配器一直刷新
                My_Pull.onRefreshComplete();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_shou_ye, null);
        initView(v);
        adapter = new MyAdapter(getActivity(), datas);
        My_Pull.setAdapter(adapter);
        initData(index);
        setListener();
        return v;
    }

    private void initView(View v) {
        My_Pull = (PullToRefreshListView) v.findViewById(R.id.My_Pull);
        My_Pull.setMode(PullToRefreshListView.Mode.BOTH);

    }

    private void setListener() {
        My_Pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                index = 1;
                datas.clear();
                initData(index);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                index++;
                initData(index);

            }


        });


    }

    private void initData(final int index) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsons = ListHttpUtil.getStr(mUrl + index);
                Gson gson = new Gson();
                MyData myData = gson.fromJson(jsons, MyData.class);
                handler.sendMessage(handler.obtainMessage(0, myData.getData()));
            }
        });

    }
}
