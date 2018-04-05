package com.sahilapps.messenger;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllUsersFragment extends Fragment {

    private RecyclerView mAllUsersRecyclerView;
    private UserRecyclerAdapter userRecyclerAdapter;
    private List<User> userList;
    private FirebaseFirestore firebaseFirestore;



    public AllUsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_users, container, false);

        mAllUsersRecyclerView = view.findViewById(R.id.rv_all_users_list);
        firebaseFirestore = FirebaseFirestore.getInstance();

        userList = new ArrayList<>();
        userRecyclerAdapter = new UserRecyclerAdapter(getContext(), userList);

        mAllUsersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAllUsersRecyclerView.setHasFixedSize(true);
        mAllUsersRecyclerView.setAdapter(userRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseFirestore.collection("users").addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){
                        User user = doc.getDocument().toObject(User.class);
                        userList.add(user);

                        userRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        userList.clear();
    }

    @Override
    public void onPause() {
        super.onPause();

    }
}
