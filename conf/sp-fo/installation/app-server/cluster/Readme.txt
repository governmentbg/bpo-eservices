1. Connectivity between domain controller and slave's host controllers:

 - It's key in the domain controller, add a management user with the script "add-user.sh",
   and add the "<secret value="secretKeyReturnedFromCommand" />" resulting to the slave's
   host.xml under the tag "<server-identities>".

   But it has to be done with the username as the name specified in the slave host.xml, in
   this case username has to be "slave1.example.com" (see snippet of the slaves host.xml):

[snippet of the slave1 host.xml]
   <host xmlns="urn:jboss:domain:4.0" name="slave1.example.com">
                                            +----------------+
                                                     +
                                                     |
                                                     V
                                                 Use this as
                                                 username in the
                                                 add-user.sh script
                                                 on the master node
       <extensions>
           <extension module="org.jboss.as.jmx"/>
       </extensions>
       ...
       ...
       ...
[/snippet]

 - otherwise, the slave couldn't connect to the domain controller.