package pdfops
import java.nio.file.Files
import java.nio.file.Path

fun moveFile(sourceFile: Path, targetFolder: Path): Path{
    Files.createDirectories(targetFolder)

    val targetFile = targetFolder.resolve(sourceFile.fileName)
    return Files.move(sourceFile, targetFile)
}