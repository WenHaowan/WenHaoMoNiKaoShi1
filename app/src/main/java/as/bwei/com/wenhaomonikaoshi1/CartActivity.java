package as.bwei.com.wenhaomonikaoshi1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import as.bwei.com.wenhaomonikaoshi1.adapter.CartAdapter;
import as.bwei.com.wenhaomonikaoshi1.adapter.CartAllCheckboxListener;
import as.bwei.com.wenhaomonikaoshi1.bean.CartBean;
import as.bwei.com.wenhaomonikaoshi1.common.Constants;
import as.bwei.com.wenhaomonikaoshi1.presenter.CartPresenter;
import as.bwei.com.wenhaomonikaoshi1.view.IcartView;

public class CartActivity extends AppCompatActivity implements IcartView,CartAllCheckboxListener{

    private CartPresenter cartPresenter;
    private XRecyclerView xRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartBean.DataBean> list;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initData();
    }

    private void initView() {
        list = new ArrayList<>();
        xRecyclerView = (XRecyclerView) findViewById(R.id.cartGV);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        xRecyclerView.setLoadingMoreEnabled(true);

        xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                page++;
                loadData();
            }
        });
    }

    private void initData() {
        loadData();
    }

    private void loadData() {
        HashMap<String,String> params = new HashMap<>();
        params.put("uid","71");
        params.put("page",page+"");

        cartPresenter = new CartPresenter(this);
        cartPresenter.getCarts(params, Constants.GETCARTS);
    }

    @Override
    public void notifyAllCheckboxStatus() {

    }

    @Override
    public void success(CartBean cartBean) {
        if (cartBean!=null && cartBean.getData() !=null){
            if (page==1){
                list = cartBean.getData();
                cartAdapter = new CartAdapter(this,list);

                xRecyclerView.setAdapter(cartAdapter);
                xRecyclerView.refreshComplete();
            }else {
                if (cartAdapter!=null){
                    cartAdapter.addPageData(cartBean.getData());
                }
                xRecyclerView.loadMoreComplete();
            }
            cartAdapter.setCartAllCheckboxListener(this);
        }
    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
