package com.kroy.kmp_project.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.kroy.kmp_project.data.model.Article
import com.kroy.kmp_project.data.model.Source
import com.kroy.kmp_project.theme.imageSize
import com.kroy.kmp_project.theme.mediumPadding
import kmp_project.composeapp.generated.resources.Res
import kmp_project.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun ArticleItem(
     article: Article,
     onClick:() -> Unit
){
    Row(
        modifier = Modifier.clickable{
            onClick()
        },
        horizontalArrangement = Arrangement.spacedBy(mediumPadding)
    ){
        AsyncImage(
                modifier = Modifier

                    .size(imageSize)
                    .clip(MaterialTheme.shapes.large)
                    .background(Color.Gray),
            model = article.urlToImage,
            error = painterResource(Res.drawable.compose_multiplatform),
            placeholder = painterResource(Res.drawable.compose_multiplatform),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )
        Column (
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                maxLines = 2,


            )
            article.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2

                )
            }
            Text(
                text = article.source.name,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                maxLines = 2

            )
        }
    }

}

@Preview
@Composable
fun ArticleItemPreview(){
    ArticleItem(
        article = Article(
            author = "Jane Doe",
            content = "Kotlin Multiplatform lets you share code across Android, iOS, desktop, and web from a single codebase.",
            description = "A deep dive into KMP and how it simplifies cross-platform development.",
            publishedAt = "2026-06-14T10:00:00Z",
            source = Source(id = "tech-crunch", name = "TechCrunch"),
            title = "Kotlin Multiplatform is the Future of Cross-Platform Apps",
            url = "https://example.com/kmp-article",
            urlToImage = "https://placehold.co/600x400"
        ),
        onClick = {}
    )
}




