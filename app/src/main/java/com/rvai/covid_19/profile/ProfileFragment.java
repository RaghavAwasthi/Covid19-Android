package com.rvai.covid_19.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.rvai.covid_19.Constants;
import com.rvai.covid_19.MainActivity;
import com.rvai.covid_19.MainFragment;
import com.rvai.covid_19.R;
import com.rvai.covid_19.RegisterUserActivity;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends MainFragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    MaterialButton login, signup;
    AppCompatImageView imageview;
    AppCompatTextView username, useraddress, userMobile;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(Constants.APP_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        if (firebaseAuth.getCurrentUser() == null) {
            view = inflater.inflate(R.layout.profile_not_logged_in, container, false);
            login = view.findViewById(R.id.button_login);
            signup = view.findViewById(R.id.button_signup);

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).signIn();

                }
            });
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).signIn();

                }
            });
        } else {

            view = inflater.inflate(R.layout.fragment_profile, container, false);
            imageview = view.findViewById(R.id.employee_image);
            username = view.findViewById(R.id.user_name);
            useraddress = view.findViewById(R.id.userAddress);
            userMobile = view.findViewById(R.id.userMobile);
            useraddress.setText(preferences.getString(Constants.USERADDRESS, " "));
            userMobile.setText(firebaseAuth.getCurrentUser().getPhoneNumber());
            username.setText(preferences.getString(Constants.USERNAME," "));
            Glide.with(imageview).load(preferences.getString(Constants.USERIMAGE,"")).diskCacheStrategy(DiskCacheStrategy.ALL).apply(RequestOptions.circleCropTransform()).into(imageview);


        }
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.title_profile;
    }
}
