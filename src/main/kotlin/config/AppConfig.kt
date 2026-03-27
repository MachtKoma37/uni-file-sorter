package config

import model.ModuleRule
import kotlin.io.path.Path

val downloadsDir = Path("YOUR DOWNLOADS PATH")
val unsortedFolder = Path("YOUR UNSORTED PATH")

val moduleRules = listOf(
    ModuleRule(
        "moduleName1",
        Path("YOUR MODULE FOLDER PATH 1"),
        listOf("keyword1", "keyword2")
    ),
    ModuleRule(
        "moduleName2",
        Path("YOUR MODULE FOLDER PATH 2"),
        listOf("keyword3", "keyword4")
    )
)