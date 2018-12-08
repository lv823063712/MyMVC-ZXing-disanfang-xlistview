package soexample.umeng.com.zhoukaomoni.callback;

public interface MyCallBack<T> {
    void setData(T user);

    void setError(T error);

}
