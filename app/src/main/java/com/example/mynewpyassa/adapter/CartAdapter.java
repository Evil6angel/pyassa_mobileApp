package com.example.mynewpyassa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mynewpyassa.R;
import com.example.mynewpyassa.model.CartItem;
import com.example.mynewpyassa.model.CartModel;
import com.example.mynewpyassa.model.ProductModel;

import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;

    public CartAdapter(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(   R.layout.cart_item_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        holder.productName.setText(cartItem.getProduct().getName());
        holder.productDescription.setText(cartItem.getProduct().getDescription());
        holder.productPrice.setText(String.format(Locale.getDefault(), "%d", cartItem.getProduct().getPrice()));

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                holder.itemView.getContext(), R.array.quantity_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantitySpinner.setAdapter(adapter);
        holder.quantitySpinner.setSelection(cartItem.getQuantity() - 1);
        Glide.with(holder.itemView.getContext())
                .load(cartItem.getProduct().getImage_url())
                .placeholder(R.drawable.placeholder_image) // optional placeholder image
                .error(R.drawable.error_image) // optional error image
                .into(holder.productImage);

        holder.removeButton.setOnClickListener(v -> {
            cartItems.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartItems.size());
            CartModel.getInstance().removeProduct(cartItem.getProduct());
        });
    }


        @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView productImage;
        TextView productName;
        TextView productDescription;
        TextView productPrice;
        Spinner quantitySpinner;
        Button removeButton;
        TextView totalPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productDescription = itemView.findViewById(R.id.product_description);
            productPrice = itemView.findViewById(R.id.product_price);
            quantitySpinner = itemView.findViewById(R.id.quantity_spinner);
            removeButton = itemView.findViewById(R.id.remove_item_button);
            totalPrice = itemView.findViewById(R.id.total_price_text_view);
        }
    }
}
