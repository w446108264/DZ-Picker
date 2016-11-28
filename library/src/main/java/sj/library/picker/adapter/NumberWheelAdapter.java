package sj.library.picker.adapter;

import com.bigkoo.pickerview.adapter.WheelAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 纯数字 可计算类型的 滚轮基类 Adapter
 * 可参考
 * 小时滚轮{@link HourWheelAdapter}
 * 进制单位为10分钟的 分钟滚轮{@link MinuteWheelAdapter}
 * Created by sj on 2016/11/27.
 */

public abstract class NumberWheelAdapter implements WheelAdapter {

    protected List<Integer> dateList;
    protected HashMap<String, Integer> dataCache;

    /**
     * Constructor
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public NumberWheelAdapter(int minValue, int maxValue) {
        if(minValue > maxValue) {
            throw new IllegalStateException("maxValue not less than minValue");
        }
        dateList = new ArrayList<>();
        for(int i = minValue ; i < maxValue + 1 ; i++) {
            dateList.add(i);
        }
    }

    /**
     * 默认构造函数 插入一个负数 -> 用作空数据识别
     */
    public NumberWheelAdapter() {
        dateList = new ArrayList<>();
        dateList.add(-1);
    }

    @Override
    public Object getItem(int index) {
        if(dateList == null) {
            return "";
        }

        // 空数据
        if(dateList.get(index) < 0) {
            return "";
        }

        // 其他数据
        String showStr = getShowContent(index);
        putDataCache(showStr, dateList.get(index));
        return showStr;
    }

    public int getItemReal(int index) {
        if(dateList == null) {
            return 0;
        }
        index = index >= dateList.size() ? dateList.size() - 1 : index;
        return dateList.get(index);
    }

    @Override
    public int getItemsCount() {
        return dateList == null ? 0 : dateList.size();
    }

    @Override
    public int indexOf(Object o){
        return dateList.indexOf(getDataCache(o.toString()));
    }

    protected void putDataCache(String showContent, Integer integer) {
        if(dataCache == null) {
            dataCache = new HashMap<>();
        }

        if(!dataCache.containsKey(showContent)) {
            dataCache.put(showContent, integer);
        }
    }

    protected Object getDataCache(String showContent) {
        if(dataCache == null) {
            return null;
        }
        return dataCache.get(showContent);
    }

    public abstract String getShowContent(int index) ;
}
