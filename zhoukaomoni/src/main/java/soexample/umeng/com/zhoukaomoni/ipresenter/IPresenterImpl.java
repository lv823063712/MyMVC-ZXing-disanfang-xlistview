package soexample.umeng.com.zhoukaomoni.ipresenter;

import soexample.umeng.com.zhoukaomoni.MainActivity;
import soexample.umeng.com.zhoukaomoni.callback.MyCallBack;
import soexample.umeng.com.zhoukaomoni.iview.IView;
import soexample.umeng.com.zhoukaomoni.moudle.MoudleImpl;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private MoudleImpl moudle;

    public IPresenterImpl(MainActivity iView) {
        this.iView = iView;
        moudle = new MoudleImpl();
    }

    @Override
    public void startRequest(final String url, final String username, final String password) {
        moudle.userMoudle(url, username, password, new MyCallBack() {
            @Override
            public void setData(Object user) {
                iView.success(user);
            }

            @Override
            public void setError(Object error) {
                iView.error(error);
            }
        });
    }

    //防止内存泄漏
    public void onDetacth() {
        if (moudle!=null){
            moudle = null;
        }
        if (iView!=null){

        }
    }
}
