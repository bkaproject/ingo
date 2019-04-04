package com.cavalerie.vlc123.gogogo.Activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.ZoomInTransformer;
import com.cavalerie.vlc123.gogogo.Dialog.ContactDialog;
import com.cavalerie.vlc123.gogogo.Data.UserProfileManager;
import com.cavalerie.vlc123.gogogo.R;
import com.cavalerie.vlc123.gogogo.Adapters.Tabpager.tabPagerDashboardAdapter;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String[] activityTitles;
    private TabLayout tabLayout;
    private ViewPager pager;
    private tabPagerDashboardAdapter tabPagerDashboardAdapter;
    private de.hdodenhof.circleimageview.CircleImageView imgprofil1;
    private Button btnEditProfil;
    private TextView txtProfilName;

    private UserProfileManager userProfileManager;

    // index to identify current nav about_menu item
    public static int navItemIndex = 0;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contact:
                contactDialog();
                break;

            case R.id.about:

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void contactDialog() {
        ContactDialog contactDialog = new ContactDialog();
        contactDialog.show(getSupportFragmentManager(), "Contact dialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.about_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);

        userProfileManager = new UserProfileManager(DashboardActivity.this);

        imgprofil1 = (de.hdodenhof.circleimageview.CircleImageView) headerview.findViewById(R.id.imgprofil1);
        txtProfilName = (TextView) headerview.findViewById(R.id.txtProfilName);
        btnEditProfil = (Button) headerview.findViewById(R.id.btnEditProfl);
        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ProfilActivity.class));
            }
        });

        //load toolbar titles from string ressource
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        //define de tablayout and viewPager
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        pager = (ViewPager)findViewById(R.id.viewpager);

        //apply the tabs in content for navigation drawer
        tabPagerDashboardAdapter = new tabPagerDashboardAdapter(getSupportFragmentManager());
        pager.setAdapter(tabPagerDashboardAdapter);
        tabLayout.setupWithViewPager(pager);
        setupTabLayout();
        setToolbarTitle();
        pagerchanger();

        //animation scroll with page transformer
        //Other transform types include AccordionTransformer, CubeInTransformer, FlipHorizontalTransformer,
        // ScaleInOutTransformer, ZoomInTransformer, and many others.
        pager.setPageTransformer(true, new ZoomInTransformer());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    private void setupTabLayout() {
        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.travel);
        tabLayout.getTabAt(2).setIcon(R.drawable.reserve);
        tabLayout.getTabAt(3).setIcon(R.drawable.message);

        txtProfilName.setText(userProfileManager.getFirstname() + " " + userProfileManager.getLastname());
        if (userProfileManager.getProfil() != null) {
            imgprofil1.setImageBitmap(BitmapFactory.decodeFile(userProfileManager.getProfil()));
        }
    }

    private void pagerchanger() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //changer de titre après le scroll
                getSupportActionBar().setTitle(activityTitles[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.nav_accueil:
                pager.setCurrentItem(0);
                navItemIndex = 0;
                setToolbarTitle();
                break;
            case R.id.nav_voyage:
                pager.setCurrentItem(1);
                navItemIndex = 1;
                setToolbarTitle();
                break;
            case R.id.nav_reserve:
                pager.setCurrentItem(2);
                navItemIndex = 2;
                setToolbarTitle();
                break;
            case R.id.nav_message:
                pager.setCurrentItem(3);
                navItemIndex = 3;
                setToolbarTitle();
                break;
            case R.id.nav_activity:
                final Intent i = new Intent(DashboardActivity.this, LoadingActivity.class);
                startActivity(i);
                break;
            case R.id.nav_history:
                final Intent j = new Intent(DashboardActivity.this, HistoryActivity.class);
                startActivity(j);
            case R.id.nav_manage:
                break;
            case R.id.nav_logout:
                userProfileManager.logoutUser();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}