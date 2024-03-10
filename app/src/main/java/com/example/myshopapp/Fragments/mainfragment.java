package com.example.myshopapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myshopapp.R;
import com.example.myshopapp.adapters.ProductAdapter;
import com.example.myshopapp.classes.MyData;
import com.example.myshopapp.classes.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link mainfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class mainfragment extends Fragment implements ProductAdapter.ProductItemClickListener {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView usernameTextView;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ProductAdapter adapter;
    private ArrayList<Product> productList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public mainfragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment mainfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static mainfragment newInstance(String param1, String param2) {
        mainfragment fragment = new mainfragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.mainfragment, container, false);
        mAuth = FirebaseAuth.getInstance();
        // Get the current user
        currentUser = mAuth.getCurrentUser();
        // Find the TextView by its ID
        usernameTextView = view.findViewById(R.id.textViewUsername);
        // Check if a user is currently signed in
        if (currentUser != null) {
            // Get the username (email) of the signed-in user
            String username = currentUser.getEmail(); // or currentUser.getDisplayName() if you have set a display name

            // Set the username in the TextView
            usernameTextView.setText("Welcome, " + username);
        } else {
            // No user is signed in
            // You may want to handle this case accordingly (e.g., prompt the user to sign in)
        }
        recyclerView = view.findViewById(R.id.recyclerViewProducts);
        linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);
        productList = new ArrayList<>();
        // List of product names


        for(int i=0;i< MyData.getProducts().length;i++)
        {
            productList.add(new Product(
                    MyData.groceryItems[i],
                    MyData.ids[i],
                    MyData.amounts[i]
            ));
        }
        // Initialize product list
        // Initialize adapter
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
        return view;    }

    // Method to add a new product to the list
    public void addProduct(String productName, int quantity) {
        // Create a new Product object
        Product product = new Product(productName, quantity);
        // Add the product to the list
        productList.add(product);
        // Notify adapter about the change
        adapter.notifyDataSetChanged();
    }
    // Method to remove a product from the list
    public void removeProduct(int position) {
        // Remove the product from the list
        productList.remove(position);
        // Notify adapter about the change
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onPlusButtonClick(int position) {
        // Handle plus button click in the fragment
        addProduct(productList.get(position).getName(), productList.get(position).getAmount() + 1);
    }
    @Override
    public void onMinusButtonClick(int position) {
        // Handle minus button click in the fragment
        int currentAmount = productList.get(position).getAmount();
        if (currentAmount > 0) {
            addProduct(productList.get(position).getName(), currentAmount - 1);
        }
    }
}
