package com.kroy.kmp_project.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun NewsBottomnaviBar(
    bottomNavigationitemList: List<BottomNavigationitem>,
    currentRoute:String?,
    onItemClick: (BottomNavigationitem) -> Unit
){
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {bottomNavigationitemList.forEach { bottomNavigationitemList->

         NavigationBarItem(
             selected = currentRoute == bottomNavigationitemList.route,
             onClick = { onItemClick(bottomNavigationitemList) },
         {
             Icon(
                 painter = painterResource(bottomNavigationitemList.icon),
                 contentDescription = stringResource(bottomNavigationitemList.title))
         },
             label = {
                 Text(text = stringResource(bottomNavigationitemList.title),
                     style = MaterialTheme.typography.labelMedium,
                     fontWeight = FontWeight.Medium,
                 )
             }
         )

    }
}
}