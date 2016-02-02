
### ADVANCED ANALYTICS WITH SPARK CH2 ###

[![Join the chat at https://gitter.im/yoeluk/advanced-analytics-spark-ch2](https://badges.gitter.im/yoeluk/advanced-analytics-spark-ch2.svg)](https://gitter.im/yoeluk/advanced-analytics-spark-ch2?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)


### Before Running it ###

This sample Spark app is taken from `Spark In Action` book

Download the data:

```
$ mkdir -p $HOME/linkage
$ curl -o donation.zip https://archive.ics.uci.edu/ml/machine-learning-databases/00210/donation.zip
$ unzip donation.zip
$ unzip 'block_*.zip'
```

### Running it ###

```
$ sbt clean assembly
$ ~/bin/Spark/bin/spark-submit --class com.briefscala.Main --master spark://127.0.0.1:7077 ./target/scala-2.11/advanced-spark-ch2-assembly-0.1.0-SNAPSHOT.jar
```

It should output the stats for each of the features

```
((1007,0.2854529057466859),0)                                                   
((5645434,0.09104268062279897),1)
((0,0.6838772482597568),2)
((5746668,0.8064147192926266),3)
((0,0.03240818525033462),4)
((795,0.7754423117834042),5)
((795,0.5109496938298719),6)
((795,0.7762059675300523),7)
```

### Disclaimer ###

The code in this chapter do not run as written and considerable debugging when parsing the data has lead this code. However, the spark jobs do yield the exact same result so it likely that the data was curated when this book code was written.

