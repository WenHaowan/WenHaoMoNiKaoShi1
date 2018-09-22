package as.bwei.com.wenhaomonikaoshi1.view;

import as.bwei.com.wenhaomonikaoshi1.bean.CartBean;

/**
 * Created by HP on 2018/9/21.
 */

public interface IcartView {
    void success(CartBean cartBean);
    void failure(String msg);
}
