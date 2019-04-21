package com.example.iread.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iread.MainActivity;
import com.example.iread.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilePageFragment extends Fragment {


    //2 - Declare callback
    private OnSignOutClickedListener mCallback;

    // 1 - Declare our interface that will be implemented by any container activity
    public interface OnSignOutClickedListener {
        void OnSignOutClickedListener(View view);
    }

    public static ProfilePageFragment newInstance() {
        return (new ProfilePageFragment());
    }


    private View.OnClickListener signOutOnClick = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            mCallback.OnSignOutClickedListener(v);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // 4 - Call the method that creating callback after being attached to parent activity
        this.createCallbackToParentActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result=inflater.inflate(R.layout.fragment_profile_page, container, false);
        result.findViewById(R.id.profile_activity_button_sign_out).setOnClickListener(signOutOnClick);
        return result;
    }

    // 3 - Create callback to parent activity
    private void createCallbackToParentActivity(){
        try {
            //Parent activity will automatically subscribe to callback
            mCallback = (OnSignOutClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString()+ " must implement OnButtonClickedListener");
        }
    }

}
