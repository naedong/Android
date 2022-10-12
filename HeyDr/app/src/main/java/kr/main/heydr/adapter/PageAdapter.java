package kr.main.heydr.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

import kr.main.heydr.view.Fragment.EndFragment;
import kr.main.heydr.view.Fragment.MyFragment;
import kr.main.heydr.view.Fragment.MypageFragment;

public class PageAdapter  extends FragmentStateAdapter {

    public int mCount;

    public PageAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if(index== 2 ) return new EndFragment();
        else if(index==1) return new MypageFragment();
        else
        return  new MyFragment();
    }

    @Override
    public int getItemCount() {
        return 2000;
    }

    public int getRealPosition(int position) { return position % mCount; }

}

