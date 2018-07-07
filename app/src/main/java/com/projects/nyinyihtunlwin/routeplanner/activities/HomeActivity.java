package com.projects.nyinyihtunlwin.routeplanner.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.projects.nyinyihtunlwin.routeplanner.R;
import com.projects.nyinyihtunlwin.routeplanner.data.vo.RouteVO;
import com.projects.nyinyihtunlwin.routeplanner.utils.CommonConstants;
import com.projects.nyinyihtunlwin.routeplanner.utils.ConfigUtils;
import com.projects.nyinyihtunlwin.routeplanner.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

public class HomeActivity extends BaseActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener, GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleMap.OnMapClickListener {

    public static final Intent newIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @BindView(R.id.ll_marker_action)
    LinearLayout llMarkerAction;

    @BindView(R.id.btn_show_route)
    Button btnShowRouote;

    @BindView(R.id.btn_check_in)
    Button btnCheckIn;

    @BindView(R.id.tv_name_location)
    TextView tvLocationName;

    @BindView(R.id.tv_current_amount)
    TextView tvCurrentAmount;

    @BindView(R.id.iv_profile)
    ImageView ivProfile;


    private GoogleMap mMap;
    private List<RouteVO> routeVOList;
    private RouteVO mCurrentRoute;
    private Marker currentMarker = null;

    private Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRealm = Realm.getDefaultInstance();

        intializeCash();
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

    /*    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        ivProfile.setOnClickListener(this);

    }

    private void intializeCash() {
        if (ConfigUtils.getInstance().loadCurrentCashAmount() == -1) {
            ConfigUtils.getInstance().saveCurrentCashAmount(10000000);
        }
        if (ConfigUtils.getInstance().loadCurrentEloadAmount() == -1) {
            ConfigUtils.getInstance().saveCurrentEloadAmount(10000000);
        }
    }

    private void initializeRouteData() {
        List<RouteVO> routeList = new ArrayList<>();
        routeList.add(new RouteVO("1", "Novotel Hotel", CommonConstants.TYPE_PICK, 16.820370, 96.131991, "1,000,000 s", 11111111, false));
        routeList.add(new RouteVO("2", "Shwe Kaung Hot Pot", CommonConstants.TYPE_DROP, 16.821183, 96.129391, "500 MMK", 11111112, false));
        routeList.add(new RouteVO("3", "KFC Junction", CommonConstants.TYPE_PICK, 16.817100, 96.131617, "3,500,000 MMK", 11111113, false));
        routeList.add(new RouteVO("4", "Gripz(Junction Square)", CommonConstants.TYPE_DROP, 16.817736, 96.130395, "100 MMK", 11111114, false));
        routeList.add(new RouteVO("5", "MRTV Earth Station", CommonConstants.TYPE_PICK, 16.816141, 96.132805, "4,300,000 MMK", 11111115, false));
        routeList.add(new RouteVO("6", "Summer Place Hotel", CommonConstants.TYPE_DROP, 16.818013, 96.132649, "230 MMK", 11111116, false));
        routeList.add(new RouteVO("7", "Max Energy Kyun Taw", CommonConstants.TYPE_PICK, 16.814937, 96.130247, "75,000,000 MMK", 11111117, false));
        routeList.add(new RouteVO("8", "Drop Location (1)", CommonConstants.TYPE_DROP, 16.819802, 96.126658, "75,000,000 MMK", 11111118, false));
        routeList.add(new RouteVO("9", "Pick Location (1)", CommonConstants.TYPE_PICK, 16.820238, 96.128623, "75,000,000 MMK", 11111119, false));

        RealmResults<RouteVO> routeResultList = mRealm.where(RouteVO.class).findAll();
        if (routeResultList != null && routeResultList.size() != 0) {
            routeVOList = routeResultList.subList(0, routeResultList.size());
            Log.e("Rouotes", routeVOList.size() + "");
        } else {
            mRealm.beginTransaction();
            for (RouteVO routeVO : routeList) {
                mRealm.insertOrUpdate(routeVO);
            }
            mRealm.commitTransaction();
            RealmResults<RouteVO> routeResults = mRealm.where(RouteVO.class).findAll();
            routeVOList = routeResultList.subList(0, routeResults.size());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            System.exit(0);
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
/*
        if (id == R.id.nav_route_list) {
            Intent intentToRouteList = RouteListActivity.newIntent(HomeActivity.this);
            startActivity(intentToRouteList);
        } else*/
        if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {
            ConfigUtils.getInstance().saveCurrentUser("");
            Intent intentToHome = LoginActivity.newIntent(HomeActivity.this);
            startActivity(intentToHome);
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
                    if (routeVO.isDone()) {
                        mMap.addMarker(new MarkerOptions().position(marker)
                                .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_beenhere_24dp))
                                .snippet(routeVO.getCurrentAmount())
                                .title(routeVO.getName()));
                    } else {
                        mMap.addMarker(new MarkerOptions().position(marker)
                                .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_location_on_drop_24dp))
                                .title(routeVO.getName()));
                    }
                    break;
                case CommonConstants.TYPE_PICK:
                    if (routeVO.isDone()) {
                        mMap.addMarker(new MarkerOptions().position(marker)
                                .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_beenhere_24dp))
                                .snippet(routeVO.getCurrentAmount())
                                .title(routeVO.getName()));
                    } else {
                        mMap.addMarker(new MarkerOptions().position(marker)
                                .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_location_on_pick_24dp))
                                .title(routeVO.getName()));
                    }
                    break;
            }

        }

        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(16.817736, 96.130395));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);


        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);

        btnShowRouote.setOnClickListener(this);
        btnCheckIn.setOnClickListener(this);
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
        for (RouteVO route : routeVOList) {
            if (marker.getTitle().equals(route.getName())) {
                mCurrentRoute = route;
                tvLocationName.setText(route.getName());
                tvCurrentAmount.setText(route.getType());
                if (route.isDone()) {
                    llMarkerAction.setVisibility(View.GONE);
                } else {
                    llMarkerAction.setVisibility(View.VISIBLE);
                }
            }
        }
        currentMarker = marker;

        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show_route:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + mCurrentRoute.getLat() + "," + mCurrentRoute.getLng() + "&mode=d");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            case R.id.btn_check_in:
                Intent intentToCheckIn = CheckInActivity.newIntent(HomeActivity.this, mCurrentRoute.getId());
                startActivityForResult(intentToCheckIn, 1000);
                break;
            case R.id.iv_profile:
                Intent intentToProfile = ProfileActivity.newIntent(HomeActivity.this);
                startActivity(intentToProfile);
                break;
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        llMarkerAction.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 1000) {
            if (currentMarker != null) {
                currentMarker.remove();
                currentMarker = null;
            }

            long givenAmount = data.getLongExtra("amount", 0);
            String type = data.getStringExtra("type");


            mRealm.beginTransaction();
            if (routeVOList.contains(mCurrentRoute)) {
                routeVOList.get(routeVOList.indexOf(mCurrentRoute)).setDone(true);
                switch (type) {
                    case CommonConstants.TYPE_CASH:
                        routeVOList.get(routeVOList.indexOf(mCurrentRoute)).setCurrentAmount(givenAmount + " (Cash)");
                        break;
                    case CommonConstants.TYPE_ELOAD:
                        routeVOList.get(routeVOList.indexOf(mCurrentRoute)).setCurrentAmount(givenAmount + " (E-Load)");
                        break;
                }
                mRealm.copyToRealmOrUpdate(routeVOList.get(routeVOList.indexOf(mCurrentRoute)));
            }
            mRealm.commitTransaction();

            LatLng marker = new LatLng(mCurrentRoute.getLat(), mCurrentRoute.getLng());
            mMap.addMarker(new MarkerOptions().position(marker)
                    .icon(bitmapDescriptorFromVector(HomeActivity.this, R.drawable.ic_beenhere_24dp))
                    .snippet(mCurrentRoute.getCurrentAmount()).title(mCurrentRoute.getName()));

            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(16.817736, 96.130395));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);


            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

            llMarkerAction.setVisibility(View.GONE);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
