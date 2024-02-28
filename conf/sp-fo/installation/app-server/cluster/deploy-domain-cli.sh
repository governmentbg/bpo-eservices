# ./jboss-cli.sh --file=deploy-domain-cli.sh
connect @node.ip@
deploy ../standalone/deployments/jackrabbit-jca.rar --all-server-groups
deploy ../standalone/deployments/jackrabbitexplorer.war --all-server-groups
deploy ../standalone/deployments/jackrabbit-webapp-2.6.2.war --all-server-groups
deploy ../standalone/deployments/sp-core-configuration.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-rules.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-resources.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-application.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-document.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-epayment.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-esignature.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-fee-calculation.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-ipapplication.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-locarno-classification.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-nice-classification.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-person-search.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-report.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-trademark-search.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-core-design-search.ear --server-groups=sp-server-group
deploy ../standalone/deployments/sp-ui-tmefiling.war --server-groups=sp-server-group
deploy ../standalone/deployments/sp-ui-dsefiling.war --server-groups=sp-server-group
deploy ../standalone/deployments/sp-ui-eservices.war --server-groups=sp-server-group