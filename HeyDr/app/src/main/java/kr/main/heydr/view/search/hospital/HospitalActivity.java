package kr.main.heydr.view.search.hospital;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ramotion.expandingcollection.ECBackgroundSwitcherView;
import com.ramotion.expandingcollection.ECCardData;
import com.ramotion.expandingcollection.ECPagerView;
import com.ramotion.expandingcollection.ECPagerViewAdapter;

import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.adapter.CardListItemAdapter;
import kr.main.heydr.adapter.HosCardListItemAdapter;
import kr.main.heydr.adapter.IntentResource;
import kr.main.heydr.view.search.pharmacy.CardDataImpl;
import kr.main.heydr.view.search.pharmacy.PharmacyActivity;

public class HospitalActivity extends AppCompatActivity {
    private TextView list_item_text;
    private ECPagerView ecPagerView;
    private FrameLayout container;
    private IntentResource intentResource;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        intentResource = new IntentResource(intent);

        // Get pager from layout
        ecPagerView = (ECPagerView) findViewById(R.id.ec_pager_element);

        // Generate example dataset
        List<ECCardData> dataset = hosptalCardDataImpl.generateExampleData();

        // Implement pager adapter and attach it to pager view
        ECPagerViewAdapter ecPagerViewAdapter = new ECPagerViewAdapter(getApplicationContext(), dataset) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, final ListView list, ECCardData data) {
                // Data object for current card
                hosptalCardDataImpl cardData = (hosptalCardDataImpl) data;

                // Set adapter and items to current card content list
                final List<String> listItems = cardData.getListItems();
                Context context = getApplicationContext();
                final HosCardListItemAdapter listItemAdapter = new HosCardListItemAdapter(context, listItems);
                list.setAdapter(listItemAdapter);
                // Also some visual tuning can be done here
                list.setBackgroundColor(Color.WHITE);

                // Here we can create elements for head view or inflate layout from xml using inflater service
                TextView cardTitle = new TextView(getApplicationContext());
                cardTitle.setText(cardData.getCardTitle());
                cardTitle.setTextSize(COMPLEX_UNIT_DIP, 20);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                head.addView(cardTitle, layoutParams);

                // Card toggling by click on head element
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        ecPagerView.toggle();

                    }
                });
            }
        };
        ecPagerView.setPagerViewAdapter(ecPagerViewAdapter);
        // Add background switcher to pager view
        ecPagerView.setBackgroundSwitcherView((ECBackgroundSwitcherView) findViewById(R.id.ec_bg_switcher_element));
        // Directly modifying dataset
        dataset.remove(2);
        ecPagerViewAdapter.notifyDataSetChanged();

        ecPagerView.setOnCardSelectedListener(new ECPagerView.OnCardSelectedListener() {

            @Override
            public void cardSelected(int i, int i1, int i2) {
                Log.e("VSESESSE", i+"i??? ???!!!!!!!!!!!");

                Log.e("VSESESSE", i1+"i??? ???!!!!!!!!!!!");

                Log.e("VSESESSE", i2+"i??? ???!!!!!!!!!!!");

            }


        });

    }

    // Card collapse on back pressed
    @Override
    public void onBackPressed() {
        if (!ecPagerView.collapse())
            super.onBackPressed();
    }
}