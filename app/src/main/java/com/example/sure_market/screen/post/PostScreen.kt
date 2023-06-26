package com.example.sure_market.screen.post

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sure_market.BuildConfig
import com.example.sure_market.R
import com.example.sure_market.screen.main.MainActivity
import com.example.sure_market.screen.map.MapActivity
import com.example.sure_market.view.*
import com.example.sure_market.viewmodel.PostViewModel
import kotlinx.coroutines.launch


@Composable
fun PostScreen(viewModel: PostViewModel, onMoveMain: () -> Unit, postComplete:() -> Unit, onMoveMapActivity: () -> Unit) {
    var dialogState by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    val focusManager = LocalFocusManager.current
    val focusRequester =
        remember { listOf(FocusRequester(), FocusRequester()) }

    val context = LocalContext.current

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uriList ->
            viewModel.addUri(uriList)
            val contentResolver = context.contentResolver
            Log.d("daeYoung", "방금 가져온 사진의 타입: ${contentResolver.getType(uriList[0])}")
        }



    if (dialogState) {
        CategoryDialog(onClick = { dialogState = false }, viewModel = viewModel)

    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "중고거래 글쓰기") },
                navigationIcon = {
                    IconButton(onClick = { onMoveMain() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = "back arrow",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { postComplete() }) {
                        Text(text = "완료", color = colorResource(id = R.color.orange))
                    }
                },
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = contentColorFor(MaterialTheme.colors.secondary)
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Divider(thickness = 5.dp)
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentHeight()
            ) {
                Box(modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable {
                        if (viewModel.uriList.size < 10) {
                            galleryLauncher.launch("image/*")
                        } else {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("개수를 초과하였습니다.")
                            }
                        }

                    }
                ) {
                    Canvas(modifier = Modifier.size(70.dp)) {
                        drawRoundRect(
                            color = Color.Gray,
                            size = size,
                            style = Stroke(width = 4.dp.toPx()),
                            cornerRadius = CornerRadius(
                                x = 10.dp.toPx(),
                                y = 10.dp.toPx(),
                            )
                        )
                    }
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                            contentDescription = "image upload",
                            modifier = Modifier.size(20.dp),
                            tint = Color.Gray,
                        )
                        Text(text = "${viewModel.getUriListCount()}/10")
                    }
                }
                LazyRow(
                    modifier = Modifier,
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(viewModel.uriList) { uri ->
                        PostImageWidget(uri, viewModel = viewModel)
                    }
                }
            }




            Divider(thickness = 1.dp)

            TitleTextField(viewModel = viewModel, focusRequester = focusRequester[0])
            Divider(thickness = 1.dp)

            CategoryButton(viewModel = viewModel, text = viewModel.category.value) { state -> dialogState = state }

            Divider(thickness = 1.dp)

            // 가격 설정
            PriceTextField(viewModel = viewModel, focusRequesterList = focusRequester)

            Divider(thickness = 1.dp)

            ContentTextField(
                viewModel = viewModel,
                focusRequester = focusRequester[1],
                focusManager = focusManager
            )

            Divider(thickness = 5.dp)

            MapButton(viewModel = viewModel, text = stringResource(id = R.string.post_place)) { onMoveMapActivity() }
        }
    }
}

