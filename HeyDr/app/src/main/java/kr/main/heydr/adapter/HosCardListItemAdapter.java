package kr.main.heydr.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;

import com.ramotion.expandingcollection.ECCardContentListItemAdapter;

import java.util.ArrayList;
import java.util.List;

import kr.main.heydr.R;
import kr.main.heydr.view.search.hospital.HospitalDetailActivity;
import kr.main.heydr.view.search.pharmacy.PharmacyActivity;
import kr.main.heydr.view.search.pharmacy.PharmacyDetailActivity;

public class HosCardListItemAdapter extends ECCardContentListItemAdapter<String> {
    private IntentResource intentResource;
    private Intent intent;
    private ArraySet<Object> items;
    private static String shopname;

    public HosCardListItemAdapter(@NonNull Context context, int resource, @NonNull List<String> objects, PharmacyActivity activity) {
        super(context, resource, objects);
        this.context = context;
    }

    private Context context;

    public HosCardListItemAdapter(@NonNull Context context, @NonNull List<String> objects) {
        super(context, R.layout.list_item, objects);
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        View rowView = convertView;
        intentResource = new IntentResource(intent);
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.itemText = (TextView) rowView.findViewById(R.id.list_item_text);
            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        final String item = getItem(position);
        if (item != null) {
            viewHolder.itemText.setText(item);
        }
        // Example of changing/removing card list items
        viewHolder.itemText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView view = (TextView) v;
                        Log.e("TATATAGADAPTER", ((TextView) v).getText()+"의 ㄱ밧이입ㄴ다");
                //v.setBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.colorPrimary));
                Intent gotoShowMoreRiple;
                gotoShowMoreRiple = new Intent(context, HospitalDetailActivity.class);
                gotoShowMoreRiple.putExtra("name", ((TextView) v).getText().toString());
                gotoShowMoreRiple.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(gotoShowMoreRiple);

                //                   CardListItemAdapter.this.remove(item);
       //                 CardListItemAdapter.this.notifyDataSetChanged();

            }
        });
        return rowView;
    }

    static class ViewHolder {
        TextView itemText;
    }
    public void refreshAdapter(ArrayList<String> items)
    {
        this.items.clear();
        this.items.addAll(items);
        notifyDataSetChanged();}
}
