package model

import java.nio.file.Path

data class ClassificationResult(
    val moduleName: String?,
    val targetFolder: Path?,
    val score: Int,
)
