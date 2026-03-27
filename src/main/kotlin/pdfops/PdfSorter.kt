package pdfops

import config.moduleRules
import config.unsortedFolder
import model.ClassificationResult
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import java.nio.file.StandardWatchEventKinds.OVERFLOW
import kotlin.io.path.extension
import kotlin.io.path.isDirectory

fun downloadsWatcher(downloadsDir: File){
    require(downloadsDir.exists() && config.downloadsDir.isDirectory()){
        "downloadsDir is not valid directory"
    }
    val watcher = FileSystems.getDefault().newWatchService()
    val downloadsPath = downloadsDir.toPath()

    downloadsPath.register(watcher, ENTRY_CREATE)

    while(true){
        val key = watcher.take()
        val events = key.pollEvents()

        for(event in events){
            if(event.kind() == OVERFLOW) continue
            if(event.kind() == ENTRY_CREATE){
                val context = event.context() as Path
                val fileDir = downloadsPath.resolve(context)
                if(fileDir.extension.lowercase() == "pdf") {
                    if(waitUntilFileIsStable(fileDir.toFile())) {
                        sortPdf(fileDir.toFile())
                    }
                }
            }
        }
        if(!key.reset()) break
    }
}

fun sortPdf(pdf : File) {
    try {
        val pdfText = extractText(pdf.toPath())
        val pdfClassifyingResult = classifyText(pdfText, moduleRules)
        val targetFolder = pdfClassifyingResult.targetFolder

        if (targetFolder == null) {
            moveFile(pdf.toPath(), unsortedFolder)
        } else {
            moveFile(pdf.toPath(), targetFolder)
        }
        printResult(pdfClassifyingResult)

    }catch(exception : Exception){
        println("unreadable PDF: ${pdf.name}")
        println("Reason: $exception")
    }
}
fun sortExistingPdfs(downloadsDir : File){
    require(downloadsDir.exists() && config.downloadsDir.isDirectory()){
        "downloadsDir is not valid directory"
    }

    val allFiles = downloadsDir.listFiles()
    if(allFiles == null) return

    val onlyPdfs = allFiles.filter {it.isFile && it.extension.lowercase() == "pdf" }

    for(pdf in onlyPdfs){
        sortPdf(pdf)
    }
}

fun printResult(pdf : ClassificationResult){
    if(pdf.targetFolder != null){
        println("Moved to: ${pdf.targetFolder}")
    }else println("Moved to: Unsorted")
    println("Module: ${pdf.moduleName}")
    println("Score: ${pdf.score}")
}

fun waitUntilFileIsStable(file: File): Boolean{
    if(!file.exists() || !file.isFile) return false

    var size = file.length()

    while(true){
        Thread.sleep(500)
        val newSize = file.length()

        if(size == newSize){
            return true
        }

        size = newSize
    }
}