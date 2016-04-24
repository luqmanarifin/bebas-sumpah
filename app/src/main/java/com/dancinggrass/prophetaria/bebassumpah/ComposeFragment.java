package com.dancinggrass.prophetaria.bebassumpah;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.math.BigInteger;

import bonek.BonekAlgorithm;
import elliptic_curve_signature.Ecdsa;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMailSelectedListener} interface
 * to handle interaction events.
 * Use the {@link ComposeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComposeFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ADDRESS = "arg_address";
    private static final String ARG_X = "arg_x";
    private static final String ARG_Y= "arg_y";
    private static final String ARG_START= "arg_start";

    private String address;
    private Key key;

    private Mail draftMail = null;

    private OnSendListener mListener = null;


    public ComposeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param address Parameter 1.
     * @return A new instance of fragment ComposeFragment.
     */
    public static ComposeFragment newInstance(String address, Key encryptKey) {
        ComposeFragment fragment = new ComposeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ADDRESS, address);
        args.putString(ARG_X, encryptKey.getX());
        args.putString(ARG_Y, encryptKey.getY());
        args.putString(ARG_START, encryptKey.getStart());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            address = getArguments().getString(ARG_ADDRESS);
            String x = getArguments().getString(ARG_X);
            String y = getArguments().getString(ARG_Y);
            String start = getArguments().getString(ARG_START);
            key = new Key(address, x, y, start);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_compose, container, false);
        Button buttonSend = (Button)v.findViewById(R.id.button_send);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                Mail mail = extractMailFromInput(v);
                getFragmentManager().popBackStack();
                mListener.sendMail(mail);
            }
        });
        return v;
    }


    private Mail extractMailFromInput(View v) {
        EditText textTo = (EditText)v.findViewById(R.id.edit_text_recipient);
        EditText textSubject = (EditText)v.findViewById(R.id.edit_text_subject);
        EditText textMessage = (EditText)v.findViewById(R.id.edit_text_message);

        String to = textTo.getText().toString();
        String subject = textSubject.getText().toString();
        String message = textMessage.getText().toString();

        CheckBox boxEncrypt = (CheckBox)v.findViewById(R.id.checkbox_encrypt);
        CheckBox boxSign = (CheckBox)v.findViewById(R.id.checkbox_sign);

        return new Mail(address, to, subject, message, boxEncrypt.isChecked(), boxSign.isChecked());
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSendListener) {
            mListener = (OnSendListener) context;
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

    public interface OnSendListener {
        void sendMail(Mail mail);
    }
}
