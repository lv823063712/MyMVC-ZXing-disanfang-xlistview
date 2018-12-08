package soexample.umeng.com.zhoukaomoni.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import soexample.umeng.com.zhoukaomoni.R;
import soexample.umeng.com.zhoukaomoni.bean.MyData;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MyData.DataBean> datas;

    public MyAdapter(Context context, ArrayList<MyData.DataBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    public ArrayList<MyData.DataBean> getDatas() {
        return datas;
    }

    public void setDatas(ArrayList<MyData.DataBean> datas) {
        this.datas = datas;
        //刷新适配器
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //声明一个holder类
        ViewHolderOne one = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.myadapter, null);
            one = new ViewHolderOne();
            one.iv_img = convertView.findViewById(R.id.Img_One);
            one.tv_title = convertView.findViewById(R.id.Title_One);
            convertView.setTag(one);
        } else {
            one = (ViewHolderOne) convertView.getTag();
        }
        Glide.with(context).load(datas.get(position).getThumbnail_pic_s()).into(one.iv_img);
        one.tv_title.setText(datas.get(position).getTitle());
        return convertView;
    }

    class ViewHolderOne {
        ImageView iv_img;
        TextView tv_title;

    }
}
