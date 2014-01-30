####About
This is a server-side implementation of Server Name Indication (SNI) in SSLServerSocket from OpenJDK 7.

**Variables:**
* `$JAVA_HOME` refers java home directory.
* `$PROJECT_HOME` refers this project root directory.

###Installation Steps:
1. Clone project from git.
2. Go to `$PROJECT_HOME` directory.
3. Type `mvn package` to compile project. It will generates `jsse.jar` file in `target` directory.
4. Backup `$JAVA_HOME/jre/lib/jsse.jar`.
5. Replace `$JAVA_HOME/jre/lib/jsse.jar` file with generated one. i.e `$PROJECT_HOME/target/jsse.jar`.


###Usage:

The following is a list of use cases that require understanding of the SNI extension for developing a server application:

#####Case 1. The server wants to accept all server name indication types.
If you do not have any code dealing with the SNI extension, then the server ignores all server name indication types.

Another way is to create an `AcceptableSNIMatcher` which always returns `true`:

 
```
SNIMatcher matcher = new AcceptableSNIMatcher();
Collection<SNIMatcher> matchers = new ArrayList<>(1);
matchers.add(matcher);
sslParameters.setSNIMatchers(matchers);
```

#####Case 2. The server wants to deny all server name indications of type host_name.<br>
Set an "invalid server name" pattern for host_name:

```
SNIMatcher matcher = SNIHostName.createSNIMatcher("");
Collection<SNIMatcher> matchers = new ArrayList<>(1);
matchers.add(matcher);
sslParameters.setSNIMatchers(matchers);
```
        
Another way is to create a `DenialSNIMatcher` which always returns `false`:
    
```
SNIMatcher matcher = new DenialSNIMatcher();
Collection<SNIMatcher> matchers = new ArrayList<>(1);
matchers.add(matcher);
sslParameters.setSNIMatchers(matchers);
```
        
#####Case 3. The server wants to accept connections to any host names in the example.com domain.<br>

Set the recognizable server name for host_name as a pattern that includes all *.example.com addresses:

```
SNIMatcher matcher = SNIHostName.createSNIMatcher("(.*\\.)*example\\.com");
Collection<SNIMatcher> matchers = new ArrayList<>(1);
matchers.add(matcher);
sslParameters.setSNIMatchers(matchers);
```

####Configuration for Clients which do not have SNISupport:

#####To Allow (or) Deny Clients:
Set system property `jsse.allowWithoutSNI` to `true` (or) `false`.

#####Default certificate Alias:
Set system property `jsse.defaultCertificateAlias`to `<certificate alias>`.<br>
<strong>Note:</strong> The given certificate alias will be served to the clients which do not have SNISupport.<br>
If you dont give any value (or) given `<certificate alias>` is invalid, then system will pick automatically any certificate based on some standard prefernces. 
