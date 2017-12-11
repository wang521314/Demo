package bo.liu.myviewpager;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2017/11/13 0013.
 */

public class ShopActivity extends Activity {

    private WebView fragment_shop_webview;
    String url;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_shop);
        initViews();
    }

    private void initViews() {
        fragment_shop_webview = (WebView) findViewById(R.id.fragment_shop_webview);
        fragment_shop_webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        WebSettings settings= fragment_shop_webview.getSettings();
        settings.setDomStorageEnabled(true);
        settings.setBlockNetworkImage(false);
        settings.setBlockNetworkLoads(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabaseEnabled(false);
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setNeedInitialFocus(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setBuiltInZoomControls(false);
        fragment_shop_webview.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
            url="http://youwuku.cn/egou/index.php/weixin/shop/show/?key=Mgz&page=w36175:150475075183&s_f=wx48297898&fxAgentId=&ySId=&sign=&UniqueKey=08726qUG609uPULxWgtLWJNNXx6XYPeI6xe3Iw-HqiW_ROCcUbFZShc7WWDXxAU2GZlz5LRGBg_4zHcSeyRWD67DC5harZhvDk6Y9BJNs9npZJ6JZLjwRh-HBjWrgtJ4zpUDPfUl";

        fragment_shop_webview.loadUrl(url);
    }
}
