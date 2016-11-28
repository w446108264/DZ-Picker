# DZ-Picker
 
> j.s 🇨🇳
 
<img src="https://github.com/w446108264/DZ-Picker/raw/master/art/screen1.jpg" /> 

# Samples Usage

```java

// 效果1
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
                        
                        
// 效果2 
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
                        
```
 
## You can [download a sample APK](https://github.com/w446108264/DZ-Picker/raw/master/art/simple.apk) 

# Thanks

* [https://github.com/saiwu-bigkoo/Android-PickerView](https://github.com/saiwu-bigkoo/Android-PickerView)