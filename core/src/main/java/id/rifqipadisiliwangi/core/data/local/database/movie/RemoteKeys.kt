package id.rifqipadisiliwangi.core.data.local.database.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys_migrate_two")
data class RemoteKeys(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo("prevKey")
    val prevKey: Int?,
    @ColumnInfo("nextKey")
    val nextKey: Int?
)