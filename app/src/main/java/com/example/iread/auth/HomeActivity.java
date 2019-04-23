package com.example.iread.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.iread.Adapters.PageAdapter;
import com.example.iread.Fragment.ProfilePageFragment;
import com.example.iread.R;
import com.example.iread.base.BaseActivity;
import com.example.iread.model.User;
import com.example.iread.options.SettingActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class HomeActivity extends BaseActivity implements ProfilePageFragment.OnUpdateButtonListener {

    // Creating identifier to identify REST REQUEST (Update username) PROFILe

    private static final int UPDATE_USERNAME = 30;


    //FOR DESIGN


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profil);
        //3 - Configure ViewPager
        this.configureViewPagerAndTabs();
        this.configureToolbar();
        this.updateUIWhenCreating(); // 2 - Update UI

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
                Toast.makeText(this, "Recherche indisponible, demandez plutôt l'avis de Google, c'est mieux et plus rapide.", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_help:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

///////Profile Gestion///////////////////////////////////////////////////////////////////////////
    // Creating identifier to identify REST REQUEST (Update username) PROFILe

    @Override
    public void onUpdateButton(View v) {
        ProfilePageFragment.profileTextViewEmail.setText("hh");
        this.updateUsernameInFirebase();
    }
    // 3 - Update User Username
    private void updateUsernameInFirebase(){

        ProfilePageFragment.profileProgressBar.setVisibility(View.VISIBLE);
        String username = ProfilePageFragment.profileTextInputEditTextUsername.getText().toString();

        if (this.getCurrentUser() != null){
            if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                com.example.firebase.api.UserHelper.updateUsername(username, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterHelpRESTRequestsCompleted(UPDATE_USERNAME));
            }
        }
    }
    private OnSuccessListener<Void> updateUIAfterHelpRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    // 8 - Hiding Progress bar after request completed
                    case UPDATE_USERNAME:
                        ProfilePageFragment.profileProgressBar.setVisibility(View.INVISIBLE);
                        break;
                }
            }
        };
    }
    // 6 - Arranging method that updating UI with Firestore data
    private void updateUIWhenCreating(){

        if (this.getCurrentUser() != null){

            // 7 - Get additional data from Firestore (isMentor & Username)
            com.example.firebase.api.UserHelper.getUser(this.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    ProfilePageFragment.profileTextInputEditTextUsername.setText(username);
                    String email =getCurrentUser().getEmail();
                    ProfilePageFragment.profileTextViewEmail.setText(email);

                    if (getCurrentUser().getPhotoUrl() != null) {
                        Glide.with(HomeActivity.this)
                                .load(getCurrentUser().getPhotoUrl())
                                .apply(RequestOptions.circleCropTransform())
                                .into(ProfilePageFragment.profileimageViewProfilel);
                    }

                }
            });
        }
    }
}