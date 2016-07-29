package com.example.fearless.fearless;


import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.example.fearless.meet.ui.SecondFragment;
import com.example.fearless.invite.ui.ThirdFragment;
import com.example.fearless.user.ui.FourFragment;

import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;


public class FearlessActivity extends FragmentActivity implements SecondFragment.OnFragmentInteractionListener, ThirdFragment.OnFragmentInteractionListener
        , FourFragment.OnFragmentInteractionListener{
    //fragment管理
    private FragmentTransaction fragmentTransaction;
    //三个fragment
    private Fragment secondFragment, thirdFragment, fourFragment;
    private RadioGroup group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ButterKnife.inject(this);
        //否则登录成功
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        group = (RadioGroup) findViewById(R.id.group);
        changeTag(R.id.one);
        group.check(R.id.one);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fearless, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @OnCheckedChanged(R.id.two)
    void secondCheckChange(boolean ischeck) {
        if (ischeck) {
            changeTag(R.id.two);
        }
    }

    @OnCheckedChanged(R.id.one)
    void thirdCheckChange(boolean ischeck) {
        if (ischeck) {
            changeTag(R.id.one);
        }
    }

    @OnCheckedChanged(R.id.four)
    void fourCheckChange(boolean ischeck) {
        if (ischeck) {
            changeTag(R.id.four);
        }
    }

    void changeTag(int id) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideall();
        switch (id) {
            case R.id.two:
                if (secondFragment == null) {
                    secondFragment = SecondFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_content, secondFragment);
                } else {
                    fragmentTransaction.show(secondFragment);
                }
                break;
            case R.id.one:
                if (thirdFragment == null) {
                    thirdFragment = ThirdFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_content, thirdFragment);
                } else {
                    fragmentTransaction.show(thirdFragment);
                }
                break;
            case R.id.four:
                if (fourFragment == null) {
                    fourFragment = FourFragment.newInstance();
                    fragmentTransaction.add(R.id.fl_content, fourFragment);
                } else {
                    fragmentTransaction.show(fourFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }

    private void hideall() {

        if (secondFragment != null) {
            fragmentTransaction.hide(secondFragment);
        }
        if (thirdFragment != null) {
            fragmentTransaction.hide(thirdFragment);
        }
        if (fourFragment != null) {
            fragmentTransaction.hide(fourFragment);
        }
    }


}
