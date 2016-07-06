package kr.asv.apps.loancalculator.fragments;

import android.support.v4.app.Fragment;
import android.view.View;

import kr.asv.apps.loancalculator.MainActivity;

/**
 * Created by Administrator on 2016-04-08.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * 자기 자신 Fragment
     */
    protected View fragmentView;//fragment view

    /**
     * Fragment 에서 자신을 지정
     * @param view
     */
    public void setFragmentView(View view)
    {
        fragmentView = view;
    }
    /**
     * findViewById 를 편하게 사용하기 위해서 생성
     * @return View
     * @author hong seok-hoon
     */
    public View findViewById(int id)
    {
        return this.fragmentView.findViewById(id);
    }

    /**
     * 로그 보여주기
     * @param msg
     */
    protected void debug(String msg)
    {
        MainActivity activity = (MainActivity)getActivity();
        activity.debug(msg);
    }
    /**
     * fragment 교체시(또는 이동시)
     * @param fragment
     */
    protected void replaceFragments(Fragment fragment)
    {
        MainActivity activity = (MainActivity)getActivity();
        activity.replaceFragments(fragment);
    }
    /**
     * fragment 교체시(또는 이동시)
     * @param fragment
     */
    protected void replaceFragments(Fragment fragment, Boolean backStack)
    {
        MainActivity activity = (MainActivity)getActivity();
        activity.replaceFragments(fragment,backStack);
    }

    /**
     * 키보드 내리기
     */
    protected void hideSoftKeyboard() {
        MainActivity activity = (MainActivity)getActivity();
        activity.hideSoftKeyboard();
    }

    /**
     * 액션바 타이틀 변경
     */
    protected void setActionBarTitle(String title) {
        MainActivity activity = (MainActivity)getActivity();
        activity.setActionBarTitle(title);
    }

    /**
     * 액션바 타이틀 변경
     */
    protected void setActionBarTitle(int titleId) {
        MainActivity activity = (MainActivity)getActivity();
        activity.setActionBarTitle(getResources().getString(titleId));
    }
}
