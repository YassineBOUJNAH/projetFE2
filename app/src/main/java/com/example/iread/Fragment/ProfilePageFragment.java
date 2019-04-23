package com.example.iread.Fragment;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.iread.MainActivity;
import com.example.iread.R;
import com.example.iread.auth.HomeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.BreakIterator;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePageFragment extends Fragment {


    public static TextView profileTextViewEmail;
    public static ImageView profileimageViewProfilel;
    public static TextInputEditText profileTextInputEditTextUsername;
    public static ProgressBar profileProgressBar;
    public static Button profileUpdateButton;

    public static ProfilePageFragment newInstance() {
        return (new ProfilePageFragment());
    }
//gestion des callback

    public interface OnUpdateButtonListener{
        public void onUpdateButton(View v);
    }

    private OnUpdateButtonListener updateCallback;

    ///
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_profile_page, container, false);
     ///For Design
        profileTextViewEmail=(TextView)result.findViewById(R.id.fragment_profile_text_view_email);
        profileimageViewProfilel=(ImageView)result.findViewById(R.id.fragment_profile_imageview_profile);
        profileTextInputEditTextUsername=(TextInputEditText) result.findViewById(R.id.fragment_profile_edit_text_username);
        profileProgressBar=(ProgressBar)result.findViewById(R.id.fragment_profile_progress_bar);
        profileUpdateButton = (Button)result.findViewById(R.id.fragment_profile_button_update);

    ///gestion des listener

        View.OnClickListener clickListenerupdate = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCallback.onUpdateButton(v);
            }
        };
        profileUpdateButton.setOnClickListener(clickListenerupdate);


        return result;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            updateCallback = (OnUpdateButtonListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

}
