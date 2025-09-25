trivy image $1 \
    --severity $2 \
    --exit-code $3 \
    --quiet \
    --format json -o $4