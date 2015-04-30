package edu.uta.cse.distributedmeetingscheduler;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class StableArrayAdapter extends ArrayAdapter<String> {

    HashMap<Integer, String> mIdMap = new HashMap<Integer, String>();

    ArrayList<String> emptylist = new ArrayList<String>();
    public StableArrayAdapter(Context context, int textViewResourceId,
                              List<String> objects, List<String> list) {
        super(context, textViewResourceId, list);
        list = new ArrayList<String>();
        super.clear();
        for (int i = 0; i < objects.size(); ++i) {
            String[] values = objects.get(i).split(",");
            mIdMap.put(Integer.valueOf(values[0]), values[1]);
            list.add(values[1]);
        }
      //  super.clear();
        super.addAll(list);
        //  super(context, textViewResourceId, objects);
    }

    @Override
    public long getItemId(int position) {
       String item = getItem(position);
        String[] value;
      //  if (item.contains(",")) {
       //     value = item.split(",");
            //  return mIdMap.get(item);

            for (Map.Entry<Integer, String> e : mIdMap.entrySet()) {
                if (e.getValue() == item)
                    return e.getKey();
            }
     //   }
        return -1;
    }

    /*@Override
    public String getItem(int position) {
       // return super.getItem(position);
        String item = getItem(position);
        return item;
    }*/

    @Override
    public boolean hasStableIds() {
        return true;
    }

}
