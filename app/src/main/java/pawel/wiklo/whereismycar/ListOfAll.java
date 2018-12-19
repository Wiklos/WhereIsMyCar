package pawel.wiklo.whereismycar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.orm.SugarContext;

import java.util.List;

public class ListOfAll extends AppCompatActivity {

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


    }
}
