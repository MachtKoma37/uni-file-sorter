package pdfops

import model.ClassificationResult
import model.ModuleRule

fun classifyText(pdfText: String, rules: List<ModuleRule>): ClassificationResult {
    val lowerText = pdfText.lowercase()

    var bestRule: ModuleRule? = null
    var bestScore = 0

    for(rule in rules){
        var score = 0

        for(keyword in rule.keywords){
            if(lowerText.contains(keyword.lowercase()))
                score++
        }

        if(score > bestScore){
            bestRule = rule
            bestScore = score
        }
    }

    if(bestRule == null){
        return ClassificationResult(null, null, 0)
    }

    return ClassificationResult(bestRule.moduleName, bestRule.targetFolder, bestScore)
}