package com.example.sure_market.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.sure_market.R
import com.example.sure_market.viewmodel.PostViewModel

@Composable
fun TitleTextField(
    viewModel: PostViewModel,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester
) {
    BasicTextField(
        value = viewModel.title.value,
        onValueChange = { viewModel.setTitle(it) },
        textStyle = TextStyle(color = Color.Black),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                innerTextField()
                if (viewModel.title.value == "") {
                    Text(text = stringResource(id = R.string.post_title), color = Color.Gray)
                }
            }
        }
    )
}

@Composable
fun ContentTextField(
    viewModel: PostViewModel,
    focusRequester: FocusRequester,
    focusManager: FocusManager
) {
    BasicTextField(
        value = viewModel.content.value,
        onValueChange = { viewModel.setContent(it) },
        textStyle = TextStyle(color = Color.Black),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .defaultMinSize(minHeight = (4 * 32).dp)
            .focusRequester(focusRequester),
        maxLines = Int.MAX_VALUE,
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.fillMaxWidth()) {
                innerTextField()
                if (viewModel.content.value == "") {
                    Text(text = stringResource(id = R.string.post_content), color = Color.Gray)
                }
            }
        }
    )
}

@Composable
fun PriceTextField(
    viewModel: PostViewModel,
    modifier: Modifier = Modifier,
    focusRequesterList: List<FocusRequester>
) {
    var checkState by rememberSaveable {
        mutableStateOf(false)
    }

    val style = TextStyle(
        color = if (checkState) MaterialTheme.colors.primary else Color.Black,
        fontWeight = if (checkState) FontWeight.Bold else FontWeight.Normal)

    LaunchedEffect(checkState) {
        when (checkState) {
            true -> {
                viewModel.setPrice("0")
            }
            false -> {
                viewModel.setPrice("")
            }
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BasicTextField(
            value = viewModel.price.value,
            onValueChange = {
                if (!checkState) {
                    if (it.matches(Regex("\\d*"))) {
                        viewModel.setPrice(it)
                    }
                }
            },
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .focusRequester(focusRequesterList[0]),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusRequesterList[1].requestFocus() }),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(modifier = Modifier.fillMaxWidth()) {
                    innerTextField()
                    if (viewModel.price.value == "") {
                        Text(text = stringResource(id = R.string.post_price), color = Color.Gray)
                    }
                }
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = checkState, onCheckedChange = { checkState = it })
            Text(text = "나눔", style = style)
        }

    }
}