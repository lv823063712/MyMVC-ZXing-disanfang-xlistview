package soexample.umeng.com.zhoukaomoni;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import java.util.ArrayList;

import soexample.umeng.com.zhoukaomoni.adapter.FragmentAdapter;
import soexample.umeng.com.zhoukaomoni.fragment.MyFragment;
import soexample.umeng.com.zhoukaomoni.fragment.XlistFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<Fragment> datas = new ArrayList<>();
    private FragmentAdapter adapter;
    private ViewPager My_Vp;
    private RadioGroup RG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        initData();
        adapter = new FragmentAdapter(getSupportFragmentManager(), datas);
        My_Vp.setAdapter(adapter);
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.RB1) {
                    My_Vp.setCurrentItem(0);
                } else {
                    My_Vp.setCurrentItem(1);
                }
            }
        });
        My_Vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener()

        {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if (i == 0) {
                    RG.check(R.id.RB1);
                } else {
                    RG.check(R.id.RB2);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void initData() {
        XlistFragment xlistFragment = new XlistFragment();
        MyFragment myFragment = new MyFragment();

        datas.add(xlistFragment);
        datas.add(myFragment);
    }


    @Override
    public void onClick(View v) {

    }

    private void initView() {
        My_Vp = findViewById(R.id.My_Vp);
        RG = findViewById(R.id.RG);
    }
}
