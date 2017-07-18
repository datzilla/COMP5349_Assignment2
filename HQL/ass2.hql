use ${hiveconf:db_name};
set mapred.reduce.tasks=11;

CREATE EXTERNAL TABLE IF NOT EXISTS places
  LIKE share.Place
  LOCATION '/share/data/place/';
  
CREATE EXTERNAL TABLE IF NOT EXISTS photos
  LIKE share.Photo
  LOCATION '/share/data/photo/';

CREATE EXTERNAL TABLE IF NOT EXISTS hiveOut(
key	STRING,
value	STRING
)
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
LINES TERMINATED BY '\n'
STORED AS TEXTFILE
LOCATION '/user/${hiveconf:db_name}/assignment2/hiveOut';

ADD FILE ./hiveReducer.pl;

FROM 
(
	SELECT photo.owner AS col1, photo.date_taken AS col2, reverse(trim(split(reverse(place.place_name),',')[0])) AS col3
	FROM Places Place JOIN Photos Photo ON photo.place_id = place.place_id
	DISTRIBUTE BY col1
	SORT BY col1 ASC, col2 ASC
) j1
INSERT OVERWRITE TABLE hiveOut
REDUCE j1.col1, j1.col2, j1.col3
USING 'perl hiveReducer.pl';

