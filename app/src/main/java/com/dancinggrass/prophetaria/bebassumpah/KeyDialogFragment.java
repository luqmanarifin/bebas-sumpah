package com.dancinggrass.prophetaria.bebassumpah;

import android.content.Context;
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
 * {@link KeyDialogFragment.OnKeyDialogListener} interface
 * to handle interaction events.
 * Use the {@link KeyDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeyDialogFragment extends DialogFragment {
    private OnKeyDialogListener mListener;

    public KeyDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment KeyDialogFragment.
     */
    public static KeyDialogFragment newInstance() {
        KeyDialogFragment fragment = new KeyDialogFragment();
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
        final View v = inflater.inflate(R.layout.fragment_key_dialog, container, false);
        Button submitButton = (Button)v.findViewById(R.id.button_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                EditText keyText = (EditText)v.findViewById(R.id.edit_text_key);
                if (mListener != null) {
                    dismiss();
                    mListener.sendEncryptedMail(keyText.getText().toString());
                }
            }
        });
        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnKeyDialogListener) {
            mListener = (OnKeyDialogListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnKeyDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnKeyDialogListener {
        void sendEncryptedMail(String key);
    }
}
