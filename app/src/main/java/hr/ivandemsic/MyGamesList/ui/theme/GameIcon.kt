package hr.ivandemsic.MyGamesList.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun GameIcon(image: String,title: String,onClick:()->Unit) {

    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 15.dp, vertical = 5.dp)
        .clickable { onClick() }
        , shape = RoundedCornerShape(12.dp)) {
        Box (modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)){
            Image(painter = rememberAsyncImagePainter(model = image), contentDescription = null
                , modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        }


    }

}