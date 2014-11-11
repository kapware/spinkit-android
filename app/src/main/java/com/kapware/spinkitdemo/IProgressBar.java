package com.kapware.spinkitdemo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.ViewDebug;
import android.view.animation.Interpolator;



/**
 * Interface to keep standard android Progressbar properties compatible with standard android
 * ProgressBar widget.
 * (javadoc copied from android android.widget.ProgressBar
 *
 * <p>
 * Visual indicator of progress in some operation.  Displays a bar to the user
 * representing how far the operation has progressed; the application can
 * change the amount of progress (modifying the length of the bar) as it moves
 * forward.  There is also a secondary progress displayable on a progress bar
 * which is useful for displaying intermediate progress, such as the buffer
 * level during a streaming playback progress bar.
 * </p>
 *
 * <p>
 * A progress bar can also be made indeterminate. In indeterminate mode, the
 * progress bar shows a cyclic animation without an indication of progress. This mode is used by
 * applications when the length of the task is unknown. The indeterminate progress bar can be either
 * a spinning wheel or a horizontal bar.
 * </p>
 *
 * <p>The following code example shows how a progress bar can be used from
 * a worker thread to update the user interface to notify the user of progress:
 * </p>
 *
 * <pre>
 * public class MyActivity extends Activity {
 *     private static final int PROGRESS = 0x1;
 *
 *     private ProgressBar mProgress;
 *     private int mProgressStatus = 0;
 *
 *     private Handler mHandler = new Handler();
 *
 *     protected void onCreate(Bundle icicle) {
 *         super.onCreate(icicle);
 *
 *         setContentView(R.layout.progressbar_activity);
 *
 *         mProgress = (ProgressBar) findViewById(R.id.progress_bar);
 *
 *         // Start lengthy operation in a background thread
 *         new Thread(new Runnable() {
 *             public void run() {
 *                 while (mProgressStatus &lt; 100) {
 *                     mProgressStatus = doWork();
 *
 *                     // Update the progress bar
 *                     mHandler.post(new Runnable() {
 *                         public void run() {
 *                             mProgress.setProgress(mProgressStatus);
 *                         }
 *                     });
 *                 }
 *             }
 *         }).start();
 *     }
 * }</pre>
 *
 * <p>To add a progress bar to a layout file, you can use the {@code &lt;ProgressBar&gt;} element.
 * By default, the progress bar is a spinning wheel (an indeterminate indicator). To change to a
 * horizontal progress bar, apply the {@link android.R.style#Widget_ProgressBar_Horizontal
 * Widget.ProgressBar.Horizontal} style, like so:</p>
 *
 * <pre>
 * &lt;ProgressBar
 *     style="@android:style/Widget.ProgressBar.Horizontal"
 *     ... /&gt;</pre>
 *
 * <p>If you will use the progress bar to show real progress, you must use the horizontal bar. You
 * can then increment the  progress with {@link #incrementProgressBy incrementProgressBy()} or
 * {@link #setProgress setProgress()}. By default, the progress bar is full when it reaches 100. If
 * necessary, you can adjust the maximum value (the value for a full bar) using the {@link
 * android.R.styleable#ProgressBar_max android:max} attribute. Other attributes available are listed
 * below.</p>
 *
 * <p>Another common style to apply to the progress bar is {@link
 * android.R.style#Widget_ProgressBar_Small Widget.ProgressBar.Small}, which shows a smaller
 * version of the spinning wheel&mdash;useful when waiting for content to load.
 * For example, you can insert this kind of progress bar into your default layout for
 * a view that will be populated by some content fetched from the Internet&mdash;the spinning wheel
 * appears immediately and when your application receives the content, it replaces the progress bar
 * with the loaded content. For example:</p>
 *
 * <pre>
 * &lt;LinearLayout
 *     android:orientation="horizontal"
 *     ... &gt;
 *     &lt;ProgressBar
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         style="@android:style/Widget.ProgressBar.Small"
 *         android:layout_marginRight="5dp" /&gt;
 *     &lt;TextView
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:text="@string/loading" /&gt;
 * &lt;/LinearLayout&gt;</pre>
 *
 * <p>Other progress bar styles provided by the system include:</p>
 * <ul>
 * <li>{@link android.R.style#Widget_ProgressBar_Horizontal Widget.ProgressBar.Horizontal}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Small Widget.ProgressBar.Small}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Large Widget.ProgressBar.Large}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Inverse Widget.ProgressBar.Inverse}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Small_Inverse
 * Widget.ProgressBar.Small.Inverse}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Large_Inverse
 * Widget.ProgressBar.Large.Inverse}</li>
 * </ul>
 * <p>The "inverse" styles provide an inverse color scheme for the spinner, which may be necessary
 * if your application uses a light colored theme (a white background).</p>
 *
 * <p><strong>XML attributes</b></strong>
 * <p>
 * See {@link android.R.styleable#ProgressBar ProgressBar Attributes},
 * {@link android.R.styleable#View View Attributes}
 * </p>
 *
 * @attr ref android.R.styleable#ProgressBar_animationResolution
 * @attr ref android.R.styleable#ProgressBar_indeterminate
 * @attr ref android.R.styleable#ProgressBar_indeterminateBehavior
 * @attr ref android.R.styleable#ProgressBar_indeterminateDrawable
 * @attr ref android.R.styleable#ProgressBar_indeterminateDuration
 * @attr ref android.R.styleable#ProgressBar_indeterminateOnly
 * @attr ref android.R.styleable#ProgressBar_interpolator
 * @attr ref android.R.styleable#ProgressBar_max
 * @attr ref android.R.styleable#ProgressBar_maxHeight
 * @attr ref android.R.styleable#ProgressBar_maxWidth
 * @attr ref android.R.styleable#ProgressBar_minHeight
 * @attr ref android.R.styleable#ProgressBar_minWidth
 * @attr ref android.R.styleable#ProgressBar_mirrorForRtl
 * @attr ref android.R.styleable#ProgressBar_progress
 * @attr ref android.R.styleable#ProgressBar_progressDrawable
 * @attr ref android.R.styleable#ProgressBar_secondaryProgress
 */
public interface IProgressBar {
    /**
     * <p>Indicate whether this progress bar is in indeterminate mode.</p>
     *
     * @return true if the progress bar is in indeterminate mode
     */
    @ViewDebug.ExportedProperty(category = "progress")
    boolean isIndeterminate();

    /**
     * <p>Change the indeterminate mode for this progress bar. In indeterminate
     * mode, the progress is ignored and the progress bar shows an infinite
     * animation instead.</p>
     * <p/>
     * If this progress bar's style only supports indeterminate mode (such as the circular
     * progress bars), then this will be ignored.
     *
     * @param indeterminate true to enable the indeterminate mode
     */
    void setIndeterminate(boolean indeterminate);

    /**
     * <p>Get the drawable used to draw the progress bar in
     * indeterminate mode.</p>
     *
     * @return a {@link android.graphics.drawable.Drawable} instance
     * @see #setIndeterminateDrawable(android.graphics.drawable.Drawable)
     * @see #setIndeterminate(boolean)
     */
    Drawable getIndeterminateDrawable();

    /**
     * <p>Define the drawable used to draw the progress bar in
     * indeterminate mode.</p>
     *
     * @param d the new drawable
     * @see #getIndeterminateDrawable()
     * @see #setIndeterminate(boolean)
     */
    void setIndeterminateDrawable(Drawable d);

    /**
     * <p>Get the drawable used to draw the progress bar in
     * progress mode.</p>
     *
     * @return a {@link android.graphics.drawable.Drawable} instance
     * @see #setProgressDrawable(android.graphics.drawable.Drawable)
     * @see #setIndeterminate(boolean)
     */
    Drawable getProgressDrawable();

    /**
     * <p>Define the drawable used to draw the progress bar in
     * progress mode.</p>
     *
     * @param d the new drawable
     * @see #getProgressDrawable()
     * @see #setIndeterminate(boolean)
     */
    void setProgressDrawable(Drawable d);

    /**
     * <p>Set the current progress to the specified value. Does not do anything
     * if the progress bar is in indeterminate mode.</p>
     *
     * @param progress the new progress, between 0 and {@link #getMax()}
     * @see #setIndeterminate(boolean)
     * @see #isIndeterminate()
     * @see #getProgress()
     * @see #incrementProgressBy(int)
     */
    void setProgress(int progress);

    /**
     * <p>
     * Set the current secondary progress to the specified value. Does not do
     * anything if the progress bar is in indeterminate mode.
     * </p>
     *
     * @param secondaryProgress the new secondary progress, between 0 and {@link #getMax()}
     * @see #setIndeterminate(boolean)
     * @see #isIndeterminate()
     * @see #getSecondaryProgress()
     * @see #incrementSecondaryProgressBy(int)
     */
    void setSecondaryProgress(int secondaryProgress);

    /**
     * <p>Get the progress bar's current level of progress. Return 0 when the
     * progress bar is in indeterminate mode.</p>
     *
     * @return the current progress, between 0 and {@link #getMax()}
     * @see #setIndeterminate(boolean)
     * @see #isIndeterminate()
     * @see #setProgress(int)
     * @see #setMax(int)
     * @see #getMax()
     */
    @ViewDebug.ExportedProperty(category = "progress")
    int getProgress();


    /**
     * <p>Get the progress bar's current level of secondary progress. Return 0 when the
     * progress bar is in indeterminate mode.</p>
     *
     * @return the current secondary progress, between 0 and {@link #getMax()}
     *
     * @see #setIndeterminate(boolean)
     * @see #isIndeterminate()
     * @see #setSecondaryProgress(int)
     * @see #setMax(int)
     * @see #getMax()
     */
    @ViewDebug.ExportedProperty(category = "progress")
    int getSecondaryProgress();

    /**
     * <p>Return the upper limit of this progress bar's range.</p>
     *
     * @return a positive integer
     *
     * @see #setMax(int)
     * @see #getProgress()
     * @see #getSecondaryProgress()
     */
    @ViewDebug.ExportedProperty(category = "progress")
    int getMax();

    /**
     * <p>Set the range of the progress bar to 0...<tt>max</tt>.</p>
     *
     * @param max the upper range of this progress bar
     *
     * @see #getMax()
     * @see #setProgress(int)
     * @see #setSecondaryProgress(int)
     */
    void setMax(int max);

    /**
     * <p>Increase the progress bar's progress by the specified amount.</p>
     *
     * @param diff the amount by which the progress must be increased
     *
     * @see #setProgress(int)
     */
    void incrementProgressBy(int diff);

    /**
     * <p>Increase the progress bar's secondary progress by the specified amount.</p>
     *
     * @param diff the amount by which the secondary progress must be increased
     *
     * @see #setSecondaryProgress(int)
     */
    void incrementSecondaryProgressBy(int diff);

    /**
     * Sets the acceleration curve for the indeterminate animation.
     * The interpolator is loaded as a resource from the specified context.
     *
     * @param context The application environment
     * @param resID The resource identifier of the interpolator to load
     */
    void setInterpolator(Context context, int resID);

    /**
     * Sets the acceleration curve for the indeterminate animation.
     * Defaults to a linear interpolation.
     *
     * @param interpolator The interpolator which defines the acceleration curve
     */
    void setInterpolator(Interpolator interpolator);

    /**
     * Gets the acceleration curve type for the indeterminate animation.
     *
     * @return the {@link Interpolator} associated to this animation
     */
    Interpolator getInterpolator();
}
