package com.example.drillar.presentation.DrillListFeature.components

import androidx.compose.runtime.Composable
import com.example.drillar.domain.model.Drill

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.drillar.R

@Composable
fun DrillCard(
    drill: Drill,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable { 
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = drill.imageResId),
            contentDescription = drill.name,
            contentScale = ContentScale.Crop, // Makes sure the image fills the box nicely
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(drill.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(drill.description.take(60) + "...", fontSize = 14.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DrillCardPreview() {
    val sampleDrill = Drill(
        id = 1,
        name = "Wall Mount Drill",
        imageResId = R.drawable.wall_mount_drill,
        description = "Used for mounting shelves, TVs, and decor onto walls.",
        tips = listOf(
            "Check for hidden wires or pipes.",
            "Use a wall plug for secure mounting.",
            "Start with a pilot hole."
        )
    )

    DrillCard(drill = sampleDrill, onClick = {})
}