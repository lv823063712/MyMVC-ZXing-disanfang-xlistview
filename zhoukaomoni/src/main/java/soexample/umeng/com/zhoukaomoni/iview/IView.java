package soexample.umeng.com.zhoukaomoni.iview;

public interface IView<T> {

    void success(T data);

    void error(T error);

}
