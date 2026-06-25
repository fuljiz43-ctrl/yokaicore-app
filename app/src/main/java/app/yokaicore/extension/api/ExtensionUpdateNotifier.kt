package app.yokaicore.extension.api

import android.content.Context
import androidx.core.app.NotificationCompat
import app.yokaicore.R
import app.yokaicore.data.notification.NotificationReceiver
import app.yokaicore.data.notification.Notifications
import app.yokaicore.util.system.cancelNotification
import app.yokaicore.util.system.notify
import yokaicore.core.i18n.pluralStringResource
import yokaicore.i18n.MR

class ExtensionUpdateNotifier(private val context: Context) {

    fun promptUpdates(names: List<String>) {
        context.notify(
            Notifications.ID_UPDATES_TO_EXTS,
            Notifications.CHANNEL_EXTENSIONS_UPDATE,
        ) {
            setContentTitle(
                context.pluralStringResource(
                    MR.plurals.update_check_notification_ext_updates,
                    names.size,
                    names.size,
                ),
            )
            val extNames = names.joinToString(", ")
            setContentText(extNames)
            setStyle(NotificationCompat.BigTextStyle().bigText(extNames))
            setSmallIcon(R.drawable.ic_extension_24dp)
            setContentIntent(NotificationReceiver.openExtensionsPendingActivity(context))
            setAutoCancel(true)
        }
    }

    fun dismiss() {
        context.cancelNotification(Notifications.ID_UPDATES_TO_EXTS)
    }
}
