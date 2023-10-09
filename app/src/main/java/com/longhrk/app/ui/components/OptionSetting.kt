package com.longhrk.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.longhrk.app.R
import com.longhrk.app.ui.theme.textColor

@Composable
fun OptionSetting(
    modifier: Modifier,
    textContent: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = textContent,
            style = TextStyle(
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = textColor,
            )
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            tint = textColor,
            contentDescription = null
        )
    }
}