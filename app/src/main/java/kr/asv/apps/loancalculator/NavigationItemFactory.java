package kr.asv.apps.loancalculator;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;

import kr.asv.apps.loancalculator.fragments.EqualPrincipalFragment;
import kr.asv.apps.loancalculator.fragments.FullAmortizationFragment;

/**
 * Created by Administrator on 2016-06-08.
 */
public class NavigationItemFactory {
    private static NavigationItemFactory instance = new NavigationItemFactory();

    public static NavigationItemFactory getInstance() {
        return instance;
    }

    /**
     * 기본 로딩 할 첫번째 항목
     */
    public void onNavigationItemFirst(FragmentActivity fragmentActivity)
    {
        EqualPrincipalFragment fragment = new EqualPrincipalFragment();
        replaceFragments(fragmentActivity,fragment,false);
        DrawerLayout drawer = (DrawerLayout) fragmentActivity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }
    /**
     * 메뉴에서 항목을 선택한 경우에 발생
     * @param fragmentActivity
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(FragmentActivity fragmentActivity,MenuItem item) {
        int id = item.getItemId();
        boolean isAction = false;

        if (id == R.id.nav_calculator_equalprincipal) {
            /*
             */
            EqualPrincipalFragment fragment = new EqualPrincipalFragment();
            replaceFragments(fragmentActivity,fragment,true);
            isAction = true;

        } else if (id == R.id.nav_calculator_fullamortization) {
            FullAmortizationFragment fragment = new FullAmortizationFragment();
            replaceFragments(fragmentActivity,fragment,true);
            isAction = true;
        }
        /*
        액티비티 또는 프레그먼트 호출 후에 처리.
        navigationDrawer(메뉴부분) 을 close 하는 부분.
        해당 메뉴가 없을 시에는 Snackbar 호출
         */
        if(isAction) {
            DrawerLayout drawer = (DrawerLayout) fragmentActivity.findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        } else {
            View view = fragmentActivity.getCurrentFocus();
            Snackbar.make(view, "준비중입니다", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        return isAction;
    }

    /**
     * 프레그먼트 를 교체할 경우에 발생
     * @param fragmentActivity 보통은 MainActivity 를 넘긴다. 그게 아닌 경우 일반 Activity 를 넘긴다.
     * @param fragment 프레그먼트 정보
     * @param backStack backstack 을 남길지 여부. true 인 경우는 히스토리에 남긴다.
     */
    public void replaceFragments(FragmentActivity fragmentActivity, Fragment fragment, Boolean backStack) {
        FragmentManager fragmentManager;
        fragmentManager = fragmentActivity.getSupportFragmentManager();

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);

        if (backStack) {
            fragmentTransaction.addToBackStack(null);//히스토리에 남긴다.
        }
        fragmentTransaction.commit();
    }
}
