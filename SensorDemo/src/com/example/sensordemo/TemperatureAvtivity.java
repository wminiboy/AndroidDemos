package com.example.sensordemo;

import android.app.Activity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class TemperatureAvtivity extends Activity {

    private  SensorManager mSensorManager ;
    private  SensorEventListener mSensorListener;
    private  Sensor mSensor;

    private int mRate = SensorManager.SENSOR_DELAY_NORMAL;
    private int mCycle = 100; // milliseconds
    private int mEventCycle = 100; // milliseconds
    private float mAccuracy = 0;

    private long lastUpdate = -1;
    private long lastEvent = -1;

    private float value = -999f;

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        mSensorManager = (SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);

        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);

        mSensorListener = new SensorEventListener() {
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
            }

            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() != Sensor.TYPE_TEMPERATURE)
                    return;
                long curTime = System.currentTimeMillis();
                if (lastUpdate == -1 || (curTime - lastUpdate) > mCycle) {
                    lastUpdate = curTime;
                    float lastValue = value;
                    value = event.values[SensorManager.DATA_X];
                    if (lastEvent == -1 || (curTime - lastEvent) > mEventCycle) {
                        if (Math.abs(value - lastValue) > mAccuracy) {
                            lastEvent = curTime;
                        }
                    }
                }
            }
        };

    }
    
    @Override
    protected void onResume() {
        if (isReady()) {
            mSensorManager.registerListener(mSensorListener, mSensor, mRate);
        }
    }

    @Override
    protected void onStop() {
        if (isReady()) {
            mSensorManager.unregisterListener(mSensorListener);
        }
    }

    public String getLastKnownValue() {
        return (value == -999) ? "null" : String.valueOf(value);
    }

    public void setCycle(int milliseconds) {
        mCycle = milliseconds;
    }

    public int getCycle() {
        return mCycle;
    }

    public void setEventCycle(int milliseconds) {
        mEventCycle = milliseconds;
    }

    public int getEventCycle() {
        return mEventCycle;
    }

    public void setAccuracy(float value) {
        mAccuracy = Math.abs(value);
    }

    public float getAccuracy() {
        return mAccuracy;
    }

    public boolean isReady() {
        return (mSensor == null) ? false : true;
    }

}
