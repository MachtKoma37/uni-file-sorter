package pdfops
import java.nio.file.Files
import java.nio.file.Path

fun moveFile(sourceFile: Path, targetFolder: Path): Path{
    Files.createDirectories(targetFolder)

    var targetFile = targetFolder.resolve(sourceFile.fileName)
    var counter = 1

    while(Files.exists(targetFile)){
        targetFile = targetFolder.resolve(
            sourceFile.fileName.toString().replace(".pdf", "($counter).pdf")
        )
        counter++
    }
    return Files.move(sourceFile, targetFile)
}