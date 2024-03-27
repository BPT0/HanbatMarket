package com.hanbat.hanbatmarket.custom_component

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import com.hanbat.hanbatmarket.R

class CustomEditText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatEditText(context, attrs, defStyleAttr) {

    init {
        // 텍스트 입력창 배경 설정
        setBackgroundResource(R.drawable.etx_bg_round_30dp)

        // 힌트 텍스트 색상 설정
        setHintTextColor(resources.getColor(R.color.text_hint_color))

        // 글자 색상 설정
        setTextColor(resources.getColor(R.color.text_color))

        // 커서 색상 변경
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            setTextCursorDrawable(R.drawable.etx_cursor)
        }

        // 입력 라인 제한
        maxLines = 1

        // 엔터 입력시 다음 창으로 이동하게 하기
        imeOptions = EditorInfo.IME_ACTION_NEXT

    }

}
