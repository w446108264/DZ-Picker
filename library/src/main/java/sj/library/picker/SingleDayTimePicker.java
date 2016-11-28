package sj.library.picker;

import android.content.Context;

import com.bigkoo.pickerview.lib.WheelView;

import java.util.Date;

import sj.library.picker.adapter.MouthDayWheelAdapter;

/**
 * 当前日期到未来日期间的滚轮
 * <p>
 * 1.只显示天
 * 2.格式为  xx月xx日
 * 3.包含3个特殊格式 xx月xx日(今天)  xx月xx日(明天)  xx月xx日(后天)
 *
 * @simple {@code
   TimePickerFactory.create(this, Type.SINGLEDAY).setTimeBuilder(new TimeBuilder.Builder(new Date())
         .setMaxAftertDay(4)
         .build());
   }
 * Created by sj on 2016/11/27.
 */

public class SingleDayTimePicker extends TimePickerWithHead {

    private WheelView wvDay;

    public SingleDayTimePicker(Context context) {
        super(context);
        setPickerContainerView(R.layout.view_picker_singleday);
        wvDay = (WheelView) findViewById(R.id.wv_day);
        setCancelable(true);
    }

    public void setPicker(TimeBuilder timeBuilder) {
        wvDay.setAdapter(new MouthDayWheelAdapter(timeBuilder.startDate, timeBuilder.endData));
        wvDay.setCurrentItem(0);
        wvDay.setCyclic(false);
    }

    @Override
    public TimePicker setTimeBuilder(TimeBuilder builder) {
        this.timeBuilder = builder;
        setPicker(timeBuilder);
        return this;
    }

    @Override
    public Date getSelectDate() {
        if(wvDay == null) {
            return null;
        }
        return ((MouthDayWheelAdapter) wvDay.getAdapter()).getItemReal(wvDay.getCurrentItem());
    }
}

