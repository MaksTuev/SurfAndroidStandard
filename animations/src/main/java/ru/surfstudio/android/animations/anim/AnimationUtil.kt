package ru.surfstudio.android.animations.anim

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TimeInterpolator
import android.support.transition.*
import android.support.v4.view.ViewCompat
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator

/**
 * Утилиты для работы с анимацией
 */
object AnimationUtil {

    const val ANIM_ENTERING = 225L
    const val ANIM_LEAVING = 195L
    const val ANIM_LARGE_TRANSITION = 375L
    const val ANIM_TRANSITION = 300L
    const val ANIM_PULSATION = 600L

    /**
     * Смена двух вью с эффектом fadeIn/fadeOut
     */
    fun crossfadeViews(inView: View, outView: View,
                       duration: Long = ANIM_LARGE_TRANSITION,
                       visibility: Int = View.GONE,
                       endAction: (() -> Unit)? = null) {
        fadeIn(inView, duration, endAction)
        fadeOut(outView, duration, visibility, endAction)
    }

    /**
     * Сокрытие вью с изменением прозрачности
     */
    fun fadeOut(outView: View,
                duration: Long = ANIM_LEAVING,
                visibility: Int = View.GONE,
                endAction: (() -> Unit)? = null) {

        ViewCompat.animate(outView)
                .alpha(0f)
                .setDuration(duration)
                .setInterpolator(LinearOutSlowInInterpolator())
                .withEndAction {
                    outView.visibility = visibility
                    endAction?.invoke()
                }
    }

    /**
     * Появление вью с изменением прозрачности
     */
    fun fadeIn(inView: View, duration: Long = ANIM_ENTERING, endAction: (() -> Unit)? = null) {
        if (inView.visibility == View.VISIBLE) return

        inView.alpha = 0f
        inView.visibility = View.VISIBLE
        ViewCompat.animate(inView)
                .alpha(1f)
                .setDuration(duration)
                .setInterpolator(FastOutLinearInInterpolator())
                .withEndAction {
                    endAction?.invoke()
                }
    }

    /**
     * Анимация типа "пульс"
     */
    fun pulseAnimation(view: View,
                       scale: Float = 1.15f,
                       duration: Long = ANIM_PULSATION,
                       repeatCount: Int = ObjectAnimator.INFINITE,
                       repeatMode: Int = ObjectAnimator.REVERSE,
                       interpolator: TimeInterpolator = FastOutLinearInInterpolator()): ObjectAnimator {
        val animation = ObjectAnimator.ofPropertyValuesHolder(
                view,
                PropertyValuesHolder.ofFloat(View.SCALE_X, scale),
                PropertyValuesHolder.ofFloat(View.SCALE_Y, scale))
        animation.duration = duration

        animation.repeatCount = repeatCount
        animation.repeatMode = repeatMode
        animation.interpolator = interpolator

        animation.start()

        return animation
    }

    /**
     * Изменение ширины и высоты вью
     */
    fun toSize(view: View,
               newWidth: Int? = null,
               newHeight: Int? = null,
               duration: Long = ANIM_TRANSITION) {
        val transition = TransitionSet()
                .addTransition(ChangeBounds())
        transition.duration = duration
        beginTransitionSafe(view, transition)

        val lp = view.layoutParams
        newWidth?.let { lp.width = it }
        newHeight?.let { lp.height = it }
        view.layoutParams = lp
    }

    /**
     * Появление вью с эффектом "слайда" в зависимости от gravity
     */
    fun slideIn(view: View,
                gravity: Int,
                duration: Long = ANIM_ENTERING,
                interpolator: TimeInterpolator = LinearInterpolator(),
                startAction: ((View) -> Unit)? = null,
                endAction: ((View) -> Unit)? = null) {
        if (view.visibility == View.GONE) {
            slide(view, gravity, duration, interpolator, endAction, startAction)
            view.visibility = View.VISIBLE
        }
    }

    /**
     * Исчезновение вью с эффектом "слайда" в зависимости от gravity
     */
    fun slideOut(view: View,
                 gravity: Int,
                 duration: Long = ANIM_LEAVING,
                 interpolator: TimeInterpolator = LinearInterpolator(),
                 startAction: ((View) -> Unit)? = null,
                 endAction: ((View) -> Unit)? = null) {
        if (view.visibility == View.VISIBLE) {
            slide(view, gravity, duration, interpolator, endAction, startAction)
            view.visibility = View.GONE
        }
    }

    private fun slide(view: View,
                      gravity: Int,
                      duration: Long,
                      interpolator: TimeInterpolator,
                      endAction: ((View) -> Unit)?,
                      startAction: ((View) -> Unit)?) {
        val slide = TransitionSet()
                .addTransition(Slide(gravity))
                .addTransition(ChangeBounds())
        slide.duration = duration
        slide.interpolator = interpolator
        slide.addListener(object : Transition.TransitionListener {
            override fun onTransitionEnd(transition: Transition) {
                endAction?.invoke(view)
            }

            override fun onTransitionResume(transition: Transition) {
            }

            override fun onTransitionPause(transition: Transition) {
            }

            override fun onTransitionCancel(transition: Transition) {
            }

            override fun onTransitionStart(transition: Transition) {
                startAction?.invoke(view)
            }
        })

        beginTransitionSafe(view, slide)
    }

    private fun beginTransitionSafe(view: View, transition: TransitionSet) {
        if (view.parent !is ViewGroup) {
            throw ClassCastException("View.parent is not ViewGroup!")
        }
        TransitionManager.beginDelayedTransition(view.parent as ViewGroup, transition)
    }
}