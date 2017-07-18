#!/usr/bin/perl                                                                                       

use Date::Parse;

undef $currentKey;
$owner ="";
$timestamp = "";
$country = "";
$prevOwner = "";
$prevTimeStamp = "";
$prevCountry = "";

%visits=();
#country: visit instance, maxtime, minTime, avgTime, totalTime, timeStamp, flag;
#eg: 1,30.8, 20.1, 25.05, 50.0, hh:mm..., 2
# need a function to update the fields in each array record
# 

while (<STDIN>) {
  chomp();
  processRow(split(/\t/));
}

processLastOwner();

sub processRow() {
  my($owner, $time, $country) = @_;

  #first row of the entire table
  if (! defined($currentKey)) {
    $currentKey = $owner;
	$visits{ $country } = [0,0.0,0.0,0.0,0.0,$time,1];
	$prevCountry = $country;
	$prevTime = $time;
    return;
  }

  #there is a change of owner
  if ($currentKey ne $owner) {
    processLastOwner(); #print out the previous value first
	%visits=(); #reset visits hash
	
    $currentKey = $owner; #update key    
	$visits{ $country } = [0,0.0,0.0,0.0,0.0,$time,1];#add current record as first record in hash
	$prevCountry = $country;
	$prevTime = $time;
	return;
  }
  else {
	#if the current country hasn't been seen
	if ( not exists $visits{ $country } ) {
		$visits{ $country } = [0,0.0,0.0,0.0,0.0,$time,1];
	} 
	
	#if there is a change in country
	if( (lc $prevCountry ne lc $country) ){
		incrementInstance($prevCountry); #increment prev country instance by 1
		setTimeStamp($country,$time); #set current country timestamp to to current timestamp
		setFlag($country,1); #set current country flag to 1
		$timediff = 0;
		
		#if previous country flag is 1
		if( $visits{$prevCountry}[6] eq 1 ) {
			if( $time ne getTimeStamp($prevCountry) ) {
				$timediff = timeDiff($time,getTimeStamp($prevCountry));
			}
		}
		
		#if there's an instance but duration is 0, means they must have stayed for 0.01 day.
		if( $timediff == 0.000) { $timediff=0.1;}
		#add the duration to the total time spent in the prev country
		addDuration($prevCountry,$timediff);
		#set the max time spent in the prev country, function checks if it's valid
		setMaxTime($prevCountry,$timediff);
		#set the min time spent in the prev country, function checks if it's valid
		setMinTime($prevCountry,$timediff);
		#set the flag of the prev country to 2
		setFlag($prevCountry,2);
		#set the average time spent in the previous country
		setAvgTime($prevCountry);
		
	}

	$prevCountry = $country;
	$prevTime = $time;	
	return;
  } 
  
}

sub processLastOwner() {
	incrementInstance($prevCountry);
	
	if( $time ne getTimeStamp($prevCountry) ) {
		$timediff = timeDiff($prevTime,getTimeStamp($prevCountry));
	}
	if( $timediff == 0.000) { $timediff=0.1;}
	addDuration($prevCountry,$timediff);
	setMaxTime($prevCountry,$timediff);
	setMinTime($prevCountry,$timediff);
	setAvgTime($prevCountry);
	outputRecord($currentKey);
}

sub outputRecord() {
	my($thisOwner)=@_;
	
	$out = "$thisOwner\t";
	foreach $countryKey (keys %visits) {
		$out .= "$countryKey (";
		for ($i = 0; $i<5; $i++) { $out.= $visits{$countryKey}[$i].",";}
		chop($out);
		$out .= "), ";
	}
	chop($out);
	chop($out);
	print $out."\n";
}

sub timeDiff() {
	my( $leftDate, $rightDate) = @_;
	my($time1) = str2time($leftDate);
	my($time2) = str2time($rightDate);

	return sprintf "%.1f", (($time1 - $time2)/(60*60*24));
}

sub addDuration() {
	my( $inCountry, $inDuration)=@_;
	$visits{ $inCountry }[4] += $inDuration;
}

sub setFlag() {
	my( $inCountry, $inFlag)=@_;
	$visits{ $inCountry }[6] = $inFlag;
}

sub incrementInstance(){
	my( $inCountry)=@_;
	$visits{ $inCountry }[0] += 1;
}

sub setMaxTime () {
	my( $inCountry, $inTime)=@_;
	
	if( $visits{ $inCountry }[1]==0.0 or $visits{ $inCountry }[1]<$inTime) {
		$visits{ $inCountry }[1]=$inTime;
	}
}

sub setMinTime () {
	my( $inCountry, $inTime)=@_;
	
	if( $visits{ $inCountry }[2]==0.0 or $visits{ $inCountry }[2]>$inTime) {
		$visits{ $inCountry }[2]=$inTime;
	}
}

sub setTimeStamp () {
	my( $inCountry, $inTime)=@_;
	$visits{ $inCountry }[5] = $inTime;
}

sub getTimeStamp(){
	my( $inCountry)=@_;
	return $visits{ $inCountry }[5];
}

sub setAvgTime() {
	my( $inCountry)=@_;
	my $totalTime = $visits{ $inCountry }[4];
	my $instances = $visits{ $inCountry }[0];
	
	my $avg = 0;
	if ($instances != 0) { $avg = $totalTime / $instances; }
	else { $avg=0.0; }
	$avg = sprintf ("%.1f", ($avg));
	$visits{ $inCountry }[3] = $avg;
}