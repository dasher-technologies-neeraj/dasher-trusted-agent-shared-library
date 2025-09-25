def vulnerabilityScan(String image, String highSevFileName, String criticalSevFileName) {
    sh(
        script: """
            trivy image ${image} \
                --severity LOW,MEDIUM,HIGH \
                --exit-code 0 \
                --quiet \
                --format json -o ${highSevFileName}
        """
    )

    sh(
        script: """
            trivy image ${image} \
                --severity CRITICAL \
                --exit-code 1 \
                --quiet \
                --format json -o ${criticalSevFileName}
        """
    )
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