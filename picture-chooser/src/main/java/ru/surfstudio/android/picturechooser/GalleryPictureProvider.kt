package ru.surfstudio.android.picturechooser

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import io.reactivex.Observable
import ru.surfstudio.android.core.ui.base.navigation.activity.navigator.ActivityNavigator
import ru.surfstudio.android.core.ui.base.navigation.activity.route.ActivityWithResultRoute
import ru.surfstudio.android.picturechooser.exceptions.ActionInterruptedException

/**
 * Позволяет получить одно или несколько изображений через сторонее приложение
 */
class GalleryPictureProvider(private val activityNavigator: ActivityNavigator,
                             private val activity: Activity) {

    fun openGalleryForSingleImage(): Observable<String> {
        val route = GallerySingleImageRoute()

        val result = activityNavigator.observeResult<String>(route)
                .flatMap { result ->
                    if (result.isSuccess) {
                        Observable.just(result.data)
                    } else {
                        Observable.error(ActionInterruptedException())
                    }
                }

        activityNavigator.startForResult(route)
        return result
    }

    fun openGalleryForMultipleImage(): Observable<List<String>> {
        val route = GalleryMultipleImageRoute()

        val result = activityNavigator.observeResult<ArrayList<String>>(route)
                .flatMap { result ->
                    if (result.isSuccess) {
                        Observable.just(result.data)
                    } else {
                        Observable.error(ActionInterruptedException())
                    }
                }
                .map { t -> t as List<String> }

        activityNavigator.startForResult(route)
        return result
    }

    private inner class GallerySingleImageRoute : ActivityWithResultRoute<String>() {
        override fun prepareIntent(context: Context?): Intent {
            return Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT, EXTERNAL_CONTENT_URI)
                    .apply { type = "image/*" },
                    activity.getString(R.string.choose_app))
        }

        override fun parseResultIntent(intent: Intent?): String? {
            return if (intent != null && intent.data != null) {
                intent.data!!.toString()
            } else null
        }
    }

    private inner class GalleryMultipleImageRoute : ActivityWithResultRoute<ArrayList<String>>() {
        override fun prepareIntent(context: Context?): Intent {
            return Intent.createChooser(Intent(Intent.ACTION_GET_CONTENT, EXTERNAL_CONTENT_URI)
                    .apply {
                        type = "image/*"
                        action = Intent.ACTION_GET_CONTENT
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    },
                    activity.getString(R.string.choose_app))
        }

        override fun parseResultIntent(intent: Intent?): ArrayList<String>? {
            return if (intent != null && intent.data != null) {
                arrayListOf(intent.data!!.toString())
            } else if (intent != null && intent.clipData != null) {
                (0 until intent.clipData.itemCount).mapTo(ArrayList<String>()) { intent.clipData.getItemAt(it).uri.toString() }
            } else {
                null
            }
        }
    }
}
