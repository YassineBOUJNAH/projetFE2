package com.example.iread.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.iread.R;
import com.example.iread.api.UserHelper;
import com.example.iread.model.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class ClickedFriendActivity extends AppCompatActivity {

    ImageView imageview;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked_friend);
        imageview = (ImageView)findViewById(R.id.clicked_friend_activity_imageview_profile);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            UserHelper.getUser(extras.getString("key")).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    user = documentSnapshot.toObject(User.class);
                    Glide.with(ClickedFriendActivity.this)
                            .load(user.getUrlPicture())
                            .apply(RequestOptions.circleCropTransform())
                            .into(imageview);


                }
            });

        }
    }
}
