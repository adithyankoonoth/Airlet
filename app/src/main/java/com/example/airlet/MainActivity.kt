package com.example.airlet

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var coLevelTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        coLevelTextView = findViewById(R.id.coLevelTextView)
        statusTextView = findViewById(R.id.statusTextView)

        database = FirebaseDatabase.getInstance("https://airlert-2025-default-rtdb.asia-southeast1.firebasedatabase.app/").reference


        setupFirebaseListener()
    }

    private fun setupFirebaseListener() {
        val deviceId = "CAR_01"
        val dataRef = database.child("$deviceId/data")

        val dataListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val coLevel = snapshot.child("mq7_co_level").getValue(Int::class.java)
                    val isAlertTriggered = snapshot.child("alert_triggered").getValue(Boolean::class.java)

                    coLevelTextView.text = if (coLevel != null) {
                        getString(R.string.co_level_label, coLevel.toString())
                    } else {
                        getString(R.string.co_level_na)
                    }

                    if (isAlertTriggered == true) {
                        statusTextView.text = getString(R.string.status_danger)
                        val notificationTitle = getString(R.string.notification_title)
                        val notificationMessage = getString(R.string.notification_message, coLevel?.toString() ?: "N/A")
                        sendAlertNotification(notificationTitle, notificationMessage)
                    } else {
                        statusTextView.text = getString(R.string.status_safe)
                    }
                } else {
                    statusTextView.text = getString(R.string.status_waiting)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                statusTextView.text = getString(R.string.status_error)
            }
        }
        dataRef.addValueEventListener(dataListener)
    }

    private fun sendAlertNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "CAR_SAFETY_ALERT_CHANNEL"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = getString(R.string.notification_channel_description)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        notificationManager.notify(1, notificationBuilder.build())
    }
}
