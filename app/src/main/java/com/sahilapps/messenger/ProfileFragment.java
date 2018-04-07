package com.sahilapps.messenger;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    private Button mLogOutButton;
    private CircleImageView mProfilePicImageView;
    private TextView mUserNameTextView;

    private FirebaseAuth firebaseAuth;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    public static final int PICK_IMAGE = 1;
    private String uid;

    private Uri imageUri;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("profile_image");
        firebaseFirestore = FirebaseFirestore.getInstance();

        mLogOutButton = view.findViewById(R.id.logout_button);
        mProfilePicImageView = view.findViewById(R.id.profile_pic_imageview);
        mUserNameTextView = view.findViewById(R.id.username_textview);

        uid = firebaseAuth.getCurrentUser().getUid();

        firebaseFirestore.collection("users").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String user_name = documentSnapshot.getString("user_name");
                String image_url = documentSnapshot.getString("image_url");

                mUserNameTextView.setText(user_name);
                Picasso.get()
                        .load(image_url)
                        .placeholder(R.drawable.placeholder)
                        .into(mProfilePicImageView);
            }
        });



        mProfilePicImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });



        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> tokenRemove = new HashMap<>();
                tokenRemove.put("token_id", FieldValue.delete());
                firebaseFirestore.collection("users").document(uid).update(tokenRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        firebaseAuth.signOut();
                        sendtoLoginActivity();
                    }
                });
            }
        });

        return view;
    }

    private void sendtoLoginActivity() {
        Intent loginActivityIntent = new Intent(getContext(), LoginActivity.class);
        startActivity(loginActivityIntent);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE){
            imageUri = data.getData();
            storageReference.child(uid).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProfilePicImageView.setImageURI(imageUri);
                    String url = taskSnapshot.getDownloadUrl().toString();
                    Map<String , Object> userMap = new HashMap<>();
                    userMap.put("image_url", url);
                    firebaseFirestore.collection("users").document(uid).update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "failed to upload Image", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
