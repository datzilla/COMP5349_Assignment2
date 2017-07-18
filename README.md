Introduction
This second assignment is based on the same data set as Assignment 1. Your task is to
perform a time series data analysis as specified under ’Problem Description’ below, but
with two different implementations. You are also required to compare the performance of
the two implementations, and to write a short performance report.


Problem Description
You are asked to find out how many times a user has visited a particular country, as well
as the maximum, minimum, average, and total time he/she spent in this country.
In the assignment data set, a user is considered of having visited a place if he/she has
taken at least one photo in that place. A user may have stayed in many different places in a
country; as long as the user did not visit another country in between, these are considered
as one visit of that country. A visit of a country may last for several days, weeks or even
years.
The following sample data shows a particular user’s photo records, sorted in chronological
order. Each line represents a photo record. For simplicity, we only show user id as
well as the time and place the photo is taken. From the sample data, we can tell that the
user 10530649@N05 has visited four countries in total. He/she has visited Australia twice
and all other countries once.
10530649@N05 2007-04-19 12:31:34 /Australia/QLD/Bundaberg/Kensington
10530649@N05 2007-05-23 08:08:27 /Australia/QLD/Cairns/Brinsmead
10530649@N05 2008-03-24 09:04:52 /Brazil/Maranhao/Primeira+Cruz
10530649@N05 2008-04-02 11:23:03 /Brazil/Espirito+Santo/Anchieta
10530649@N05 2008-04-10 16:33:25 /Brazil/Espirito+Santo/Guarapari
10530649@N05 2008-04-20 15:17:43 /United+Kingdom/Scotland/Spean+Bridge
10530649@N05 2008-05-06 13:24:03 /United+Kingdom/Scotland/Eshaness
10530649@N05 2009-10-23 11:00:16 /United+States/California/Cayucos
10530649@N05 2009-10-25 08:21:44 /United+States/California/Mill+Valley
10530649@N05 2009-12-10 20:53:56 /Australia/SA/Adelaide/Eden+Hills
10530649@N05 2009-12-14 10:11:31 /Australia/SA/Whyalla/Whyalla+Norrie
