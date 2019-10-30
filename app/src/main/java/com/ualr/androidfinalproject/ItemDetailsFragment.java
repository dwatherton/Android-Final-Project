package com.ualr.androidfinalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.ualr.androidfinalproject.model.ItemViewModel;
import com.ualr.androidfinalproject.model.UserViewModel;

public class ItemDetailsFragment extends BottomSheetDialogFragment {

    private static final String TAG = "FinalProject";
    private static final String ITEM_POSITION_KEY = "ItemPositionKey";
    private TextView itemNameTV;
    private int position;
    private ItemViewModel itemViewModel;
    private UserViewModel userViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ITEM_POSITION_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getLayoutInflater().inflate(R.layout.item_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        initBottomSheet(view);
    }

    private void initBottomSheet(View view) {
        itemNameTV =  view.findViewById(R.id.item_details_name);
        // TODO: Initialize Bottom Sheet With Item Details
    }

    static ItemDetailsFragment newInstance(int pos) {
        ItemDetailsFragment editDeviceFragment = new ItemDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ITEM_POSITION_KEY, pos);
        editDeviceFragment.setArguments(args);
        return editDeviceFragment;
    }

    public void onAddToCartButtonClicked(View view) {
        Log.d(TAG, "Adding Item to Shopping Cart!");
        Toast.makeText(getContext(), "Adding Item: " + itemViewModel.getItemAt(position) + " to Cart", Toast.LENGTH_SHORT).show();
    }
}
