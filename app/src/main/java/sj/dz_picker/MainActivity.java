package sj.dz_picker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

import sj.library.picker.DHMTimePicker;
import sj.library.picker.TimeBuilder;
import sj.library.picker.TimePicker;
import sj.library.picker.TimePickerFactory;
import sj.library.picker.util.TimeUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerFactory.create(MainActivity.this, TimePickerFactory.Type.SINGLEDAY)
                        .setTimeBuilder(new TimeBuilder.Builder(new Date())
                                .setMaxAftertDay(4)
                                .build())
                        .setTitle("起飞时间")
                        .setOnTimeSelectListener(new TimePicker.OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Object date) {
                                Toast.makeText(MainActivity.this, "" + date, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        findViewById(R.id.btn_date_didi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuilder builder = new TimeBuilder.Builder(TimeUtils.getSpecifiedDateTimeBySeconds(new Date(), 1200))
                        .setMaxAftertHour(26)
                        .build();

                ((DHMTimePicker)TimePickerFactory.create(MainActivity.this, TimePickerFactory.Type.DHM))
                        .setTimeBuilder(builder, "立即用车")
                        .setOnTimeSelectListener(new TimePicker.OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Object date) {
                                Toast.makeText(MainActivity.this, "" + date, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setTitle("用车时间")
                        .show();
            }
        });
    }
}
