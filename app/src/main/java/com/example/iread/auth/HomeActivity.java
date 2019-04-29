package com.example.iread.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.iread.Adapters.PageAdapter;
import com.example.iread.Fragment.NewsPageFragment;
import com.example.iread.Fragment.NewsPageFragment.OnAddClicklistener;
import com.example.iread.Fragment.ProfilePageFragment;
import com.example.iread.R;
import com.example.iread.base.BaseActivity;
import com.example.iread.controller.SearchBooks;
import com.example.iread.controller.SearchFriends;
import com.example.iread.model.User;
import com.example.iread.options.SettingActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeActivity extends BaseActivity implements OnAddClicklistener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);
        //3 - Configure ViewPager
        this.configureViewPagerAndTabs();
        this.configureToolbar();

    }

    private void configureViewPagerAndTabs(){
        // 1 - Get ViewPager from layout
        ViewPager pager = (ViewPager)findViewById(R.id.activity_main_viewpager);
        // 2 - Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        TabLayout tabs= (TabLayout)findViewById(R.id.activity_main_tabs);
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        for (int i = 0; i < tabs.getTabCount(); i++) {
            tabs.getTabAt(i).setIcon(R.drawable.ic_search_white_24dp);
            tabs.getTabAt(i).setText(null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //2 - Inflate the menu and add it to the Toolbar
        getMenuInflater().inflate(R.menu.menu_profile_activity, menu);
        return true;
    }
//// ne touch pas ca
    @Override
    public int getFragmentLayout() { return R.layout.activity_profile; }


    private void configureToolbar(){
        // Get the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_main_toolbar);
        // Sets the Toolbar
        setSupportActionBar(toolbar);
    }


    ///////// Les Action de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //3 - Handle actions on menu items
        switch (item.getItemId()) {
            case R.id.action_setting:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.menu_activity_main_search:
                startActivity(new Intent(this, SearchBooks.class));
                return true;
            case R.id.action_help:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAddClick(View view) {
        startActivity(new Intent(this, SearchFriends.class));

    }
///manupiler AmisFrgament///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}