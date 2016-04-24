package com.dancinggrass.prophetaria.bebassumpah;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnLoginDialogListener} interface
 * to handle interaction events.
 * Use the {@link LoginDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginDialogFragment extends DialogFragment {
    private String mParam1;
    private String mParam2;

    private OnLoginDialogListener mListener;

    public LoginDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginDialogFragment.
     */
    public static LoginDialogFragment newInstance() {
        LoginDialogFragment fragment = new LoginDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_login_dialog, container, false);
        Button loginButton = (Button)v.findViewById(R.id.button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                EditText addressText = (EditText)v.findViewById(R.id.edit_text_address);
                if (mListener != null) {
                    dismiss();
                    mListener.onLogin(addressText.getText().toString());
                }
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginDialogListener) {
            mListener = (OnLoginDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnLoginDialogListener {
        void onLogin(String address);
    }
}
