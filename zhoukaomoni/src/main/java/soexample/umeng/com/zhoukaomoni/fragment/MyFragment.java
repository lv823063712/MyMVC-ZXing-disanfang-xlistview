package soexample.umeng.com.zhoukaomoni.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import soexample.umeng.com.zhoukaomoni.MainActivity;
import soexample.umeng.com.zhoukaomoni.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    private ImageView Image;
    private Button Exit;
    private View v;
    private String contentEtString = "我很花心,每一秒的你,我都喜欢.帅景,我爱你.你得吕飞大宝贝";
    private SharedPreferences mShared;
    private SharedPreferences.Editor mEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_my, null);
        mShared = getActivity().getSharedPreferences("lf", Context.MODE_PRIVATE);
        String zhanghao = mShared.getString("zhanghao", null);
        contentEtString = zhanghao;

        initView(v);
        initData();
        return v;
    }

    private void initData() {
        Bitmap logo = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        try {
            //生成二维码需要用到类是CodeCreator
            Bitmap bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, logo);
            Image.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    private void initView(View v) {
        Image = (ImageView) v.findViewById(R.id.Image);
        Exit = (Button) v.findViewById(R.id.Exit);


        Exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Exit:
                mEdit = mShared.edit();
                mEdit.clear();
                mEdit.commit();
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();

                break;
        }
    }
}
