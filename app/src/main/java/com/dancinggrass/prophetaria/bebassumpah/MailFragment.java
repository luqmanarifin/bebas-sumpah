package com.dancinggrass.prophetaria.bebassumpah;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import bonek.BonekAlgorithm;
import elliptic_curve_signature.Ecdsa;
import elliptic_curve_signature.Point;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MailFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TO = "arg_to";
    private static final String ARG_SUBJECT = "arg_subject";
    private static final String ARG_MESSAGE = "arg_message";
    private static final String ARG_ENCRYPTED = "arg_encrypted";
    private static final String ARG_SIGNED = "arg_signed";

    private String to;
    private String subject;
    private String message;

    private boolean encrypted;
    private boolean signed;


    public MailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mail Parameter 1.
     * @return A new instance of fragment MailFragment.
     */

    public static MailFragment newInstance(Mail mail) {
        MailFragment fragment = new MailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TO, mail.getTo());
        args.putString(ARG_SUBJECT, mail.getSubject());
        args.putString(ARG_MESSAGE, mail.getMessage());
        args.putBoolean(ARG_ENCRYPTED, mail.getTypeEncrypted());
        args.putBoolean(ARG_SIGNED, mail.getTypeSignature());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            to = getArguments().getString(ARG_TO);
            subject = getArguments().getString(ARG_SUBJECT);
            message = getArguments().getString(ARG_MESSAGE);
            encrypted = getArguments().getBoolean(ARG_ENCRYPTED);
            signed = getArguments().getBoolean(ARG_SIGNED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mail, container, false);
        populateMail(v);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void populateMail(View v) {
        TextView textAddress = (TextView)v.findViewById(R.id.text_address);
        TextView textSubject = (TextView)v.findViewById(R.id.text_subject);
        TextView textMessage = (TextView)v.findViewById(R.id.text_message);

        textAddress.setText(to);
        textSubject.setText(subject);
        textMessage.setText(message);
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }


}
