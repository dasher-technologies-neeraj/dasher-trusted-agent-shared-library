def vulnerabilityScan(Map config = [:]) {
    loadScript(name: "trivy.sh")
    sh "./trivy.sh ${config.imageName} ${config.severity} ${config.existCode} ${config.reportName}"
}

def convertJsonTrivyReportsToHtml(String sourceFileName, String destFileName) {
    sh """
        trivy convert \
            --format template \
            --template "@/contrib/html.tpl" \
            --output ${destFileName} \
            ${sourceFileName}
    """
}

def convertJsonTrivyReportsToXml(String sourceFileName, String destFileName) {
    sh """
        trivy convert \
            --format template \
            --template "@/contrib/junit.tpl" \
            --output ${destFileName} \
            ${sourceFileName}
    """
}