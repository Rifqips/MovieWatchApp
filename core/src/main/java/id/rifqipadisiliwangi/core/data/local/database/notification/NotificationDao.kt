package id.rifqipadisiliwangi.core.data.local.database.notification

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query


@Dao
interface NotificationDao {

    @Query("UPDATE notification_keys_migrate_two SET isChecked = :isChecked WHERE notifId = :id ")
    fun notifIsChecked(id: Int, isChecked: Boolean)

    @Query("SELECT * FROM notification_keys_migrate_two WHERE isChecked = :isChecked")
    fun getUnreadNotifications(isChecked: Boolean): LiveData<List<NotificationKey>?>

    @Query(
        "INSERT INTO notification_keys_migrate_two (notifId," +
                "notifType," +
                "notifTitle, " +
                "notifBody, " +
                "notifDate, " +
                "notifTime, " +
                "notifImage, " +
                "isChecked) values (:notifId, :notifType, :notifTitle, :notifBody, :notifDate, :notifTime, :notifImage, :isChecked)"
    )
    suspend fun createNotification(
        notifId: Int,
        notifType: String,
        notifTitle: String,
        notifBody: String,
        notifDate: String,
        notifTime: String,
        notifImage: String,
        isChecked: Boolean
    )

    @Query("SELECT * FROM notification_keys_migrate_two")
    fun getNotifications(): LiveData<List<NotificationKey>?>

    @Query("DELETE FROM notification_keys_migrate_two")
    fun deleteAllNotif()
}