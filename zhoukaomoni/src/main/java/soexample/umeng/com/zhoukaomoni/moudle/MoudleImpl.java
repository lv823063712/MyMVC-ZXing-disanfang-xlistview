package soexample.umeng.com.zhoukaomoni.moudle;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import soexample.umeng.com.zhoukaomoni.bean.MyData;
import soexample.umeng.com.zhoukaomoni.bean.User;
import soexample.umeng.com.zhoukaomoni.callback.MyCallBack;
import soexample.umeng.com.zhoukaomoni.util.HttpUtil;

public class MoudleImpl implements Moudle {


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String jsons = (String) msg.obj;
            Gson gson = new Gson();
            User user = gson.fromJson(jsons, User.class);
            callBack.setData(user);
        }
    };

    private MyCallBack callBack;

    @Override
    public void userMoudle(final String url, final String username, final String password, final MyCallBack callBack) {
        this.callBack = callBack;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsons = HttpUtil.getStr(url, username, password);

                    handler.sendMessage(handler.obtainMessage(0, jsons));
                } catch (Exception e) {
                    Looper.prepare();
                    callBack.setError("出现了异常");
                    Looper.loop();
                }

            }
        }).start();
    }
}
