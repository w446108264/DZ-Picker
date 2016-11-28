package sj.library.picker;

import android.content.Context;

import com.bigkoo.pickerview.lib.WheelView;
import com.bigkoo.pickerview.listener.OnItemSelectedListener;

import java.util.Calendar;
import java.util.Date;

import sj.library.picker.adapter.HourWheelAdapter;
import sj.library.picker.adapter.MinuteWheelAdapter;
import sj.library.picker.adapter.MouthDayWheelAdapter;
import sj.library.picker.util.TimeUtils;

/**
 * 当前日期到未来日期间的滚轮 ->3轴<-
 * <p>
 * 1.3轴 显示 日期 小时 分钟
 * 2.分钟为 10分钟进制
 * @simple
 * Created by sj on 2016/11/27.
 */
public class DHMTimePicker extends TimePickerWithHead {

    private WheelView wvDay;
    private WheelView wvHour;
    private WheelView wvMinute;

    private Calendar calendar;

    private OnItemSelectedListener dayWheelListener;
    private OnItemSelectedListener hourWheelListener;

    public DHMTimePicker(Context context) {
        super(context);
        setPickerContainerView(R.layout.view_picker_dhm);
        wvDay = (WheelView) findViewById(R.id.wv_day);
        wvHour = (WheelView) findViewById(R.id.wv_hour);
        wvMinute = (WheelView) findViewById(R.id.wv_minute);
        setCancelable(true);
        calendar = Calendar.getInstance();
    }

    public void setPicker(TimeBuilder timeBuilder) {

        this.timeBuilder = timeBuilder;

        fixStartTime();

        initOnItemSelectedListener();

        wvDay.setAdapter(new MouthDayWheelAdapter(timeBuilder.startDate, timeBuilder.endData));
        wvDay.setCurrentItem(1);
        wvDay.setCyclic(false);
        wvDay.setOnItemSelectedListener(dayWheelListener);
        wvDay.setTextSize(16);

        calendar.setTime(timeBuilder.startDate);
        int selectHours = calendar.get(Calendar.HOUR_OF_DAY);

        /**
         * 起始时间和结束时间是同一天， 计算结束时间最大的hour
         */
        if(TimeUtils.isSameDay(timeBuilder.startDate, timeBuilder.endData)) {
            calendar.setTime(timeBuilder.endData);
            int selectHoursEnd = calendar.get(Calendar.HOUR_OF_DAY);
            wvHour.setAdapter(new HourWheelAdapter(selectHours, selectHoursEnd));
        } else {
            wvHour.setAdapter(new HourWheelAdapter(selectHours, 23));
        }
        wvHour.setCurrentItem(0);
        wvHour.setCyclic(false);
        wvHour.setOnItemSelectedListener(hourWheelListener);
        wvHour.setTextSize(16);

        calendar.setTime(timeBuilder.startDate);
        int selectMinute = calendar.get(Calendar.MINUTE);
        calendar.setTime(timeBuilder.endData);
        int selectMinuteEnd = calendar.get(Calendar.MINUTE);
        int start = (int) Math.ceil(selectMinute / (float) 10);
        int end = (int) Math.ceil(selectMinuteEnd / (float) 10);
        wvMinute.setAdapter(new MinuteWheelAdapter(start, 5));
        wvMinute.setCurrentItem(0);
        wvMinute.setCyclic(false);
        wvMinute.setTextSize(16);

    }

    /**
     * 由于业务需求，每次只去 % 10 整点的时间 即可 0 10 20 30 40 50
     * 必然有时间到达临界值
     * 直接修正startTime 为下一个 % 10 分整点的时间
     *
     * @simple 2016-11-26 23:56 修正为 2016-11-27 0:0
     */
    private void fixStartTime() {
        timeBuilder.startDate = TimeUtils.getNextNowTime(timeBuilder.startDate);
        timeBuilder.endData = TimeUtils.getNextNowTime(timeBuilder.endData);
    }

    /**
     * 初始化联动监听器
     */
    private void initOnItemSelectedListener() {

        /**
         * 日期滚轮 联动监听
         */
        dayWheelListener = dayWheelListener == null ? new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (wvHour == null) {
                    return;
                }

                /**
                 * 特殊的数据 非日期
                 */
                if(((MouthDayWheelAdapter) wvDay.getAdapter()).isSpecialData(index)) {
                    wvHour.setAdapter(new HourWheelAdapter());
                    wvMinute.setAdapter(new MinuteWheelAdapter());
                    return;
                }

                // 获取当前选择的小时 的 位置
                int positionHour = wvHour.getCurrentItem();

                // 获取当前选择的日期
                Date selectDate = ((MouthDayWheelAdapter) wvDay.getAdapter()).getItemReal(wvDay.getCurrentItem());
                // 获取当前选择的小时
                int selectHour = ((HourWheelAdapter) wvHour.getAdapter()).getItemReal(positionHour);

                // 获取起始的日期
                Date startDate = timeBuilder.startDate;
                // 起始日期距离当前选择的日期 判断是否是 该天
                int diff = TimeUtils.diffDateWithDay(startDate, selectDate);

                // 如果是当天，重新调整时间，显示剩余时间 当前 x - 23
                if (diff == 0) {
                    // 起始时间 的 小时
                    calendar.setTime(startDate);
                    int startHours = calendar.get(Calendar.HOUR_OF_DAY);

                    // 当前选择的小时大于 起始时间的小时 最小值
                    if (selectHour < startHours) {
                        positionHour = 0;
                    } else {
                        positionHour = selectHour - startHours;
                    }
                    wvHour.setAdapter(new HourWheelAdapter(startHours, 23));
                }
                // 最后一天，判断小时截止时间
                else if(TimeUtils.isSameDay(timeBuilder.endData, selectDate)) {

                    // 起始时间 的 小时
                    calendar.setTime(timeBuilder.endData);
                    int endHours = calendar.get(Calendar.HOUR_OF_DAY);

                    // 当前选择的小时大于 结束时间的小时 最大值
                    if (selectHour > endHours) {
                        positionHour = endHours;
                    } else {
                        positionHour = selectHour;
                    }

                    wvHour.setAdapter(new HourWheelAdapter(0, endHours));
                }
                // 非当天，显示全部时间 0-23
                else {
                    positionHour = selectHour;
                    wvHour.setAdapter(new HourWheelAdapter(0, 23));
                }
                wvHour.setCurrentItem(positionHour);

                // 联动 minute 滚轮
                if (wvMinute != null) {
                    hourWheelListener.onItemSelected(positionHour);
                }
            }
        } : dayWheelListener;

        /**
         * 小时滚轮 联动监听
         */
        hourWheelListener = hourWheelListener == null ? new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (wvMinute == null) {
                    return;
                }

                index = index < 0 ? 0 : index;

                // 获取当前选择的日期
                Date selectDate = ((MouthDayWheelAdapter) wvDay.getAdapter()).getItemReal(wvDay.getCurrentItem());

                if(selectDate == null) {
                    return;
                }

                // 获取当前选择的小时
                int selectHour = ((HourWheelAdapter) wvHour.getAdapter()).getItemReal(index);

                // 获取起始的日期
                Date startDate = timeBuilder.startDate;
                // 起始日期距离当前选择的日期 判断是否是 该天
                int diff = TimeUtils.diffDateWithDay(startDate, selectDate);

                // 起始时间 的 小时
                calendar.setTime(startDate);
                int startHours = calendar.get(Calendar.HOUR_OF_DAY);

                // 获取当前选择的分钟
                int positionMinute = wvMinute.getCurrentItem();
                int selectMinute = ((MinuteWheelAdapter) wvMinute.getAdapter()).getItemReal(positionMinute);

                // 如果是当天 且是当前小时
                if (diff == 0 && selectHour == startHours) {
                    // 起始时间 的 分钟
                    int startMinute = calendar.get(Calendar.MINUTE);
                    int start = (int) Math.ceil(startMinute / (float) 10);

                    if (selectMinute < start) {
                        positionMinute = 0;
                    } else {
                        positionMinute = selectMinute - start;
                    }
                    wvMinute.setAdapter(new MinuteWheelAdapter(start, 5));
                }
                // 非当天 当前小时，显示全部时间 0 10 20 30 40 50
                else {
                    positionMinute = selectMinute;
                    wvMinute.setAdapter(new MinuteWheelAdapter(0, 5));
                }
                wvMinute.setCurrentItem(positionMinute);
            }
        } : hourWheelListener;
    }

    @Override
    public TimePicker setTimeBuilder(TimeBuilder builder) {
        this.timeBuilder = builder;
        setPicker(timeBuilder);
        return this;
    }

    @Override
    public Object getSelectDate() {
        if(wvDay == null) {
            return null;
        }

        // 特殊数据
        if(((MouthDayWheelAdapter) wvDay.getAdapter()).isSpecialData(wvDay.getCurrentItem())) {
            return ((MouthDayWheelAdapter) wvDay.getAdapter()).getSpecialData(wvDay.getCurrentItem());
        }

        Date date = ((MouthDayWheelAdapter) wvDay.getAdapter()).getItemReal(wvDay.getCurrentItem());
        calendar.setTime(date);
        int hour = ((HourWheelAdapter) wvHour.getAdapter()).getItemReal(wvHour.getCurrentItem());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        int minute = ((MinuteWheelAdapter) wvMinute.getAdapter()).getItemRealShowTime(wvMinute.getCurrentItem());
        calendar.set(Calendar.MINUTE, minute);
        date = calendar.getTime();
        return date;
    }
}

