package com.example.mynewpyassa.model;

import com.example.mynewpyassa.adapter.CartAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CartModel {
    private ArrayList<CartItem> cartItems;
    private static CartModel instance;
    private FirebaseFirestore firestore;
    private CollectionReference cartCollection;

    // Static method to get the single instance of CartModel
    public static CartModel getInstance() {
        if (instance == null) {
            instance = new CartModel();
        }
        return instance;
    }

    public CartModel() {
        cartItems = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();
        cartCollection = firestore.collection("carts");
    }

    public void addProduct(CartItem cartItem, int quantity) {
        cartItem.setQuantity(quantity);
        if (cartItems.contains(cartItem)) {
            // If the cart already contains the item, increment its quantity
            int index = cartItems.indexOf(cartItem);
            CartItem existingItem = cartItems.get(index);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            // Otherwise, add the item to the cart
            cartItems.add(cartItem);
        }

        // Save the cart item in Firestore
        saveCartItem(cartItem);
    }

    private void saveCartItem(CartItem cartItem) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Get the current user's ID
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Create a reference to the current user's cart document
        DocumentReference cartRef = db.collection("users").document(userId).collection("cart").document();

        // Create a new cart item document
        Map<String, Object> cartItemData = new HashMap<>();
        cartItemData.put("name", cartItem.getProduct().getName());
        cartItemData.put("price", cartItem.getProduct().getPrice());
        cartItemData.put("quantity", cartItem.getQuantity());
        cartItemData.put("description", cartItem.getProduct().getDescription());
        cartItemData.put("image_url", cartItem.getProduct().getImage_url());

        // Set the cart item document in the user's cart document
        cartRef.set(cartItemData);
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public float getTotalPrice() {
        float totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getProduct().getPrice() * cartItem.getQuantity();
        }
        return totalPrice;
    }

    public void removeProduct(ProductModel product) {
        // Find the cart item corresponding to the given product
        CartItem cartItemToRemove = null;
        int position = -1;
        for (int i = 0; i < cartItems.size(); i++) {
            if (cartItems.get(i).getProduct().equals(product)) {
                cartItemToRemove = cartItems.get(i);
                position = i;
                break;
            }
        }

        if (cartItemToRemove != null) {
            cartItems.remove(cartItemToRemove);
            if(cartItems.size()>0){
                CartAdapter cartAdapter = new CartAdapter(cartItems);
                cartAdapter.notifyItemRemoved(position);
            }
        }
    }





    private void deleteCartItem(String productId) {
        DocumentReference cartItemRef = cartCollection.document(productId);
        cartItemRef.delete();
    }

    public void updateCartItemQuantity(int position, int quantity) {
        CartItem item = cartItems.get(position);
        item.setQuantity(quantity);
        cartItems.set(position, item);

        // Update the cart item in Firestore
        updateCartItem(item);
    }

    private void updateCartItem(CartItem cartItem) {
        DocumentReference cartItemRef = cartCollection.document(cartItem.getProduct().getProductID());
        cartItemRef.set(cartItem);
    }
}