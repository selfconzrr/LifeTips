package com.example.zhangruirui.lifetips

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_count_down.*
import java.util.concurrent.TimeUnit

/**
 * 使用 RxJava 和 RxBinding 实现倒计时获取验证码的功能
 */
class CountDownActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)

        RxView.clicks(btn_get_code)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                // 点击后设置为不可点击
                .doOnNext { RxView.enabled(btn_get_code).accept(false) }
                .subscribe {
                    // 从0开始发射11个数字为：0-10依次输出，延时0s执行，每1s发射一次。
                    Flowable.intervalRange(0, 11, 0, 1, TimeUnit.SECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            // 每次发射数字更新 UI
                            .doOnNext {
                                RxTextView.text(btn_get_code).accept("重新获取（" + (10 - it) + ")")
                            }
                            .doOnComplete {
                                RxView.enabled(btn_get_code).accept(true)
                            }
                            .subscribe()
                }
    }
}
