package com.demo.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

class CharAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater inflater;
    private List<Character> mainDataList = null;
    private ArrayList<Character> arraylist;

    public CharAdapter(Context context, List<Character> mainDataList) {

        mContext = context;
        this.mainDataList = mainDataList;
        this.arraylist = new ArrayList<Character>();
        this.arraylist.addAll(mainDataList);
        inflater = LayoutInflater.from(mContext);
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvAge;
        ImageView Image;
    }

    @Override
    public int getCount() {
        return mainDataList.size();
    }

    @Override
    public Character getItem(int position) {
        return mainDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();

            view = inflater.inflate(R.layout.char_item, null);
            holder.tvName = (TextView) view.findViewById(R.id.os_texts);
            holder.tvAge = (TextView) view.findViewById(R.id.os_texts2);
            holder.Image = (ImageView) view.findViewById(R.id.os_images);

            view.setTag(holder);
            view.setTag(R.id.os_texts, holder.tvName);
            view.setTag(R.id.os_texts2, holder.tvAge);
            view.setTag(R.id.os_images, holder.Image);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        String date = mainDataList.get(position).getCharAge();
        String DOB = "";
        try {
            String[] dateParts = date.split("-");
            String day = dateParts[0];
            String month = dateParts[1];
            String year = dateParts[2];
            DOB = CalculateDOB(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            holder.tvAge.setText(DOB + " Years old");
        } catch (Exception e) {
            holder.tvAge.setText(mainDataList.get(position).getCharAge());
        }


        holder.tvName.setText(mainDataList.get(position).getCharName());


        Picasso.get().load(mainDataList.get(position).getCharUrl()).into(holder.Image);

        return view;
    }

    private String CalculateDOB(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(year, month, day);
        int ageYear = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            ageYear--;
        }
        Integer ageYearInt = new Integer(ageYear);
        String ageS1 = ageYearInt.toString();
        return ageS1;
    }

}
