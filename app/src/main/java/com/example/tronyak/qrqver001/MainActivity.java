package com.example.tronyak.qrqver001;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.PushService;

import net.sourceforge.zbar.Symbol;


public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    FragmentTransaction fTrans;
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;
    private String result_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // To track statistics around application
        ParseAnalytics.trackAppOpened(getIntent());

        // inform the Parse Cloud that it is ready for notifications
        PushService.setDefaultPushCallback(this, MainActivity.class);
        ParseInstallation.getCurrentInstallation().saveInBackground();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        ParseUser currentUser = ParseUser.getCurrentUser();
        if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
            // Go to the user info activity
            Log.d("tronyak", "we are login");
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }


       /* final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

    }
    public void launchQRScanner(View v) {
        if (isCameraAvailable()) {
            Intent intent = new Intent(this, ZBarScannerActivity.class);
            intent.putExtra(ZBarConstants.SCAN_MODES, new int[]{Symbol.QRCODE});
            startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
        } else {
            Toast.makeText(this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ZBAR_SCANNER_REQUEST:
            case ZBAR_QR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK) {
                    result_code = data.getStringExtra(ZBarConstants.SCAN_RESULT);

                    Fragment search_page = new SearchPage();
                    fTrans = getFragmentManager().beginTransaction();
                    fTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);

                    Bundle args = new Bundle();
                    args.putString("qr_id", result_code);
                    search_page.setArguments(args);
                    fTrans.replace(R.id.container, search_page);
                    fTrans.commit();

                }
        }
    }
    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }
    @Override
    public void onNavigationDrawerItemSelected(int position) {

        Fragment main_page = new Start_page();
        Fragment quests_page = new Qests();
        Fragment users_page = new All_users_page();

        fTrans = getFragmentManager().beginTransaction();
        fTrans.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right);
        switch (position + 1) {
            case 1:
                fTrans.replace(R.id.container, quests_page);
                Log.d("tronyak", "fragment 1" + position + " is 1");
                break;
            case 2:
                fTrans.replace(R.id.container, users_page);
                Log.d("tronyak", "fragment 2" + position + " is 2");

                break;
            case 3:
                Log.d("tronyak", "fragment 3" + position +" is 3");
                fTrans.replace(R.id.container, main_page);
                break;
            case 4:
                Log.d("tronyak", "fragment 4" + position +" is 4");
                /*ParseUser currentUser = ParseUser.getCurrentUser();
                if ((currentUser != null) && ParseFacebookUtils.isLinked(currentUser)) {
                    ParseUser.logOut();
                    Toast.makeText(this, "We log out",Toast.LENGTH_LONG).show();

                }else{*/
                    Intent user_page = new Intent(this, UserDetailsActivity.class);
                    startActivity(user_page);
                /*}*/
                break;
        }
        fTrans.commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.menu_main);
                break;
            case 2:
                mTitle = getString(R.string.menu_users);
                break;
            case 3:
                mTitle = getString(R.string.menu_news);
                break;
            case 4:
                mTitle = getString(R.string.menu_user_page);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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


}
