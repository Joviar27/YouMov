package com.cobasendiri.youmov.ui.screen.detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.cobasendiri.youmov.ui.theme.DarkGrey
import com.cobasendiri.youmov.ui.theme.DarkSurface

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

    LaunchedEffect(state.toastMessage) {
        Toast.makeText(context, state.toastMessage, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(movieId) {
        viewModel.refreshDetailPageInfo(movieId)
    }

    Scaffold { innerPadding ->
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
            error = false
        ) { event ->
            when(event){
                is DetailScreenEvent.OnNavigateBack ->{
                    onNavigateBack.invoke()
                }
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

    Box(Modifier.fillMaxSize()
        .background(DarkBackground),
        contentAlignment = Alignment.Center
    ){
        if(loading){
            LoadingIndicator(Modifier.size(48.dp))
        }
        if(error && !loading){
            RefreshButton(Modifier.size(48.dp)) {
                event.invoke(DetailScreenEvent.OnRefresh)
            }
        }
    }

    Column(Modifier.fillMaxSize()
        .background(DarkBackground)
        .padding(innerPadding)
    ) {
        Box(Modifier
            .fillMaxWidth()
            .height(200.dp)
        ){
            YouMovTopBar(
                titleText = title,
                backgroundColor = DarkSurface.copy(alpha = 0.3f),
                onNavigateBack = {
                    event.invoke(DetailScreenEvent.OnNavigateBack)
                }
            )
            AsyncImage(
                model = "${BuildConfig.IMAGE_BASE_URL}$imagePath",
                fallback = painterResource(R.drawable.placeholder_landscape),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Column(Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton({
                    event.invoke(DetailScreenEvent.OnFavoriteClick(""))
                }) {
                    Image(
                        painter = painterResource(
                            if(isFavorite) R.drawable.ic_favorite_filled_32
                            else R.drawable.ic_favorite_outline_32
                        ),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.width(4.dp))
                IconButton({
                    event.invoke(DetailScreenEvent.OnShareClick)
                }) {
                    Image(
                        painter = painterResource(R.drawable.ic_share_32),
                        contentDescription = null
                    )
                }
            }
            Text(
                text = releaseInfo,
                style = MaterialTheme.typography.labelLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.description),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.review),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))
            LazyColumn(Modifier.fillMaxWidth()) {
                items(reviewItems.count()){ index ->
                    reviewItems[index].let {
                        ReviewItem(
                            avatarUri = it.avatarUri,
                            reviewAuthor = it.author,
                            reviewRating = it.rating,
                            reviewDate = it.date,
                            reviewContent = it.content
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row(Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(reviewSectionCount){ index ->
                    ReviewSection(
                        number = index,
                        isActive = index == reviewSection
                    ){
                        event.invoke(DetailScreenEvent.OnReviewSectionClick(it))
                    }
                }
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
    Box(Modifier
        .background(if (isActive) DarkGrey else DarkSurface)
        .clickable { onClick.invoke(number) }
    ){
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "$number",
            style = MaterialTheme.typography.titleLarge
        )
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
        loading = false
    ) {}
}