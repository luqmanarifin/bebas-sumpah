package com.dancinggrass.prophetaria.bebassumpah;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.backendless.Backendless;
import com.backendless.BackendlessCollection;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.BackendlessDataQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnMailSelectedListener} interface
 * to handle interaction events.
 * Use the {@link SentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ADDRESS = "arg_address";

    // TODO: Rename and change types of parameters
    private String address;

    private OnMailSelectedListener mListener;

    public SentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param address Parameter 1.
     * @return A new instance of fragment SentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SentFragment newInstance(String address) {
        SentFragment fragment = new SentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ADDRESS, address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            address = getArguments().getString(ARG_ADDRESS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sent, container, false);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO: Get mail from list
        Mail mail = null;
        mListener.onMailSelected(mail);
    }

    private String toClauseString(String clause) {
        return "'" + clause + "'";
    }

    @Override
    public void onStart() {
        super.onStart();
        View v = getView();
        updateView(v);
    }

    void updateView(View v) {
        final ListView lv = (ListView)v.findViewById(R.id.sent);

        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setWhereClause("from = " + toClauseString(address));
        Backendless.Persistence.of(Mail.class).find(query, new AsyncCallback< BackendlessCollection<Mail> >(){
            @Override
            public void handleResponse(BackendlessCollection<Mail> foundMails) {
                List<String> items = new ArrayList<String>();
                ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                lv.setAdapter(ad);

                List<Mail> results = foundMails.getCurrentPage();
                Log.d(SentFragment.class.getSimpleName(), results.toString());
                for (Mail result: results) {
                    items.add(result.toString());
                }
            }
            @Override
            public void handleFault(BackendlessFault fault ) {
                Log.d(SentFragment.class.getSimpleName(), fault.toString());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnMailSelectedListener) {
            mListener = (OnMailSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
