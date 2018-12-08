package soexample.umeng.com.zhoukaomoni.moudle;

import soexample.umeng.com.zhoukaomoni.callback.MyCallBack;

public interface UserMoudle {
    //获取数据接口
    void getData(String url, String index, MyCallBack callBack);

}
