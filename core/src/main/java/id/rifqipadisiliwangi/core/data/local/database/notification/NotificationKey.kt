package id.rifqipadisiliwangi.core.data.local.database.notification

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notification_keys_migrate_two")
data class NotificationKey(

    @field:ColumnInfo(name = "notifId")
    @field:PrimaryKey
    val notifId: Int = 0,

    @field:ColumnInfo(name = "notifType")
    val notifType: String,

    @field:ColumnInfo("notifTitle")
    val notifTitle: String,

    @field:ColumnInfo("notifBody")
    val notifBody: String,

    @field:ColumnInfo("notifDate")
    val notifDate: String,

    @field:ColumnInfo("notifTime")
    val notifTime: String,

    @field:ColumnInfo("notifImage")
    val notifImage: String,

    @field:ColumnInfo("isChecked")
    var isChecked: Boolean,
)
