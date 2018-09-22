package as.bwei.com.wenhaomonikaoshi1.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import as.bwei.com.wenhaomonikaoshi1.R;
import as.bwei.com.wenhaomonikaoshi1.bean.CartBean;

/**
 * Created by HP on 2018/9/21.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements CartCheckListener{

    private Context mContext;
    private List<CartBean.DataBean> cartList;
    private CartAllCheckboxListener allCheckboxListener;

    public CartAdapter(Context context, List<CartBean.DataBean> list) {
        mContext = context;
        cartList = list;
    }

    public void setCartAllCheckboxListener(CartAllCheckboxListener cartAllCheckboxListener) {
        allCheckboxListener = cartAllCheckboxListener;
    }

    public void addPageData(List<CartBean.DataBean> list){
        if (cartList!=null){
            cartList.addAll(list);
            notifyDataSetChanged();
        }
    }

    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.cart_item_layout,parent,false);
        CartViewHolder viewHolder = new CartViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CartAdapter.CartViewHolder holder, int position) {
        final CartBean.DataBean bean = cartList.get(position);
        holder.nameTv.setText(bean.getSellerName());

        holder.productXRV.setLayoutManager(new LinearLayoutManager(mContext));
        final ProductAdapter productAdapter = new ProductAdapter(mContext,bean.getList());
        holder.productXRV.setAdapter(productAdapter);
        productAdapter.setCheckListener(this);

    }

    @Override
    public int getItemCount() {
        return cartList.size() == 0 ? 0 : cartList.size();
    }

    @Override
    public void notifyParent() {
        notifyDataSetChanged();
        if (allCheckboxListener!=null){
            allCheckboxListener.notifyAllCheckboxStatus();
        }
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;
        private RecyclerView productXRV;

        public CartViewHolder(View itemView) {
            super(itemView);

            nameTv = (TextView) itemView.findViewById(R.id.sellerNameTv);
            productXRV = (RecyclerView) itemView.findViewById(R.id.productXRV);
        }
    }
}
