package com.meishe.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.meishe.base.R;
import com.meishe.base.utils.FormatUtils;
import com.meishe.base.utils.SizeUtils;

/**
 * The type My seek bar view.
 * 调节滑杆
 *
 * @author liupanfeng
 */
public class SeekBarTextView extends LinearLayout {

    private OnSeekBarListener mListener;
    /**
     * The M seek bar.
     */
    public SeekBar mSeekBar;
    private TextView mProgressText;
    private View mTextViewLayout;
    private int type = -1;
    /**
     * The constant TYPE_TRANSITION_TIME.
     */
    public static final int TYPE_TRANSITION_TIME = 0;

    /**
     * Instantiates a new Seek bar text view.
     *
     * @param context the context
     */
    public SeekBarTextView(Context context) {
        super(context);
        initView(context);
    }


    /**
     * Instantiates a new Seek bar text view.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public SeekBarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    /**
     * Instantiates a new Seek bar text view.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public SeekBarTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.view_seekbar_textview, this);
        mSeekBar = rootView.findViewById(R.id.seek_bar);
        mProgressText = rootView.findViewById(R.id.tv_progress_text);
        mTextViewLayout = rootView.findViewById(R.id.seek_text_layout);
        initListener();

    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(int type) {
        this.type = type;
    }


    /**
     * Gets max progress.
     *
     * @return the max progress
     */
    public float getMaxProgress() {
        return mSeekBar.getMax() + minProgress;
    }

    /**
     * set max progress.
     */
    public void setMaxProgress(int max) {
        if (mSeekBar != null) {
            mSeekBar.setMax(max - minProgress);
        }
    }


    private int minProgress = 0;

    /**
     * set min progress.
     */
    public void setMinProgress(int min) {
        this.minProgress = min;
    }


    /**
     * Gets  progress.
     *
     * @return the  progress
     */
    public float getProgress() {
        return mSeekBar.getProgress() + minProgress;
    }

    /**
     * Gets min progress.
     *
     * @return the min progress
     */
    public float getMinProgress() {
        return 0;
    }

    /**
     * Sets init data.
     *
     * @param max     the max
     * @param current the current
     */
    public void setInitData(int max, int current) {
        if (mSeekBar != null) {
            mSeekBar.setMax(max);
            mSeekBar.setProgress(current);
        }
    }

    private void initListener() {
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mListener != null) {
                    progress = progress + minProgress;
                    mListener.onProgressChanged(seekBar, progress, fromUser);
                    if (type == -1) {
                        mProgressText.setText(progress + "%");
                    } else {
                        mProgressText.setText(FormatUtils.objectFormat2String(progress / 10.0f) + "s");
                    }
                    setTextLocation(seekBar, progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mProgressText.setVisibility(VISIBLE);
                if (mListener != null) {
                    mListener.onStartTrackingTouch(seekBar);
                }

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mListener != null) {
                    mListener.onStopTrackingTouch(seekBar.getProgress());
                }

            }
        });
    }

    /**
     * 设置进度文字距离seekbar的距离
     * Set progress text margin.
     *
     * @param margin the margin
     */
    public void setProgressTextMargin(int margin) {
        if (mTextViewLayout != null) {
            LayoutParams params = (LayoutParams) mTextViewLayout.getLayoutParams();
            params.bottomMargin = margin;
            mTextViewLayout.setLayoutParams(params);
        }
    }

    /**
     * 设置进度数字显示
     * Sets progress text visible.
     *
     * @param progress the progress
     */
    public void setProgressTextVisible(final int progress) {
        post(new Runnable() {
            @Override
            public void run() {
                if (mProgressText != null) {
                    mProgressText.setVisibility(VISIBLE);
                    mProgressText.setText(progress + "%");
                    setTextLocation(mSeekBar, progress);
                }
            }
        });

    }

    /**
     * Sets listener.
     *
     * @param listener the listener
     */
    public void setListener(OnSeekBarListener listener) {
        this.mListener = listener;
    }

    /**
     * Sets seek progress.
     * 设置拖动条进度
     *
     * @param progress the progress 进度
     */
    public void setProgress(int progress) {
        if (mSeekBar != null) {
            mSeekBar.setProgress(progress - minProgress);
        }
    }

    /**
     * The interface On seek bar listener.
     * 拖动条监听的接口
     */
    public interface OnSeekBarListener {

        /**
         * On stop tracking touch.
         * 停止触摸追踪
         *
         * @param progress the progress 进度
         */
        void onStopTrackingTouch(int progress);

        /**
         * On progress changed.
         * 改变进度
         *
         * @param seekBar  the seek bar 拖动条
         * @param progress the progress 进度
         * @param fromUser the from user 来自用户
         */
        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);

        /**
         * On start tracking touch.
         * 开始触摸追踪
         *
         * @param seekBar the seek bar
         */
        void onStartTrackingTouch(SeekBar seekBar);

    }

    private void setTextLocation(SeekBar seekBar, int progress) {
        //获取文本宽度
        float textWidth = mProgressText.getWidth();

        //获取seekbar最左端的x位置
        float left = seekBar.getLeft();

        //进度条的刻度值
        float max = Math.abs(getMaxProgress());

        //这不叫thumb的宽度,叫seekbar距左边宽度,seekbar 不是顶格的，两头都存在一定空间，所以xml 需要用paddingStart 和 paddingEnd 来确定具体空了多少值,
        float thumb = SizeUtils.dp2px(15);

        //每移动1个单位，text应该变化的距离 = (seekBar的宽度 - 两头空的空间) / 总的progress长度0
        float average = (((float) seekBar.getWidth()) - 2 * thumb) / max;
        float currentProgress = progress;

        //textview 应该所处的位置 = seekbar最左端 + seekbar左端空的空间 + 当前progress应该加的长度 - textview宽度的一半(保持居中作用)
        float pox = left - textWidth / 2 + thumb + average * currentProgress;
        mTextViewLayout.setX(pox);
    }


}
