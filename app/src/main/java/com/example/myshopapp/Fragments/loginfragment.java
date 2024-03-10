package com.example.myshopapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myshopapp.MainActivity;
import com.example.myshopapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginfragment extends Fragment {
    private FirebaseAuth mAuth;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public loginfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static loginfragment newInstance(String param1, String param2) {
        loginfragment fragment = new loginfragment();
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



    public void loginFunc(View view, View parentView, String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Navigation.findNavController(view).navigate(R.id.action_loginfragment_to_mainfragment);
                        } else {
                            Navigation.findNavController(view).navigate(R.id.action_loginfragment_to_registerfragment);
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getContext(), "Login failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.loginfragment, container, false);
        EditText useredittext = view.findViewById(R.id.editTextUsername);
        EditText passwordedittext = view.findViewById(R.id.editTextPassword);
        Button logbutton = view.findViewById(R.id.buttonLogin);
        Button regbutton = view.findViewById(R.id.buttonregister);
        logbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve username and password when login button is clicked
                String username = useredittext.getText().toString();
                String password = passwordedittext.getText().toString();
                // Check if username or password is empty
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    // Display a Toast message indicating that the input is incomplete
                    Toast.makeText(getContext(), "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return; // Exit the onClick method early to prevent further execution
                }
                loginFunc(v, view, username, password);
            }
        });
        regbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginfragment_to_registerfragment);
            }
        });


            return view;
        }

    }

