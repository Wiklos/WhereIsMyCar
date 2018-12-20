package pawel.wiklo.whereismycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class ListOfAll extends AppCompatActivity {

    List<DataBaseValues> noteList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_all);
        SugarContext.init(this);
        List<DataBaseValues> myList = DataBaseValues.listAll(DataBaseValues.class);

        TextView tv = (TextView)findViewById(R.id.textView);

        for (DataBaseValues value : myList)
        {
            tv.setText(tv.getText()+"\n"+value.getData()+" "+value.getLat()+" "+value.getLen());
        }


                List<DataBaseValues> notes = DataBaseValues.listAll(DataBaseValues.class);
        for(DataBaseValues item : notes){
            noteList.add(item);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewList);
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        List<String> input = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            input.add("Test" + i);
        }// define an adapter
        mAdapter = new MyAdapter(myList);
        recyclerView.setAdapter(mAdapter);

//        List<DataBaseValues> notes = DataBaseValues.listAll(DataBaseValues.class);
//        for(DataBaseValues item : notes){
//            noteList.add(item);
//        }
//        mAdapter.notifyDataSetChanged();


    }
}
