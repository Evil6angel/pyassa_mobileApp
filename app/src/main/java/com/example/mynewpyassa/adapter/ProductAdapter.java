    package com.example.mynewpyassa.adapter;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.recyclerview.widget.RecyclerView;

    import com.bumptech.glide.Glide;
    import com.example.mynewpyassa.CartActivity;
    import com.example.mynewpyassa.R;
    import com.example.mynewpyassa.model.CartItem;
    import com.example.mynewpyassa.model.CartModel;
    import com.example.mynewpyassa.model.ProductModel;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.firestore.FirebaseFirestore;

    import java.util.HashMap;
    import java.util.List;

    import io.reactivex.rxjava3.annotations.NonNull;

    public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

        // Declare a List of Product objects and a Cart object
        private List<ProductModel> productList;
        private CartModel cartModel;
        FirebaseFirestore firestore;
        String title, imageUrl, desc, productid, userid;
        int position = 0;
        int price = 0;
        int finalprice = 0;


        // Constructor that takes a List of Product objects as a parameter

        public ProductAdapter(List<ProductModel> productList, FirebaseFirestore firestore) {
            this.productList = productList;
            this.cartModel = new CartModel();
            this.firestore = firestore;
        }
        public ProductAdapter(List<ProductModel> productList) {
            this.productList = productList;
            this.cartModel = new CartModel();

        }




            // Create a ProductViewHolder class to hold the views for each item
        public static class ProductViewHolder extends RecyclerView.ViewHolder {


            // Declare the views in the product_item_layout.xml file
            public ImageView productImageView;
            public TextView productNameTextView;
            public TextView productPriceTextView;
            public TextView productDescriptionTextView;
                Spinner productQuantityTextView;

            public Button addToCartButton;




            public ProductViewHolder(View itemView) {
                super(itemView);

                // Find the views by their IDs
                productImageView = itemView.findViewById(R.id.product_image);
                productNameTextView = itemView.findViewById(R.id.product_name);
                productPriceTextView = itemView.findViewById(R.id.product_price);
                productDescriptionTextView = itemView.findViewById(R.id.product_description);
                productQuantityTextView=itemView.findViewById(R.id.quantity_spinner);
                addToCartButton = itemView.findViewById(R.id.add_to_cart_button);


            }
        }

        // Override the onCreateViewHolder method to inflate the product_item_layout.xml file
        @Override
        public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item_layout, parent, false);
            return new ProductViewHolder(view);
        }

        // Override the onBindViewHolder method to set the views in each item
        @Override
        public void onBindViewHolder(final ProductViewHolder holder, final int position) {
            final ProductModel product = productList.get(position);

            // Set the values of the views in the item
            holder.productNameTextView.setText(product.getName());
            holder.productDescriptionTextView.setText(product.getDescription());
            holder.productPriceTextView.setText(String.valueOf(product.getPrice()));

            // Use Glide to load the product image into the ImageView
            Glide.with(holder.itemView.getContext())
                    .load(productList.get(position).getImage_url())
                    .into(holder.productImageView);

            // Set an OnClickListener on the "Add to cart" button to add the product to the cart
            holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the quantity of the product from the user
                    final String[] quantityOptions = holder.itemView.getResources().getStringArray(R.array.quantity_options);
                    AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext());
                    builder.setTitle("Select quantity")
                            .setItems(quantityOptions, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    int quantity = Integer.parseInt(quantityOptions[which]);

                                    // Add the product to the cart with the selected quantity
                                    CartItem cartItem = new CartItem(product, quantity);
                                    CartModel.getInstance().addProduct(cartItem,quantity);

                                    // Show a toast message to confirm that the product was added to the cart
                                   //AddedInCart();
                                    String message = "Added " + product.getName() + " to cart";
                                    Toast.makeText(holder.itemView.getContext(), message, Toast.LENGTH_SHORT).show();

                                    // Finish the current activity to prevent going back to it from the Cart activity
                                    ((Activity) holder.itemView.getContext()).finish();

                                    // Create an Intent to launch the Cart activity
                                    Intent intent = new Intent(holder.itemView.getContext(), CartActivity.class);

                                    // Start the Cart activity
                                    holder.itemView.getContext().startActivity(intent);
                                }
                            });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                private void AddedInCart() {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("price", product.getPrice());
                    hashMap.put("name", product.getName());
                    hashMap.put("imageUrl", product.getImage_url());
                    hashMap.put("description", product.getDescription());
                    hashMap.put("category",product.getCategory());
                    hashMap.put("productID",product.getProductID());
                    ProductAdapter adapter = new ProductAdapter(productList, firestore);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    firestore.collection("Cart" + userid).document(title).set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // Perform any actions that need to be done once the data has been added to the cart
                        }
                    });
                }
            });


            // Override the getItemCount method to return the number of items in the productList

        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

    }
