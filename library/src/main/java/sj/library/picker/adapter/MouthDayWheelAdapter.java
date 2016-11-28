package sj.library.picker.adapter;

import com.bigkoo.pickerview.adapter.WheelAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sj.library.picker.util.TimeUtils;

/**
 * Created by sj on 2016/11/27.
 */

public class MouthDayWheelAdapter implements WheelAdapter {

    private Date startDate;
    private List<Date> dateList;
    private HashMap<String, Date> dataCache;
    private final List<String> specialList = new ArrayList<>();

    private ThreadLocal<SimpleDateFormat> dateFormaterWhole = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("MM月dd日");
        }
    };

    public MouthDayWheelAdapter(Date startDate, Date endData) {
        this.startDate = startDate;
        dateList = TimeUtils.getDaysListBetweenDates(startDate, endData);
    }

    public void addSpecialData(String data) {
        specialList.add(data);
    }

    public void addSpecialData(List<String> list) {
        if(list != null) {
            specialList.addAll(list);
        }
    }

    @Override
    public Object getItem(int index) {
        // 特殊数据
        if(isSpecialData(index)) {
            return specialList.get(index);
        }

        index -= specialList.size();
        // 正常数据
        if(dateList == null) {
            return "";
        }
        String showStr = dateFormaterWhole.get().format(dateList.get(index)) + getSpecifiedTimeDescribe(dateList.get(index));
        putDataCache(showStr, dateList.get(index));
        return showStr;
    }

    /**
     * 是否为特殊数据
     * 特殊数据 -> 可以执行清空其他联动器数据 {@link sj.library.picker.DHMTimePicker#initOnItemSelectedListener()}
     * @return
     */
    public boolean isSpecialData(int index) {
        return index < specialList.size();
    }

    /**
     * 获取特殊数据
     * @param index
     * @return
     */
    public String getSpecialData(int index) {
        return specialList.get(index);
    }

    /**
     * 获取真实的数据 而非展示的数据
     * @param index
     * @return
     */
    public Date getItemReal(int index) {
        if(dateList == null) {
            return null;
        }
        index -= specialList.size();
        index = index < 0 ? 0 : index;
        return dateList.get(index);
    }

    @Override
    public int getItemsCount() {
        return dateList == null ? specialList.size() : specialList.size() + dateList.size();
    }

    @Override
    public int indexOf(Object o) {
        return specialList.size() + dateList.indexOf(getDataCache(o.toString()));
    }

    private void putDataCache(String showContent, Date date) {
        if(dataCache == null) {
            dataCache = new HashMap<>();
        }

        if(!dataCache.containsKey(showContent)) {
            dataCache.put(showContent, date);
        }
    }

    private Object getDataCache(String showContent) {
        if(dataCache == null) {
            return null;
        }
        return dataCache.get(showContent);
    }

    /**
     * 特殊的时间处理
     * @param diffData
     * @return
     */
    private String getSpecifiedTimeDescribe(Date diffData) {
        int diff = TimeUtils.diffDateWithDay(startDate, diffData);
        switch (diff) {
            case 0:
                return "(今天)";
            case 1:
                return "(明天)";
            case 2:
                return "(后天)";
        }
        return "";
    }
}
