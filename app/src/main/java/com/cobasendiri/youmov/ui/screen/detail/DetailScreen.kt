package com.cobasendiri.youmov.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cobasendiri.youmov.BuildConfig
import com.cobasendiri.youmov.R
import com.cobasendiri.youmov.domain.model.ReviewItem
import com.cobasendiri.youmov.ui.ViewModelFactory
import com.cobasendiri.youmov.ui.component.LoadingIndicator
import com.cobasendiri.youmov.ui.component.RefreshButton
import com.cobasendiri.youmov.ui.component.ReviewItem
import com.cobasendiri.youmov.ui.component.YouMovTopBar
import com.cobasendiri.youmov.ui.theme.DarkBackground
import com.cobasendiri.youmov.ui.theme.DarkSurface
import com.cobasendiri.youmov.ui.theme.Grey
import com.cobasendiri.youmov.ui.theme.TextPrimary
import com.cobasendiri.youmov.ui.theme.YouMovTheme
import com.cobasendiri.youmov.ui.util.showToast
import com.cobasendiri.youmov.ui.util.toReadableDate
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    movieId: Int,
    onNavigateBack: () -> Unit
) {

    val context = LocalContext.current
    val appContext = context.applicationContext

    val viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(appContext)
    )

    val state by viewModel.state.collectAsStateWithLifecycle()

    val message = state.toastMessage
    LaunchedEffect(message) {
        if(message.isNotEmpty()){
            context.showToast(message)
            viewModel.consumeToast()
        }
    }

    LaunchedEffect(movieId) {
        viewModel.refreshDetailPageInfo(movieId)
    }

    Scaffold(
        topBar = {
            YouMovTopBar(
                titleText = stringResource(R.string.detail),
                backgroundColor = DarkSurface,
                onNavigateBack = {
                    onNavigateBack.invoke()
                }
            )
        }
    ) { innerPadding ->
        DetailScreenContent(
            innerPadding = innerPadding,
            imagePath = state.imagePath,
            title = state.title,
            isFavorite = state.isFavorite,
            releaseInfo = state.releaseInfo,
            description = state.description,
            reviewItems = state.reviewItems,
            reviewSection = state.reviewSection,
            reviewSectionCount = state.reviewSectionCount,
            loading = state.loading,
            error = state.error
        ) { event ->
            when(event){
                is DetailScreenEvent.OnFavoriteClick ->{
                    viewModel.updateFavorite()
                }
                is DetailScreenEvent.OnShareClick ->{

                }
                is DetailScreenEvent.OnReviewSectionClick ->{
                    viewModel.getMovieReviews(movieId, event.number)
                }
                is DetailScreenEvent.OnRefresh ->{
                    viewModel.refreshDetailPageInfo(movieId)
                }
            }
        }
    }
}

@Composable
fun DetailScreenContent(
    innerPadding: PaddingValues,
    imagePath: String,
    title: String,
    isFavorite: Boolean,
    releaseInfo: String,
    description: String,
    reviewItems: List<ReviewItem>,
    reviewSection: Int,
    reviewSectionCount: Int,
    loading: Boolean,
    error: Boolean,
    event: (DetailScreenEvent) -> Unit
){
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(Modifier.fillMaxSize()
        .background(DarkBackground)
        .padding(innerPadding),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            AsyncImage(
                modifier = Modifier.fillMaxWidth().height(250.dp),
                model = "${BuildConfig.IMAGE_BASE_URL}$imagePath",
                placeholder = painterResource(R.drawable.placeholder_landscape),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        item {
            Row(Modifier.padding(horizontal = 16.dp)
                .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.width(8.dp))
                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = {
                        event.invoke(DetailScreenEvent.OnFavoriteClick(""))
                    }
                ) {
                    Image(
                        painter = painterResource(
                            if(isFavorite) R.drawable.ic_favorite_filled_32
                            else R.drawable.ic_favorite_outline_32
                        ),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.width(16.dp))
                IconButton(
                    modifier = Modifier.size(28.dp),
                    onClick = {
                        event.invoke(DetailScreenEvent.OnShareClick)
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_share_32),
                        contentDescription = null
                    )
                }
            }
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = releaseInfo.toReadableDate(),
                style = MaterialTheme.typography.labelLarge
            )
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        item {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.review),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
        }
        items(reviewItems.count()){ index ->
            reviewItems[index].let {
                ReviewItem(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    avatarPath = it.avatarUri,
                    reviewAuthor = it.author,
                    reviewRating = it.rating,
                    reviewDate = it.date,
                    reviewContent = it.content
                )
            }
        }
        item {
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(reviewSectionCount){ index ->
                    val numberDisplay = index + 1
                    ReviewSection(
                        number = numberDisplay,
                        isActive = numberDisplay == reviewSection
                    ){
                        coroutineScope.launch {
                            listState.animateScrollToItem(3)
                        }
                        event.invoke(DetailScreenEvent.OnReviewSectionClick(it))
                    }
                }
            }
        }
    }

    if(!loading && !error) return
    Box(Modifier.fillMaxSize()
        .background(DarkBackground),
        contentAlignment = Alignment.Center
    ){
        if(loading){
            LoadingIndicator(Modifier.size(48.dp))
        }
        if(error){
            RefreshButton(Modifier.size(48.dp)) {
                event.invoke(DetailScreenEvent.OnRefresh)
            }
        }
    }
}

@Composable
fun ReviewSection(
    number: Int,
    isActive: Boolean,
    onClick: (Int) -> Unit
){
    Box(Modifier.size(36.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(if (isActive) Grey else DarkSurface)
        .clickable { onClick.invoke(number) }
    ){
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "$number",
            style = MaterialTheme.typography.titleSmall.copy(
                color = if(isActive) DarkBackground else TextPrimary
            )
        )
    }
}

@Preview
@Composable
fun ReviewSectionPrev(){
    YouMovTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(5){ index ->
                ReviewSection(
                    number = index + 1,
                    isActive = (index+1) == 2
                ){}
            }
        }
    }
}

@Preview
@Composable
fun DetailScreenContentPrev() {
    val reviewItems = List(5){
        ReviewItem(
            avatarUri = "",
            author = "Author Name",
            rating = "6.5",
            date = "21 Desember 2026",
            content = "Long text review of a moview, the author state" +
                    "their opinion in this long text, but apparently it's not" +
                    "enough to convince people otherwise, maybe we all just a spec" +
                    "of dust in this huge universe"
        )
    }
    YouMovTheme {
        DetailScreenContent(
            innerPadding = PaddingValues(0.dp),
            imagePath = "",
            title = "Movie Name",
            isFavorite = false,
            releaseInfo = "19 Oktober 2024",
            description = "The overview of the movie displayed in this item apparently can get quite long yeah it can get",
            reviewItems = reviewItems,
            reviewSection = 1,
            reviewSectionCount = 3,
            error = false,
            loading = true
        ) {}
    }
}