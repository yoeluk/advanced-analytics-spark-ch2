
### ADVANCED ANALYTICS WITH SPARK CH2 ###


### Before Running it ###

This trivial sample Spark app is taken from `Spark In Action` book

Download the data:

```
$ mkdir -p $HOME/sia/github-archive
$ cd $HOME/sia/github-archive
$ wget http://data.githubarchive.org/2015-03-01-{0..23}.json.gz
$ gunzip *
```

### Running it ###

```
$ sbt clean assembly
$ ~/bin/Spark/bin/spark-submit --class com.briefscala.Main --master spark://127.0.0.1:7077 ./target/scala-2.11/advanced-spark-ch2-assembly-0.1.0-SNAPSHOT.jar
```

It should out the stats for each of the features

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

The code in the book do not run as written and considerable debugging when parsing the data has lead this code. However the spark jobs do yield the exact same numbers so it likely that the data was curated when this examples were written.

