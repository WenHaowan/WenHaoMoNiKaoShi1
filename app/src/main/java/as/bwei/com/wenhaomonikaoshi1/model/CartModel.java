package as.bwei.com.wenhaomonikaoshi1.model;

import android.os.Handler;
import android.text.TextUtils;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import as.bwei.com.wenhaomonikaoshi1.bean.CartBean;
import as.bwei.com.wenhaomonikaoshi1.utils.OkHttpUtils;
import as.bwei.com.wenhaomonikaoshi1.utils.RequestCallback;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HP on 2018/9/21.
 */

public class CartModel {

    Handler handler = new Handler();

    public void getCarts(HashMap<String,String> params,String url,final CartCallback cartCallback){
        OkHttpUtils.getInstance().postData(url, params, new RequestCallback() {
            @Override
            public void failure(Call call, IOException e) {
                if (cartCallback!=null){
                    cartCallback.fail("网络异常，稍后再试");
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                try {
                    String jsonResult = response.body().string();
                    if (!TextUtils.isEmpty(jsonResult)){
                        parseResult(jsonResult,cartCallback);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseResult(String jsonResult, final CartCallback cartCallback) {
        final CartBean cartBean = new Gson().fromJson(jsonResult,CartBean.class);
        if (cartCallback!=null && cartBean != null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    cartCallback.success(cartBean);
                }
            });
        }
    }

    public interface CartCallback {
        void success(CartBean cartBean);
        void fail(String msg);
    }
}
