package kr.main.heydr.view.search.pharmacy;

import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import kr.main.heydr.R;

public class PaneListener implements SlidingPaneLayout.PanelSlideListener {
    private PharmacyDetailActivity pharmacyDetailActivity;
    @Override
    public void onPanelSlide(@NonNull View panel, float slideOffset) {

        Log.e("Tat","PANEL SLIDING SLIDE");

    }

    @Override
    public void onPanelOpened(@NonNull View panel) {

        Log.e("Tat","PANEL SLIDING OPENSS");
    }

    @Override
    public void onPanelClosed(@NonNull View panel) {
        System.out.println("Panel sliding");
        Log.e("Tat","PANEL SLIDING CLOSEDDD");
    }
}
