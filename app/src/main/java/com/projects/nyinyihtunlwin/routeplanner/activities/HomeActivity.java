package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.data.vo.RouteVO;
import com.projects.nyinyihtunlwin.routeplanner.utils.CommonConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener {

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @BindView(R.id.ll_marker_action)
    LinearLayout llMarkerAction;

    private GoogleMap mMap;
    private List<RouteVO> routeVOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeRouteData();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void initializeRouteData() {
        routeVOList = new ArrayList<>();
        routeVOList.add(new RouteVO("1", "Novotel Hotel", CommonConstants.TYPE_PICK, 16.820370, 96.131991, "1,000,000 MMK"));
        routeVOList.add(new RouteVO("1", "Shwe Kaung Hot Pot", CommonConstants.TYPE_DROP, 16.821183, 96.129391, "500 MMK"));
        routeVOList.add(new RouteVO("1", "KFC Junction", CommonConstants.TYPE_PICK, 16.817100, 96.131617, "3,500,000 MMK"));
        routeVOList.add(new RouteVO("1", "Gripz(Junction Square)", CommonConstants.TYPE_DROP, 16.817736, 96.130395, "100 MMK"));
        routeVOList.add(new RouteVO("1", "MRTV Earth Station", CommonConstants.TYPE_PICK, 16.816141, 96.132805, "4,300,000 MMK"));
        routeVOList.add(new RouteVO("1", "Summer Place Hotel", CommonConstants.TYPE_DROP, 16.818013, 96.132649, "230 MMK"));
        routeVOList.add(new RouteVO("1", "Max Energy Kyun Taw", CommonConstants.TYPE_PICK, 16.814937, 96.130247, "75,000,000 MMK"));
        routeVOList.add(new RouteVO("1", "Drop Location (1)", CommonConstants.TYPE_DROP, 16.819802, 96.126658, "75,000,000 MMK"));
        routeVOList.add(new RouteVO("1", "Pick Location (1)", CommonConstants.TYPE_PICK, 16.820238, 96.128623, "75,000,000 MMK"));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_route_list) {
            Intent intentToRouteList = RouteListActivity.newIntent(HomeActivity.this);
            startActivity(intentToRouteList);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        for (RouteVO routeVO : routeVOList) {
            LatLng marker = new LatLng(routeVO.getLat(), routeVO.getLng());


            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inMutable = true;

            switch (routeVO.getType()) {
                case CommonConstants.TYPE_DROP:
                    mMap.addMarker(new MarkerOptions().position(marker)
                            .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_location_on_drop_24dp))
                            .snippet(routeVO.getCurrentAmount()).title(routeVO.getName()));
                    break;
                case CommonConstants.TYPE_PICK:
                    mMap.addMarker(new MarkerOptions().position(marker)
                            .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_location_on_pick_24dp))
                            .snippet(routeVO.getCurrentAmount()).title(routeVO.getName()));
                    break;
            }

        }

        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(16.817736, 96.130395));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);


        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        mMap.setOnMarkerClickListener(this);
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth() * 2, vectorDrawable.getIntrinsicHeight() * 2);
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth() * 2, vectorDrawable.getIntrinsicHeight() * 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        llMarkerAction.setVisibility(View.VISIBLE);
        return false;
    }
}
