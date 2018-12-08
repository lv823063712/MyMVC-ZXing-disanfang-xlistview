package soexample.umeng.com.zhoukaomoni;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import soexample.umeng.com.zhoukaomoni.bean.User;
import soexample.umeng.com.zhoukaomoni.ipresenter.IPresenterImpl;
import soexample.umeng.com.zhoukaomoni.iview.IView;

public class MainActivity<T> extends AppCompatActivity implements View.OnClickListener, IView<T> {

    private EditText UserName, Password;
    private Button Login, ZhuCe, Three;
    private SharedPreferences mShare;
    private boolean isLogin;
    private SharedPreferences.Editor mEditor;
    private IPresenterImpl presenter;
    private String mUrl = "http://www.xieast.com/api/user/login.php";
    private CheckBox MyZiDong;
    private CheckBox MyJiZhu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShare = getSharedPreferences("lf", MODE_PRIVATE);
        initView();

        if (mShare.getBoolean("jizhu", false)) {
            String zhanghao = mShare.getString("zhanghao", null);
            String mima = mShare.getString("mima", null);
            UserName.setText(zhanghao);
            Password.setText(mima);
            MyJiZhu.setChecked(true);
        }
        processLogic();
    }

    private void processLogic() {
        //本地保存账号密码
        mShare = getSharedPreferences("lf", MODE_PRIVATE);
        mEditor = mShare.edit();
        boolean zhuangtai = mShare.getBoolean("zhuangtai", false);
        //
        if (zhuangtai) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }

        presenter = new IPresenterImpl(this);

    }

    private void initView() {
        UserName = (EditText) findViewById(R.id.UserName);
        Password = (EditText) findViewById(R.id.Password);
        Login = (Button) findViewById(R.id.Login);
        ZhuCe = (Button) findViewById(R.id.ZhuCe);
        Three = (Button) findViewById(R.id.Three);

        Login.setOnClickListener(this);
        ZhuCe.setOnClickListener(this);
        Three.setOnClickListener(this);
        MyZiDong = (CheckBox) findViewById(R.id.MyZiDong);
        MyZiDong.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditor.putBoolean("zhuangtai", true);
                    mEditor.putBoolean("jizhu", true);
                    mEditor.commit();
                    MyJiZhu.setChecked(true);
                } else {
                    mEditor.putBoolean("zhuangtai", false);
                    mEditor.commit();
                }
            }
        });
        MyJiZhu = (CheckBox) findViewById(R.id.MyJiZhu);
        MyJiZhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = UserName.getText().toString().trim();
                String pwd = Password.getText().toString().trim();
                if (MyJiZhu.isChecked()) {
                    mEditor.putBoolean("jizhu", true);
                    mEditor.putString("zhanghao", name);
                    mEditor.putString("mima", pwd);
                    mEditor.commit();
                } else {
                    mEditor.putBoolean("jizhu", false);
                    mEditor.putString("zhanghao", null);
                    mEditor.putString("mima", null);
                    mEditor.commit();
                }

            }
        });
        MyJiZhu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Login:
                //进行取值
                String name = UserName.getText().toString().trim();
                String pwd = Password.getText().toString().trim();
                //进行判断
                if (name.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(MainActivity.this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    if (!name.equals("13800138000") || !pwd.equals("123456")) {
                        Toast.makeText(MainActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        presenter.startRequest(mUrl, name, pwd);
                    }
                }
                break;
            case R.id.ZhuCe:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.Three:
                umengDeleteOauth(SHARE_MEDIA.QQ);
                //开始授权
                shareLoginUmeng(this, SHARE_MEDIA.QQ);
                break;
        }
    }

    public static void shareLoginUmeng(final Activity activity, SHARE_MEDIA share_media_type) {

        UMShareAPI.get(activity).getPlatformInfo(activity, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                // Log.e(TAG, "onStart授权开始: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //sdk是6.4.5的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String unionid = map.get("unionid");//微博没有
                String access_token = map.get("access_token");
                String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到
                String expires_in = map.get("expires_in");
                String name = map.get("name");//名称
                String gender = map.get("gender");//性别
                String iconurl = map.get("iconurl");//头像地址

               /* Log.e(TAG, "onStart授权完成: " + openid);
                Log.e(TAG, "onStart授权完成: " + unionid);
                Log.e(TAG, "onStart授权完成: " + access_token);
                Log.e(TAG, "onStart授权完成: " + refresh_token);
                Log.e(TAG, "onStart授权完成: " + expires_in);
                Log.e(TAG, "onStart授权完成: " + uid);
                Log.e(TAG, "onStart授权完成: " + name);
                Log.e(TAG, "onStart授权完成: " + gender);
                Log.e(TAG, "onStart授权完成: " + iconurl);*/
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(activity, "授权失败", Toast.LENGTH_LONG).show();
                // Log.e(TAG, "onError: " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(activity, "授权取消", Toast.LENGTH_LONG).show();
                // Log.e(TAG, "onError: " + "授权取消");
            }
        });
    }

    /**
     * 友盟取消授权（登入）
     */
    private void umengDeleteOauth(SHARE_MEDIA share_media_type) {

        UMShareAPI.get(this).deleteOauth(this, share_media_type, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                //开始授权
                // Log.e(TAG, "onStart: ");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                //取消授权成功 i=1
                //Log.e(TAG, "onComplete: ");
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                //授权出错
                //Log.e(TAG, "onError: ");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                //取消授权
                //Log.e(TAG, "onCancel: ");
            }
        });
    }


    @Override
    public void success(T data) {
        User user = (User) data;
        if (user.getCode() == 100) {
            Toast.makeText(this, user.getCode() + "", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, user.getCode() + "", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, HomeActivity.class));
            //登录完毕销毁此页面
            finish();

        }
    }

    @Override
    public void error(T error) {
        String e = (String) error;
        Toast.makeText(MainActivity.this, e, Toast.LENGTH_SHORT).show();
    }
}
