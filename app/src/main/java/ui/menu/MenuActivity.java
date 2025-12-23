package com.example.comp2000restaurantapp.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.model.MenuData;
import com.example.comp2000restaurantapp.ui.home.GuestHomeActivity;
import com.example.comp2000restaurantapp.ui.reservation.ReservationActivity;
import com.example.comp2000restaurantapp.ui.auth.LoginActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private LinearLayout menuContainer;
    private ScrollView scrollMenu;
    private final Map<Integer, View> anchors = new HashMap<>();

    // 🔐 TEMP ROLE FLAG (later replace with auth logic)
    private boolean isStaff = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.navigation_view);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        TabLayout tabs = findViewById(R.id.tabLayout);
        menuContainer = findViewById(R.id.menuContainer);
        scrollMenu = findViewById(R.id.scrollMenu);
        MaterialButton btnBook = findViewById(R.id.btnBookNow);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddItem);

        // 🔍 Role detection (simple & safe for assessment)
        isStaff = getIntent().getBooleanExtra("IS_STAFF", false);

        fabAdd.setVisibility(isStaff ? View.VISIBLE : View.GONE);

        toolbar.setNavigationOnClickListener(v -> drawer.openDrawer(GravityCompat.START));

        nav.setNavigationItemSelectedListener(item -> {
            drawer.closeDrawers();

            if (item.getItemId() == R.id.nav_home)
                startActivity(new Intent(this, GuestHomeActivity.class));
            else if (item.getItemId() == R.id.nav_logout) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            return true;
        });

        btnBook.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationActivity.class)));

        fabAdd.setOnClickListener(v ->
                startActivity(new Intent(this, StaffMenuEditorActivity.class)));

        String[] categories = {"Starters", "Deals", "Specials", "Mains", "Desserts", "Drinks"};
        for (String c : categories) tabs.addTab(tabs.newTab().setText(c));

        displayMenu();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View anchor = anchors.get(tab.getPosition());
                if (anchor != null)
                    scrollMenu.smoothScrollTo(0, anchor.getTop());
            }
            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void displayMenu() {
        menuContainer.removeAllViews();
        anchors.clear();

        if (MenuData.menuItems.isEmpty()) {
            View empty = getLayoutInflater()
                    .inflate(R.layout.view_menu_empty, menuContainer, false);
            menuContainer.addView(empty);
            return;
        }

        int index = 0;

        for (MenuData.Category category : MenuData.Category.values()) {

            View header = getLayoutInflater()
                    .inflate(R.layout.item_category_header, menuContainer, false);

            ((TextView) header.findViewById(R.id.tvCategoryTitle))
                    .setText(category.name());

            anchors.put(index++, header);
            menuContainer.addView(header);

            for (MenuData.MenuItem item : MenuData.menuItems) {
                if (item.category != category) continue;

                View card = getLayoutInflater()
                        .inflate(R.layout.item_menu_card, menuContainer, false);

                ((TextView) card.findViewById(R.id.tvItemName)).setText(item.name);
                ((TextView) card.findViewById(R.id.tvItemPrice))
                        .setText(String.format(Locale.UK, "£%.2f", item.price));
                ((TextView) card.findViewById(R.id.tvItemCategory))
                        .setText(category.name());

                menuContainer.addView(card);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayMenu();
    }
}
