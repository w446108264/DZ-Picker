package sj.library.picker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sj on 2016/11/27.
 */

public class TimeBuilder {

    public Date startDate;
    public Date endData;

    private TimeBuilder(TimeBuilder.Builder builder) {
        if(builder != null) {
            startDate = builder.startDate;
            endData = builder.endData;
        }
    }

    public static TimeBuilder instance(Date startDate, Date endData) {
        return new TimeBuilder(new Builder(startDate, endData));
    }

    public static class Builder {

        Date startDate;
        Date endData;

        public Builder(Date startDate, Date endData) {
            if(startDate == null) {
                throw new NullPointerException("startDate can't be null!");
            }
            if(endData == null) {
                throw new NullPointerException("endData can't be null!");
            }
            this.startDate = startDate;
            this.endData = endData;
        }

        public Builder(Date startDate) {
            if(startDate == null) {
                throw new NullPointerException("startDate can't be null!");
            }
            this.startDate = startDate;
        }

        public TimeBuilder Builder(Date startDate, Date endData) {
            if(startDate == null) {
                throw new NullPointerException("startDate can't be null!");
            }
            this.startDate = startDate;
            return new TimeBuilder(this);
        }

        /**
         * 起始时间之后最长截止时间
         * 天
         * @return
         */
        public Builder setMaxAftertDay(int aftertDays) {
            if(endData != null) {
                throw new IllegalStateException("Can only be set once!");
            }
            endData = startDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endData);
            calendar.add(Calendar.DAY_OF_MONTH, aftertDays);
            endData = calendar.getTime();
            return this;
        }

        /**
         * 起始时间之后最长截止时间 如果 24小时后
         * 小时
         * @return
         */
        public Builder setMaxAftertHour(int aftertHour) {
            if(endData != null) {
                throw new IllegalStateException("Can only be set once!");
            }
            endData = startDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endData);
            calendar.add(Calendar.HOUR, aftertHour);
            endData = calendar.getTime();
            return this;
        }

        /**
         * 起始时间之后最长截止时间
         * 分钟
         * @return
         */
        public Builder setMaxAftertMinute(int aftertMinute) {
            if(endData != null) {
                throw new IllegalStateException("Can only be set once!");
            }
            endData = startDate;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endData);
            calendar.add(Calendar.MINUTE, aftertMinute);
            endData = calendar.getTime();
            return this;
        }

        public TimeBuilder build() {
            return new TimeBuilder(this);
        }
    }
}
