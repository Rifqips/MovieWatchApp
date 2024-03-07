package id.rifqipadisiliwangi.core.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import id.rifqipadisiliwangi.core.data.local.database.cart.CartDao
import id.rifqipadisiliwangi.core.data.local.database.cart.CartKey
import id.rifqipadisiliwangi.core.data.local.database.movie.MovieKey
import id.rifqipadisiliwangi.core.data.local.database.movie.RemoteKeys
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationDao
import id.rifqipadisiliwangi.core.data.local.database.notification.NotificationKey
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionDao
import id.rifqipadisiliwangi.core.data.local.database.transaction.TransactionKey
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistDao
import id.rifqipadisiliwangi.core.data.local.database.wishlist.WishlistKey
import id.rifqipadisiliwangi.core.utils.Constant.APP_DATABASE_NAME

@Database(
    entities = [CartKey::class, WishlistKey::class, NotificationKey::class, RemoteKeys::class, MovieKey::class, TransactionKey::class],
    version = 6,
    exportSchema = false,
)
abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
    abstract fun cartDao(): CartDao
    abstract fun notificationDao(): NotificationDao
    abstract fun transactionDao(): TransactionDao
    companion object {

        private var INSTANCE : ApplicationDatabase? = null

        val MIGRATION_5_6: Migration = object : Migration(5,6){
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE cart_keys_migrate RENAME TO cart_keys_migrate_two")
                db.execSQL("ALTER TABLE wishlist_keys_migrate RENAME TO wishlist_keys_migrate_two")
                db.execSQL("ALTER TABLE notification_keys_migrate RENAME TO notification_keys_migrate_two")
                db.execSQL("ALTER TABLE transaction_keys_migrate RENAME TO transaction_keys_migrate_two")
                db.execSQL("ALTER TABLE movie_keys_migrate RENAME TO movie_keys_migrate_two")
                db.execSQL("ALTER TABLE remote_keys_migrate RENAME TO remote_keys_migrate_two")
            }
        }


        fun getInstance(context: Context): ApplicationDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApplicationDatabase::class.java,
                    APP_DATABASE_NAME
                )
                    .addMigrations(MIGRATION_5_6)
                    .build()
                INSTANCE = instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

}