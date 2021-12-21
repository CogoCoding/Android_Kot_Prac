package com.example.kot6.kot10

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.example.kot6.R
import java.util.*

class kot10:AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kot10)
        //STEP0 뷰를 초기화
        initOnOffButton()
        initChangeAlarmTimeButton()

        //STEP1 데이터 가져오기
        val model = fetchDataFromSharedPreferences()
        renderView(model)

        //STEP2 뷰에 데이터 그려주기
    }

    private fun renderView(model: AlarmDisplayModel) {
        findViewById<TextView>(R.id.ampmTv).apply {
            text = model.ampmText
        }
        findViewById<TextView>(R.id.timeTv).apply {
            text = model.timeText
        }
        findViewById<TextView>(R.id.onoffBtn).apply {
            text = model.onOffText
            tag = model
        }

    }

    //java의 static과 같음
    companion object{
        private const val SHARED_PREFERENCES_NAME = "time"
        private const val ALARM_KEY = "alarm"
        private const val ONOFF_KEY = "onoff"
        private const val ALARM_REQUEST_CODE = 1000
    }

    private fun fetchDataFromSharedPreferences(): AlarmDisplayModel {
        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val timeDBValue = sharedPreferences.getString(ALARM_KEY, "09:30") ?: "09:30"
        val onOffDBValue = sharedPreferences.getBoolean(ONOFF_KEY, false)
        val alarmData = timeDBValue.split(":")

        val alarmModel = AlarmDisplayModel(
            hour = alarmData[0].toInt(),
            min = alarmData[1].toInt(),
            onOff = onOffDBValue
        )
        //보정 예외처리
        val pendingIntent = PendingIntent.getBroadcast(this,
            ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE)
        if ((pendingIntent == null) and alarmModel.onOff) {
            //알람은 꺼져있는데, 데이터는 켜져있는 경우
            alarmModel.onOff = false
        } else if ((pendingIntent != null) and alarmModel.onOff.not()) {
            //알람 켜져있는데, 데이터는 꺼져있는경우
            //알람 취소
            cancelAlarm()
        }
        return alarmModel
    }


    private fun initOnOffButton() {
        val onoffBtn = findViewById<Button>(R.id.onoffBtn)
        onoffBtn.setOnClickListener {
            val model = it.tag as? AlarmDisplayModel ?: return@setOnClickListener
            val newModel = saveAlarmModel(model.hour,model.min,model.onOff.not())
            renderView(newModel)
            if(newModel.onOff){
                //온 -> 알람 등록
                val calendar = Calendar.getInstance().apply{
                    set(Calendar.HOUR_OF_DAY,newModel.hour)
                    set(Calendar.MINUTE,newModel.min)
                    if(before(Calendar.getInstance())){//현재시간가져와서 비교
                        add(Calendar.DATE,1)
                    }
                }
                val alarmManager = getSystemService(Context.ALARM_SERVICE)as AlarmManager
                val intent = Intent(this,AlarmReceiver::class.java)
                val pendingIntent = PendingIntent.getBroadcast(this, ALARM_REQUEST_CODE,intent,PendingIntent.FLAG_UPDATE_CURRENT)
                alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.timeInMillis,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
                )
            }else{
                //오프 -> 알람 제거
            }
            //데이터 확인
            //온오프 따라 작업 처리
            //데이터 저장
        }
    }

    private fun cancelAlarm(){
        val pendingIntent = PendingIntent.getBroadcast(this,
            ALARM_REQUEST_CODE,
            Intent(this, AlarmReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE)
        //기존에 있던 알람 삭제
        pendingIntent?.cancel()
    }
    private fun initChangeAlarmTimeButton(){
        val changeAlarmButton = findViewById<Button>(R.id.changeAlarmTimeBtn)

        changeAlarmButton.setOnClickListener {
            //현재시간 그냥 가져오기
            //TimePickDialog로 시간 설정하고, 그 시간을 가져와서
            val calendar = Calendar.getInstance()
            TimePickerDialog(this, { picker, hour, min ->
                //데이터 저장
                val model = saveAlarmModel(hour,min,false)
                renderView(model)
               //기존에 있던 알람 삭제
                cancelAlarm()
            },calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
                .show()
        }
    }

    private fun saveAlarmModel(
        hour :Int,
        min :Int,
        onOff :Boolean
    ):AlarmDisplayModel {
        val model = AlarmDisplayModel(
            hour = hour,
            min = min,
            onOff = false
        )

        val SharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)
        with(SharedPreferences.edit()) {
            putString(ALARM_KEY, model.makeDataForDB())
            putBoolean(ONOFF_KEY, model.onOff)
            commit()
        }
        return model
    }


}
