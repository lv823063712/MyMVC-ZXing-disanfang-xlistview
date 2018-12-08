package soexample.umeng.com.zhoukaomoni;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

public class WebViewActivity extends AppCompatActivity {


    private WebView My_Web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        initView();
        Intent intent = getIntent();
        String uu = intent.getStringExtra("uu");
        WebSettings settings = My_Web.getSettings();
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setJavaScriptEnabled(true);
        My_Web.setWebViewClient(new WebViewClient());
        My_Web.loadUrl(uu);

    }

    private void initView() {
        My_Web = (WebView) findViewById(R.id.My_Web);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //引入布局
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return true;
    }

    //菜单点击事件


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.My_menu:
                Intent intent = getIntent();
                String img = intent.getStringExtra("img");
                UMImage umImage = new UMImage(this, img);
                new ShareAction(WebViewActivity.this)
                        .withText("hello").withMedia(umImage)
                        .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.QZONE)
                        //分享的监听
                        .setCallback(shareListener).open();
                break;
        }

        return true;
    }

    //设置回调监听
    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(WebViewActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(WebViewActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(WebViewActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };
}
