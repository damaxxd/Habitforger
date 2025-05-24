package com.gr6.habitforger.core.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.gr6.habitforger.core.domain.AlarmScheduler
import com.gr6.habitforger.core.domain.IntentActions
import com.gr6.habitforger.core.domain.NotificationReceiver
import com.gr6.habitforger.habits.domain.Habit
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class NotificationAlarmScheduler(
    private val context: Context
): AlarmScheduler {

    private val tag = "NotificationAlarmScheduler"
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    // schedule notifications for habit
    override fun schedule(item: Habit) {
        var time = item.time

        while (time.isBefore(LocalDateTime.now())) {
            time = time.plusDays(1)
        }

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("1", item.title)
            putExtra("2", item.description)
            putExtra("3", item.id)
            action = IntentActions.HABIT_NOTIFICATION.action
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        Log.d(tag, "Scheduled notification for ${time.format(DateTimeFormatter.ofPattern("dd/MM/ hh:mm a"))}")
    }

    // cancel habit notifications
    override fun cancel(item: Habit) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id.hashCode(),
                Intent(context, NotificationReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )

        Log.d(tag, "Cancelled notification for ${item.title}")
    }
}