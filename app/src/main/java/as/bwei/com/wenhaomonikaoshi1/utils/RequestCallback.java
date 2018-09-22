package as.bwei.com.wenhaomonikaoshi1.utils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by HP on 2018/9/21.
 */

public interface RequestCallback {
    void failure(Call call, IOException e);
    void onResponse(Call call, Response response);
}
