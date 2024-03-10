package com.example.myshopapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myshopapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link registerfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class registerfragment extends Fragment {

    private FirebaseAuth mAuth;
    private View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public registerfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment registerfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static registerfragment newInstance(String param1, String param2) {
        registerfragment fragment = new registerfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.registerfragment, container, false);

        EditText usernameEditText = view.findViewById(R.id.editTextUsername);
        EditText passwordEditText = view.findViewById(R.id.editTextPassword);
        EditText phoneNumberEditText = view.findViewById(R.id.editTextPhoneNumber);
        Button signUpButton = view.findViewById(R.id.buttonSignUp);
        Button checkAvailabilityButton = view.findViewById(R.id.buttonCheckAvailability);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();
                signUpUser(username, password, phoneNumber);
            }
        });
        checkAvailabilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();

                // Check if username is empty
                if (username.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter a username", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if username is available
                mAuth.fetchSignInMethodsForEmail(username)
                        .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                if (task.isSuccessful()) {
                                    SignInMethodQueryResult result = task.getResult();
                                    if (result != null && result.getSignInMethods() != null && result.getSignInMethods().isEmpty()) {
                                        // Username is available
                                        Toast.makeText(getContext(), "Username is available", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Username is not available
                                        Toast.makeText(getContext(), "Username is already taken", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // Error occurred while checking username availability
                                    Toast.makeText(getContext(), "Error checking username availability", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });



        return view;

    }
    private void signUpUser(String username, String password, String phoneNumber) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign up success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        // You can handle what to do next here, for example, navigating to another fragment
                        // or displaying a message that registration was successful
                        Bundle bundle = new Bundle();
                        bundle.putString("userEmail", user.getEmail());

                        Toast.makeText(getContext(), "Sign up successful.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.action_registerfragment_to_mainfragment,bundle);

                    } else {
                        // If sign up fails, display a message to the user.
                        Toast.makeText(getContext(), "Sign up failed. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}