
## SBT TEMPLATE PROJECT WITH SPARK ##


### Running it ###

This trivial sample Spark app is taken from `Spark In Action` book

If you want to run the app execute the following commands

```
$ mkdir -p $HOME/sia/github-archive
$ cd $HOME/sia/github-archive
$ wget http://data.githubarchive.org/2015-03-01-{0..23}.json.gz
$ gunzip *
```

### Disclaimer ###

The code in the book do not run as written and considerable debugging when parsing the data has lead this code. However the spark jobs do yield the exact same numbers so it likely that the data was cureated when this examples were written.