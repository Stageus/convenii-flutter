package com.example.convenii.view.detail

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.convenii.R
import com.example.convenii.model.APIResponse
import com.example.convenii.ui.theme.pretendard
import com.example.convenii.view.components.CommentUi
import com.example.convenii.view.components.ConfirmBtn
import com.example.convenii.viewModel.detail.DetailViewModel
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ProductDetailScreen(
    navController: NavController,
    productIdx: String?,
    viewModel: DetailViewModel
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = true) {
        viewModel.getProductDetailData(productIdx!!.toInt())
        viewModel.getProductReviewMain(productIdx.toInt())
    }
    val productDetailDataState = viewModel.productDetailDataState.collectAsState()
    val resultState = remember {
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<Boolean>("isUpdate")
    }
    val reviewData = viewModel.reviewData.collectAsState()
    val bookmarked = viewModel.isBookmark.collectAsState()

    LaunchedEffect(key1 = resultState) {
        if (resultState?.value == true) {
            Log.d("ProductDetailScreen", "isUpdate")
        }

    }

    if (productDetailDataState.value is APIResponse.Success) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                .fillMaxHeight()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = null,

                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                if (productDetailDataState.value is APIResponse.Success) {
                    if (productDetailDataState.value.data!!.rankIdx == 2) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9.dp))
                                .clickable(enabled = true, onClick = {})
                                .background(Color.White)
                                .height(30.dp)
                                .width(77.dp)
                                .border(
                                    BorderStroke(1.dp, Color(0xffE6E8EB)),
                                    shape = RoundedCornerShape(9.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "상품 삭제",
                                color = Color(0xff646F7C),
                                fontSize = 11.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(9.dp))
                                .clickable(enabled = true, onClick = {})
                                .background(Color.White)
                                .height(30.dp)
                                .width(77.dp)
                                .border(
                                    BorderStroke(1.dp, Color(0xffE6E8EB)),
                                    shape = RoundedCornerShape(9.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "상품 수정",
                                color = Color(0xff646F7C),
                                fontSize = 11.sp,
                                fontFamily = pretendard,
                                fontWeight = FontWeight.Medium,
                            )
                        }
                    }
                }

            }
            //메인 컬럼
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(top = 16.dp)
            ) {

                GlideImage(
                    imageModel = { productDetailDataState.value.data!!.data.product.productImg },
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .fillMaxWidth(0.41f)
                        .aspectRatio(1f)
                        .align(Alignment.CenterHorizontally),
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = productDetailDataState.value.data!!.data.product.name,
                        fontSize = 18.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    Icon( // 별점
                        painter = painterResource(id = R.drawable.icon_fill_star),
                        contentDescription = null,
                        tint = Color(0xffFFD643)
                    )
                    Text(
                        text = productDetailDataState.value.data!!.data.product.score,
                        fontSize = 16.sp,
                        fontFamily = pretendard,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 5.dp, end = 10.dp)
                    )
                }
                Text( //가격
                    text = "${productDetailDataState.value.data!!.data.product.price}원",
                    fontSize = 16.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(20.dp))

                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    BookmarkBtn(
                        bookmarked = bookmarked.value,
                        onClick = {
                            if (bookmarked.value) {
                                viewModel.deleteBookmark(productIdx!!.toInt())
                            } else {
                                viewModel.postBookmark(productIdx!!.toInt())
                            }
                        })
                }

                Spacer(modifier = Modifier.height(20.dp))


                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ) {
                    for (event in productDetailDataState.value.data!!.data.product.eventInfo[0].events) {
                        when (event.companyIdx) {
                            1 -> {
                                EventRow(
                                    icon = R.drawable.image_gs25,
                                    event = event.price ?: "행사없음"
                                )
                            }

                            2 -> {
                                EventRow(
                                    icon = R.drawable.image_cu,
                                    event = event.price ?: "행사없음"
                                )
                            }

                            3 -> {
                                EventRow(
                                    icon = R.drawable.image_emart24,
                                    event = event.price ?: "행사없음"
                                )
                            }

                        }

                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(
                    text = "할인 히스토리",
                    fontSize = 18.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Log.d(
                    "productDetailData",
                    productDetailDataState.value.data!!.data.product.eventInfo.toString()
                )
                val originalEventData =
                    productDetailDataState.value.data!!.data.product.eventInfo
                val tableData = viewModel.convertEventData(originalEventData)


                StaticDataTable(data = tableData)

                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "리뷰 모두보기",
                    fontSize = 12.sp,
                    fontFamily = pretendard,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xff7D8791),
                    modifier = Modifier.clickable {
                        navController.navigate("reviewDetail/${productIdx}")
                    }
                )

                Column {

                    reviewData.value.forEachIndexed { index, it ->
                        if (index > 0) Spacer(modifier = Modifier.height(10.dp))
                        CommentUi(
                            nickname = it.nickname,
                            star = it.score,
                            comment = it.content,
                            date = it.createdAt
                        )
                    }
                }

                if (productDetailDataState.value.data!!.rankIdx != 0) {
                    ConfirmBtn(
                        text = "리뷰 남기기",
                        enabled = true,
                        onClick = {
                            navController.navigate("reviewAdd")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 20.dp)
                    )
                }
            } // 메인 컬럼 끝
        }
        // success 끝
    } else {
        // 로딩
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
        )
    }
}

@Composable
fun EventRow(icon: Int, event: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        com.skydoves.landscapist.glide.GlideImage(
            imageModel = { icon },
            modifier = Modifier
                .width(25.dp)
                .height(25.dp),
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = event,
            modifier = Modifier.padding(start = 4.dp),
            fontSize = 12.sp,
            fontFamily = pretendard
        )
    }
}

@Composable
fun StaticDataTable(data: List<List<String>>) {
    Column(
        modifier = Modifier
    ) {
        data.forEach { rowData ->
            TableRow(rowData = rowData)
        }
    }
}

@Composable
fun TableRow(rowData: List<String>) {
    Row(
    ) {
        // 첫 번째 셀에 대한 처리
        TableCell(text = rowData[0], weight = 1.4f) // 첫 번째 열이 좁게 설정됩니다.

        // 나머지 셀들에 대한 처리
        rowData.drop(1).forEach { cellData ->
            TableCell(text = cellData, weight = 2f) // 나머지 열들은 더 넓게 설정됩니다.
        }
    }


}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .weight(weight)
            .height(40.dp)
            .border(1.dp, Color.Black)
            .padding(8.dp)
    ) {
        Text(
            text = text,
            fontSize = 12.sp,
            fontFamily = pretendard,
            textAlign = TextAlign.Center

        )
    }


}


@Composable
fun BookmarkBtn(bookmarked: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(enabled = true, onClick = {
                onClick()
            })
            .background(
                if (bookmarked) {
                    Color(0xff7D8791)
                } else {
                    Color(0xff2D8DF4)
                }
            )
            .height(30.dp)
            .width(77.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (bookmarked) {
                "북마크 취소"
            } else {
                "북마크"
            },
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
        )
    }

}