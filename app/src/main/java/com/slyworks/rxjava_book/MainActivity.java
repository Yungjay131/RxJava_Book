package com.slyworks.rxjava_book;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Observer, NavigationView.OnNavigationItemSelectedListener {
    //region Vars
    private static final String TAG = MainActivity.class.getSimpleName();

     private DrawerLayout drawer;
     private Toolbar toolBar;
     private ActionBarDrawerToggle toggle;
     private NavigationView navigationView;

     private List<AppController.Subscription> mSubscriptionList = new ArrayList<>();
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initSubscriptions();
        initViews();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void initSubscriptions(){
        AppController appController = AppController.getInstance();

        appController.addEvent(Constants.EVENT_GET_FRAGMENT_MANAGER);
        AppController.Subscription sub_1 = appController.subscribeTo(Constants.EVENT_GET_FRAGMENT_MANAGER, (Observer) FragmentTransactionHelper.getInstance());
        appController.notifyObservers(Constants.EVENT_GET_FRAGMENT_MANAGER, getSupportFragmentManager());
        mSubscriptionList.add(sub_1);

        appController.addEvent(Constants.EVENT_UPDATE_NAV_VIEW);
        AppController.Subscription sub_2 = appController.subscribeTo(Constants.EVENT_UPDATE_NAV_VIEW, this);
        mSubscriptionList.add(sub_2);
    }

    private void initViews(){
        _initNavView();
        _initTabLayout();
        inflateFragment(FragmentWrapper.WELCOME);
    }

    private void _initNavView(){
        drawer = findViewById(R.id.drawer_main);

        toolBar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolBar);

        setTitle("");

        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolBar,
                R.string.open_text,
                R.string.close_text
        );
        toggle.syncState();

        drawer.addDrawerListener(toggle);

        navigationView = findViewById(R.id.navigation_main);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void _initTabLayout(){ }

    private void inflateFragment(FragmentWrapper f){
       FragmentTransactionHelper.getInstance().setContainerID(R.id.fragment_container_main);
       FragmentTransactionHelper.getInstance().inflateFragment(f);
    }

    @Override
    public void onBackPressed() {
        if(!FragmentTransactionHelper.getInstance().onBackPressed()){
            //TODO:inflate exit dialog here
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case R.id.action_welcome:{
               inflateFragment(FragmentWrapper.WELCOME);
               if(drawer.isShown()) drawer.closeDrawers();
               return true;
           }
           case R.id.action_chap_01:{
               inflateFragment(FragmentWrapper.CHAP_01);
               if(drawer.isShown()) drawer.closeDrawers();
               return true;
           }
           case R.id.action_chap_02:{
               inflateFragment(FragmentWrapper.CHAP_02);
               if(drawer.isShown()) drawer.closeDrawers();
               return true;
           }
           case R.id.action_chap_03:{
               inflateFragment(FragmentWrapper.CHAP_03);
               if(drawer.isShown()) drawer.closeDrawers();
               return true;
           }
       }

       return false;
    }

    @Override
    public <T> void notify(String event, T data) {
        switch(event){
            case Constants.EVENT_UPDATE_NAV_VIEW:{
                switch ((FragmentWrapper) data){
                    case WELCOME:{
                        navigationView.setCheckedItem(R.id.action_welcome);
                        break;
                    }
                    case CHAP_01:{
                        navigationView.setCheckedItem(R.id.action_chap_01);
                        break;
                    }
                    case CHAP_02:{
                        navigationView.setCheckedItem(R.id.action_chap_02);
                        break;
                    }
                    case CHAP_03:{
                        navigationView.setCheckedItem(R.id.action_chap_03);
                        break;
                    }

                }
            }

        }
    }

}