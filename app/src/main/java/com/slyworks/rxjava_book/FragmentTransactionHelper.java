package com.slyworks.rxjava_book;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Joshua Sylvanus, 8:35 AM, 27-Dec-21.
 */
public class FragmentTransactionHelper implements Observer {
    //region Vars
    private static FragmentTransactionHelper INSTANCE = null;

    private final int NOT_SET = -1;
    private final int FALLBACK_ID = R.id.fragment_container_main;

    private FragmentManager mSupportFragmentManager;

    private int mContainerID = NOT_SET;

    private List<FragmentWrapper> mBackStack = new ArrayList<>();
    private Stack<FragmentWrapper> mBackStack2 = new Stack<>();
    //endregion

    public static FragmentTransactionHelper getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FragmentTransactionHelper();
        }

        return INSTANCE;
    }

    private FragmentTransactionHelper(){}

    public void setContainerID(int ID){ this.mContainerID = ID; }
    public void inflateFragment(FragmentWrapper fragmentWrapper){
        int containerID = (mContainerID == NOT_SET) ? FALLBACK_ID : mContainerID;

        addToBackStack(fragmentWrapper);

        FragmentTransaction transaction = mSupportFragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.replace(containerID, fragmentWrapper.getFragmentClass(), null);
        transaction.commit();
    }
    public boolean onBackPressed(){
       FragmentWrapper instance = getNextFragment();
       if(instance == null) return false;

       inflateFragment(instance);

       AppController.getInstance().notifyObservers(Constants.EVENT_UPDATE_NAV_VIEW, instance);

       return true;
    }

    private void addToBackStack(FragmentWrapper f){
        for(FragmentWrapper fw: mBackStack){ if(fw == f) mBackStack.remove(fw); }
        mBackStack.add(f);
    }

    private FragmentWrapper getNextFragment(){
        /*means this is the first inflated fragment because,
        * size = 1, means there is only one item(FragmentWrapper)
        * but size = 2 means there are 2, hence mBackStack.get(2-2) == FragmentWrapper at 0*/
        if(mBackStack.get(mBackStack.size() - 2) == null) return null;

        //removing the current fragment which is the last
        mBackStack.remove(mBackStack.size()-1);

        FragmentWrapper f = mBackStack.get(mBackStack.size()-1);

        //removing the next which has now been retrieved
        mBackStack.remove(mBackStack.size()-1);

        return f;
    }


    @Override
    public <T> void notify(String event, T data) {
        switch(event){
            case Constants.EVENT_GET_FRAGMENT_MANAGER: {
                 mSupportFragmentManager = (FragmentManager) data;
                 break;
            }

        }
    }
}
