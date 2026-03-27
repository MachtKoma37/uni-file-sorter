package pdfops

import org.apache.pdfbox.Loader
import org.apache.pdfbox.text.PDFTextStripper
import java.nio.file.Path

fun extractText(path: Path): String {
    val pdf = Loader.loadPDF(path.toFile())
    val stripper = PDFTextStripper()
    val text = stripper.getText(pdf)

    pdf.close()
    return text
}
