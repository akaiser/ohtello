# Gyro Reader

HMC5883L playground...

### Good resources:
https://github.com/MikeHouser/WookieBot
https://github.com/tijos-sensor-library/HMC5883

### Setup:
```
# enable i2c
## check with
lsmod | grep i2c

## if there is no i2c-dev but just older i2c_bcm2708 or newer i2c_bcm2835 or none of them...
## add to /etc/modules
i2c-dev

## fix permission
sudo chmod 666 /dev/i2c-0

## verify
sudo i2cdetect -y 0
```

### Run:
```
# prereq
sudo apt install wiringpi

# package, copy and exec
rsync -azP --stats ~/Dev/github/ohtello/gyror/ 192.168.0.100:/home/akaiser/rsync
java -jar rsync/gyror/target/gyror-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```
