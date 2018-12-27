package pawel.wiklo.whereismycar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<DataBaseValues> values;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtDate;
        public TextView txtName;
        public TextView txtId;
        public TextView txtLat;
        public TextView txtLon;
        public View layout;

        public ImageButton rightArrowImageButton;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtDate = (TextView) v.findViewById(R.id.date);
            txtName = (TextView) v.findViewById(R.id.name);
            //txtId = (TextView) v.findViewById(R.id.id);
            //txtLat = (TextView) v.findViewById(R.id.lat);
            //txtLon = (TextView) v.findViewById(R.id.lon);

            rightArrowImageButton = v.findViewById(R.id.rightArrowImageButton);
        }
    }

    public void add(int position, DataBaseValues item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<DataBaseValues> myDataset) {
        values = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.listitem, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final String name = String.valueOf(values.get(position));
        final DataBaseValues item = values.get(position);
        holder.txtDate.setText(item.getData());
        holder.rightArrowImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove(position);


                DataBaseValues value = DataBaseValues.findById(DataBaseValues.class, item.getId());

                Intent intent = new Intent(view.getContext(), DetailedInfo.class);
                //intent.putExtra("name", value.getNazwa());
                //intent.putExtra("date", value.getData());
                //intent.putExtra("lat", value.getLat());
                //intent.putExtra("lon", value.getLon());
                intent.putExtra("Id", item.getId());
                view.getContext().startActivity(intent);

//                Intent intent = new Intent(v.getContext(), DetailedInfo.class);
//
//                v.getContext().startActivity(intent);
            }
        });
        holder.txtName.setText(item.getNazwa());
        //holder.txtId.setText("Id: " + item.getId());
        //holder.txtLat.setText("Lat: " + item.getLat());
        //holder.txtLon.setText("Lon: " + item.getLon());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return values.size();
    }

}