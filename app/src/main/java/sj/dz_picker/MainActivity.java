package sj.dz_picker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Date;

import sj.library.picker.TimeBuilder;
import sj.library.picker.TimePicker;
import sj.library.picker.TimePickerFactory;
import sj.library.picker.util.TimeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimeBuilder builder = new TimeBuilder.Builder(TimeUtils.getSpecifiedDateTimeBySeconds(new Date(), 1200))
                .setMaxAftertHour(26)
                .build();

        TimePickerFactory.create(this, TimePickerFactory.Type.DHM)
                .setTimeBuilder(builder)
                .setOnTimeSelectListener(new TimePicker.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Object date) {
                        Toast.makeText(MainActivity.this, "" + date, Toast.LENGTH_SHORT).show();
                    }
                })
                .setTitle("用车时间")
                .show();
    }
}
