package com.ualr.androidfinalproject;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.ualr.androidfinalproject.adapter.ItemListAdapter;
import com.ualr.androidfinalproject.model.Item;
import com.ualr.androidfinalproject.model.ItemViewModel;
import com.ualr.androidfinalproject.model.UserViewModel;

public class ShoppingCartListFragment extends Fragment {

    private static final String TAG = "FinalProject";
    private ItemListAdapter mAdapter;
    private ItemViewModel itemViewModel;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.item_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mAdapter = new ItemListAdapter(getActivity().getApplicationContext(), false);
        itemViewModel.setItems(mAdapter.getItems());
        RecyclerView mRecyclerView = view.findViewById(R.id.item_list_recylcer_view);
        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter.setOnItemClickListener(new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, String.format("The user has tapped on %s at position %s.", itemViewModel.getItemAt(position).getName(), position));
            }
        });
        itemViewModel.getItems().observe(getViewLifecycleOwner(), new Observer<SortedList<Item>>() {
            @Override
            public void onChanged(SortedList<Item> sortedList) {
                Log.d(TAG, "Item list changed, viewmodel observer is updating the recycler view.");
            }
        });
    }

    SortedList<Item> getItems() {
        return mAdapter.getItems();
    }

    void clearShoppingCart() {
        // Simply Clear The Shopping Cart Stored In The UserViewModel
        itemViewModel.clearCart();
    }
}
