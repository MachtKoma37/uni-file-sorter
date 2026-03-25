package model

import java.nio.file.Path

data class ModuleRule(
    val moduleName: String,
    val targetFolder : Path,
    val keywords: List<String>
)
