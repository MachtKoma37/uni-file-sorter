package app

import config.downloadsDir
import pdfops.downloadsWatcher
import pdfops.sortExistingPdfs

fun main() {

    sortExistingPdfs(downloadsDir.toFile())
    downloadsWatcher(downloadsDir.toFile())
}