# Gpsd Reader

GPSd playground...

### Run:
```
# helpful stuff to verify
gpsmon
cgps -s

# package, copy and exec
rsync -azP --stats ~/Dev/github/ohtello/gpsdr/ 192.168.0.100:/home/akaiser/rsync
java -jar rsync/gpsdr/target/gpsdr-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```
