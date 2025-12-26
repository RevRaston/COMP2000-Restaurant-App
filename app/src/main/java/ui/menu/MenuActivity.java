package com.example.comp2000restaurantapp.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.comp2000restaurantapp.R;
import com.example.comp2000restaurantapp.domain.model.MenuData;
import com.example.comp2000restaurantapp.ui.auth.LoginActivity;
import com.example.comp2000restaurantapp.ui.home.GuestHomeActivity;
import com.example.comp2000restaurantapp.ui.reservation.ReservationActivity; // ✅ FIXED
import com.example.comp2000restaurantapp.ui.user.AccountActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private ScrollView scrollMenu;
    private LinearLayout menuContainer;
    private LinearLayout loadingContainer;
    private TextView emptyStateText;

    private final Map<Integer, View> anchors = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView nav = findViewById(R.id.navigation_view);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        TabLayout tabs = findViewById(R.id.tabLayout);

        scrollMenu = findViewById(R.id.scrollMenu);
        menuContainer = findViewById(R.id.menuContainer);
        loadingContainer = findViewById(R.id.loadingContainer);
        emptyStateText = findViewById(R.id.emptyStateText);
        MaterialButton btnBook = findViewById(R.id.btnBookNow);

        // Tabs
        String[] categories = {"Starters", "Deals", "Specials", "Mains", "Desserts", "Drinks"};
        for (String c : categories) {
            tabs.addTab(tabs.newTab().setText(c));
        }

        // Drawer
        toolbar.setNavigationOnClickListener(v ->
                drawer.openDrawer(GravityCompat.START));

        nav.setNavigationItemSelectedListener(item -> {
            drawer.closeDrawers();

            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, GuestHomeActivity.class));
            } else if (item.getItemId() == R.id.nav_account) {
                startActivity(new Intent(this, AccountActivity.class));
            } else if (item.getItemId() == R.id.nav_logout) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            return true;
        });

        // ✅ Reservation button works now
        btnBook.setOnClickListener(v ->
                startActivity(new Intent(this, ReservationActivity.class)));

        // Fake loading delay for UX polish
        new Handler().postDelayed(this::displayMenu, 800);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View anchor = anchors.get(tab.getPosition());
                if (anchor != null) {
                    scrollMenu.smoothScrollTo(0, anchor.getTop());
                }
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {}
            @Override public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void displayMenu() {
        menuContainer.removeAllViews();
        anchors.clear();

        loadingContainer.setVisibility(View.GONE);

        if (MenuData.menuItems.isEmpty()) {
            scrollMenu.setVisibility(View.GONE);
            emptyStateText.setVisibility(View.VISIBLE);
            return;
        }

        emptyStateText.setVisibility(View.GONE);
        scrollMenu.setVisibility(View.VISIBLE);

        int index = 0;

        for (MenuData.Category category : MenuData.Category.values()) {

            TextView header = new TextView(this);
            header.setText(category.name());
            header.setTextSize(20);
            header.setPadding(0, 40, 0, 16);

            anchors.put(index, header);
            menuContainer.addView(header);

            for (MenuData.MenuItem item : MenuData.menuItems) {
                if (item.category != category) continue;

                View card = getLayoutInflater()
                        .inflate(R.layout.item_menu_card, menuContainer, false);

                ((TextView) card.findViewById(R.id.tvItemName))
                        .setText(item.name);

                ((TextView) card.findViewById(R.id.tvItemPrice))
                        .setText(String.format(Locale.UK, "£%.2f", item.price));

                ((TextView) card.findViewById(R.id.tvItemCategory))
                        .setText(category.name());

                menuContainer.addView(card);
            }

            index++;
        }
    }
}
