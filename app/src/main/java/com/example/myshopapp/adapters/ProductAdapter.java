package com.example.myshopapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myshopapp.classes.Product;
import com.example.myshopapp.R;

import java.util.List;

// ProductAdapter.java

import android.widget.Button;
import android.widget.TextView;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private List<Product> productList;
    private ProductItemClickListener clickListener;

    public ProductAdapter(List<Product> productList, ProductItemClickListener clickListener) {
        this.productList = productList;
        this.clickListener = clickListener;
    }
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }
    public interface ProductItemClickListener {
        void onPlusButtonClick(int position);
        void onMinusButtonClick(int position);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);
        return new ViewHolder(view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView productNameTextView;
        TextView productIDTextview;
        TextView productAmountTextView;
        Button plusButton;
        Button minusButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productIDTextview=itemView.findViewById(R.id.idproduct);
            productNameTextView = itemView.findViewById(R.id.product_name);
            productAmountTextView = itemView.findViewById(R.id.product_quantity);
            plusButton = itemView.findViewById(R.id.plusButon);
            minusButton = itemView.findViewById(R.id.minusButon);

            // Set click listeners for buttons
            plusButton.setOnClickListener(this);
            minusButton.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                if (v.getId() == R.id.plusButon) {
                    handlePlusButtonClick(position);
                } else if (v.getId() == R.id.minusButon) {
                    handleMinusButtonClick(position);
                }
            }
        }
        private void handlePlusButtonClick(int position) {
            // Handle plus button click
            // Increment quantity of the corresponding product
            Product product = productList.get(position);
            int currentQuantity = product.getAmount();
            // Increment quantity by 1
            currentQuantity++;
            product.setAmount(currentQuantity);
            // Update UI
            productAmountTextView.setText("Amount: " + currentQuantity);
            // Notify data change
            notifyItemChanged(position);
        }

        private void handleMinusButtonClick(int position) {
            // Handle minus button click
            // Decrease quantity of the corresponding product
            Product product = productList.get(position);
            int currentQuantity = product.getAmount();
            // Check if quantity is greater than 0 to avoid negative values
            if (currentQuantity > 0) {
                // Decrement quantity by 1
                currentQuantity--;
                product.setAmount(currentQuantity);
                // Update UI
                productAmountTextView.setText("Amount: " + currentQuantity);
                // Notify data change
                notifyItemChanged(position);
            }
        }

        public void bind(Product product) {
            productNameTextView.setText(product.getName());
            productIDTextview.setText(product.getId());
            productAmountTextView.setText("Amount: " + product.getAmount());
        }

    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
