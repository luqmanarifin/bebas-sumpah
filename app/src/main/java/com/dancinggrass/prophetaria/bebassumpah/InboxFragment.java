package com.dancinggrass.prophetaria.bebassumpah;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
 * {@link InboxFragment.OnMailSelectedListener} interface
 * to handle interaction events.
 * Use the {@link InboxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InboxFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_ADDRESS = "arg_address";

    private String address;

    private OnMailSelectedListener mListener;

    public InboxFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param address Parameter 1.
     * @return A new instance of fragment InboxFragment.
     */
    public static InboxFragment newInstance(String address) {
        InboxFragment fragment = new InboxFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ADDRESS, address);
        fragment.setArguments(args);
        return fragment;

    }

    public class Mail {
        public String from;
        public String to;
        public String message;
        public boolean type_encrypted;
        public boolean type_signature;

        public String getFrom() {
            return from;
        }

        public String getMessage() {
            return message;
        }

        public String getTo() {
            return to;
        }

        public boolean getTypeEncrypted() {
            return type_encrypted;
        }

        public boolean getTypeSignature() {
            return type_signature;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public void setType_encrypted(boolean type_encrypted) {
            this.type_encrypted = type_encrypted;
        }

        public void setType_signature(boolean type_signature) {
            this.type_signature = type_signature;
        }

        @Override
        public String toString() {
            return "From: " + from;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            address = getArguments().getString(ARG_ADDRESS);
        }


    }

    private String toClauseString(String clause) {
        return "'" + clause + "'";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_inbox, container, false);
        final ListView lv = (ListView)v.findViewById(R.id.inbox);

        BackendlessDataQuery query = new BackendlessDataQuery();
        query.setWhereClause("to = " + toClauseString(address));
        Backendless.Persistence.of(Mail.class).find(query, new AsyncCallback< BackendlessCollection<Mail> >(){
            @Override
            public void handleResponse(BackendlessCollection<Mail> foundMails) {
                List<String> items = new ArrayList<String>();
                ArrayAdapter<String> ad = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
                lv.setAdapter(ad);

                List<Mail> results = foundMails.getCurrentPage();
                Log.d(InboxFragment.class.getSimpleName(), results.toString());
                for (Mail result: results) {
                    items.add(result.toString());
                }
            }
            @Override
            public void handleFault(BackendlessFault fault ) {
                Log.d(InboxFragment.class.getSimpleName(), fault.toString());
            }
        });
        return v;
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity
        mListener.onMailSelected(position);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnMailSelectedListener {
        void onMailSelected(int position);
    }
}
