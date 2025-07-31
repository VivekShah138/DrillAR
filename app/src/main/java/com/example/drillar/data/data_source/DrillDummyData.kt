package com.example.drillar.data.data_source

import com.example.drillar.domain.model.Drill
import com.example.drillar.R

object DummyDrillData{
    val dummyDrillList = listOf(
        Drill(
            id = 1,
            name = "Wall Mount Drill",
            imageResId = R.drawable.wall_mount_drill,
            description = "Used for mounting shelves, TVs, and decor onto walls.",
            tips = listOf(
                "Check for hidden wires or pipes.",
                "Use a wall plug for secure mounting.",
                "Start with a pilot hole."
            )
        ),
        Drill(
            id = 2,
            name = "Woodworking Drill",
            imageResId = R.drawable.wood_working_drill,
            description = "Ideal for furniture making and carpentry.",
            tips = listOf(
                "Clamp the wood.",
                "Use a sharp bit.",
                "Mark drill points beforehand."
            )
        ),
        Drill(
            id = 3,
            name = "Metal Drill",
            imageResId = R.drawable.metal_drill,
            description = "Used for boring through metal surfaces like steel and aluminum.",
            tips = listOf(
                "Use low speed.",
                "Apply cutting oil.",
                "Wear eye protection."
            )
        ),
        Drill(
            id = 4,
            name = "Precision Mini Drill",
            imageResId = R.drawable.mini_drill,
            description = "For small crafts, jewelry, and electronic repairs.",
            tips = listOf(
                "Keep your hand steady.",
                "Use it for light tasks only.",
                "Avoid too much pressure."
            )
        ),
        Drill(
            id = 5,
            name = "Hammer Drill",
            imageResId = R.drawable.hammer_drill,
            description = "Great for drilling into concrete and brick.",
            tips = listOf(
                "Use ear protection.",
                "Hold it firmly.",
                "Let the hammer do the work."
            )
        )
    )

}
