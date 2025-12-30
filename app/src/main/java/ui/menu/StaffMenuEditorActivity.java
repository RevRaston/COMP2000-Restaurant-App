package com.example.comp2000restaurantapp.ui.menu;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.model.MenuItemModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class StaffMenuEditorActivity extends AppCompatActivity {

    private static final Set<String> ALLOWED_CATEGORIES = new HashSet<>(
            Arrays.asList("Starters", "Deals", "Specials", "Mains", "Desserts", "Drinks")
    );

    private FirebaseFirestore db;
    private LinearLayout menuContainer;

    private TextInputEditText etName;
    private TextInputEditText etPrice;
    private TextInputEditText etCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_menu_editor);

        db = FirebaseFirestore.getInstance();

        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        toolbar.setNavigationOnClickListener(v -> finish());

        menuContainer = findViewById(R.id.menuContainer);

        etName = findViewById(R.id.etName);
        etPrice = findViewById(R.id.etPrice);
        etCategory = findViewById(R.id.etCategory);

        MaterialButton btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(v -> addItem());

        startLiveMenuUpdates();
    }

    private void addItem() {
        String name = safe(etName);
        String priceStr = safe(etPrice);
        String category = safe(etCategory);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(priceStr) || TextUtils.isEmpty(category)) {
            Toast.makeText(this, "Fill name, price, and category", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!ALLOWED_CATEGORIES.contains(category)) {
            Toast.makeText(this,
                    "Category must be: Starters / Deals / Specials / Mains / Desserts / Drinks",
                    Toast.LENGTH_LONG).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price", Toast.LENGTH_SHORT).show();
            return;
        }

        if (price < 0) {
            Toast.makeText(this, "Price must be 0 or more", Toast.LENGTH_SHORT).show();
            return;
        }

        MenuItemModel item = new MenuItemModel(null, name, price, category, true);

        db.collection("menu_items")
                .add(item)
                .addOnSuccessListener(ref -> {
                    Toast.makeText(this, "Item added", Toast.LENGTH_SHORT).show();
                    etName.setText("");
                    etPrice.setText("");
                    etCategory.setText("");
                })
                .addOnFailureListener(err ->
                        Toast.makeText(this, "Add failed: " + err.getMessage(), Toast.LENGTH_LONG).show()
                );
    }

    private void startLiveMenuUpdates() {
        db.collection("menu_items")
                .addSnapshotListener((snapshots, e) -> {
                    if (snapshots == null) return;

                    menuContainer.removeAllViews();

                    if (snapshots.isEmpty()) {
                        TextView empty = new TextView(this);
                        empty.setText("No menu items yet.");
                        empty.setPadding(0, 24, 0, 0);
                        menuContainer.addView(empty);
                        return;
                    }

                    for (DocumentSnapshot doc : snapshots) {
                        String id = doc.getId();
                        String name = doc.getString("name");
                        Double price = doc.getDouble("price");
                        String category = doc.getString("category");
                        Boolean available = doc.getBoolean("available");

                        if (name == null) name = "(Unnamed)";
                        if (price == null) price = 0.0;
                        if (category == null) category = "Specials";
                        if (available == null) available = true;

                        View card = getLayoutInflater().inflate(R.layout.item_staff_menu, menuContainer, false);

                        TextView tvName = card.findViewById(R.id.tvName);
                        TextView tvInfo = card.findViewById(R.id.tvInfo);

                        tvName.setText(name);
                        tvInfo.setText(String.format(
                                Locale.UK,
                                "£%.2f • %s • %s",
                                price,
                                category,
                                available ? "Available" : "Hidden"
                        ));

                        card.findViewById(R.id.btnDelete).setOnClickListener(v ->
                                db.collection("menu_items")
                                        .document(id)
                                        .delete()
                        );

                        menuContainer.addView(card);
                    }
                });
    }

    private String safe(TextInputEditText et) {
        if (et.getText() == null) return "";
        return et.getText().toString().trim();
    }
}
