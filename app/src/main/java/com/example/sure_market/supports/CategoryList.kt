package com.example.sure_market.supports

import androidx.compose.ui.res.stringResource
import com.example.sure_market.R
import com.example.sure_market.data.UserSharedPreference

object CategoryList {
    fun getCategoryList() = listOf(
        "디지털",
        "생활가전",
        "가구/인테리어",
        "생활/주방",
        "유아의류",
        "유아도서",
        "여성의류",
        "남성패션",
        "뷰티/미용",
        "스포츠/레저",
    )
    fun getCategoryIconList() = listOf<Int>(
        R.drawable.digital,
        R.drawable.home_appliances,
        R.drawable.furniture,
        R.drawable.kitchen,
        R.drawable.baby_clothes,
        R.drawable.book,
        R.drawable.female_clothes,
        R.drawable.male_clothes,
        R.drawable.beauty,
        R.drawable.sport_leisure
    )
}