def call() {
    return """
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            app.kubernetes.io/name: node-agent
          name: node-agent
        spec:
          containers:
          - command:
            - sleep
            - "999999"
            image: 705454746869.dkr.ecr.ap-south-1.amazonaws.com/jenkins-test:node-agent
            name: node-container
            workingDir: "/home/jenkins/agent"
            resources:
              requests:
                memory: "2Gi"
                cpu: "1"
              limits:
                memory: "2Gi"
                cpu: "1"
            volumeMounts:
              - mountPath: "/opt/dependency-check/data/"
                name: dependency-check-nvd-data-cache-vol
                subPath: "dependency-check-nvd-data-cache"
          securityContext:
            fsGroup: 1000
          nodeSelector:
            karpenter.sh/nodepool: jenkins-test
          affinity:
            podAntiAffinity:
              requiredDuringSchedulingIgnoredDuringExecution:
                - labelSelector:
                    matchExpressions:
                      - key: app.kubernetes.io/name
                        operator: In
                        values:
                          - jenkins-test
                  topologyKey: topology.kubernetes.io/zone
          volumes:
            - name: dependency-check-nvd-data-cache-vol
              persistentVolumeClaim:
                claimName: jenkins-agent-vol-claim
          tolerations:
            - key: "jenkins-test"
              operator: "Equal"
              value: "true"
              effect: "NoSchedule"
          restartPolicy: Never
    """
}