package com.ualr.androidfinalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class BrowseItemsActivity extends AppCompatActivity {

    private static final String TAG = "FinalProject";
    private BrowseItemListFragment browseItemListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.browse_items);

        // Set Toolbar to include my custom menu
        Toolbar toolbar = findViewById(R.id.browse_items_toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.textColorSecondary));
        setSupportActionBar(toolbar);

        // Get the Item List Fragment
        browseItemListFragment = ((BrowseItemListFragment) getSupportFragmentManager().findFragmentById(R.id.browse_item_list_fragment));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browse_items_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "Clicked on menu option: " + item.getTitle() + ".");
        switch (item.getItemId()) {
            case R.id.search_items:
                browseItemListFragment.searchItems();
                return true;
            case R.id.view_shopping_cart:
                // Create An Intent For Showing The Shopping Cart View, And Start The Shopping Cart Activity
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout:
                // Call Logout Method In Browse Item List Fragment
                browseItemListFragment.logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
