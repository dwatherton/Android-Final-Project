package com.ualr.androidfinalproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.ualr.androidfinalproject.R;
import com.ualr.androidfinalproject.model.Item;

public class ItemListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "FinalProject";
    private static final int BROWSE_VIEW = 0;
    private static final int SHOPPING_CART_VIEW = 1;
    private SortedList<Item> mItems;
    private Context mContext;
    private boolean isBrowseView;
    private OnItemClickListener mOnItemClickListener;

    public ItemListAdapter(Context context, boolean isBrowseView) {
        this.mContext = context;
        this.isBrowseView = isBrowseView;
        this.mItems = new SortedList<>(Item.class, new SortedList.Callback<Item>() {
            @Override
            public int compare(Item o1, Item o2) {
                if (o1.getPrice() <= o2.getPrice()) {
                    return -1;
                } else {
                    return 1;
                }
            }

            @Override
            public void onChanged(int position, int count) {
                notifyDataSetChanged();
            }

            @Override
            public boolean areContentsTheSame(Item oldItem, Item newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(Item item1, Item item2) {
                return false;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
        // TODO: Replace This With DataGenerator POJO Class
        for (int i = 0; i < 20; i++)
        {
            mItems.add(new Item(R.drawable.ic_photo_24dp, "Item #" + i, 0.99 + (int)(Math.random() * 20 - 1) + 1, this.isBrowseView));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == BROWSE_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse_item_fragment, parent, false);
            vh = new BrowseItemViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_item_fragment, parent, false);
            vh = new ShoppingCartItemViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Item item = mItems.get(position);
        if (holder instanceof BrowseItemViewHolder) {
            BrowseItemViewHolder view = (BrowseItemViewHolder) holder;
            view.itemImage.setImageDrawable(mContext.getDrawable(item.getImageResId()));
            view.itemName.setText(item.getName());
            view.itemPrice.setText(String.valueOf(item.getPrice()));
        }
        else {
            ShoppingCartItemViewHolder view = (ShoppingCartItemViewHolder) holder;
            view.itemImage.setImageDrawable(mContext.getDrawable(item.getImageResId()));
            view.itemName.setText(item.getName());
            view.itemPrice.setText(String.valueOf(item.getPrice()));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).isBrowseView() ? BROWSE_VIEW : SHOPPING_CART_VIEW;
    }

    public SortedList<Item> getItems() {
        return this.mItems;
    }

    public class BrowseItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImage;
        private TextView itemName;
        private TextView itemPrice;
        private LinearLayout itemContainer;

        public BrowseItemViewHolder(@NonNull View view) {
            super(view);
            itemImage = view.findViewById(R.id.browse_item_image);
            itemName = view.findViewById(R.id.browse_item_name);
            itemPrice = view.findViewById(R.id.browse_item_price);
            itemContainer = view.findViewById(R.id.browse_item_container);
            itemContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Clicked on " + mItems.get(getAdapterPosition()).getName() + " at position " + getAdapterPosition() + ".");
            mOnItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public class ShoppingCartItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView itemImage;
        private TextView itemName;
        private TextView itemPrice;
        private LinearLayout itemContainer;

        public ShoppingCartItemViewHolder(@NonNull View view) {
            super(view);
            itemImage = view.findViewById(R.id.shopping_cart_item_image);
            itemName = view.findViewById(R.id.shopping_cart_item_name);
            itemPrice = view.findViewById(R.id.shopping_cart_item_price);
            itemContainer = view.findViewById(R.id.shopping_cart_item_container);
            itemContainer.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d(TAG, "Clicked on " + mItems.get(getAdapterPosition()).getName() + " at position " + getAdapterPosition() + ".");
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
}
