#--------------------------------------------------------------------------------#
#                         	 SP Consolidated Front Office    		             #
#--------------------------------------------------------------------------------#

#--------------------------------------------------------------------------------#
#								JMETER TO BE USED								 #
#--------------------------------------------------------------------------------#

Download from SVN:
https://svn.oami.europa.eu/viewvc/emrepo/SOPRA/Performance/00-DELIVERIES/JMeter/


#--------------------------------------------------------------------------------#
#							FOLDER OF THE PROJECT								 #
#--------------------------------------------------------------------------------#

#		STEP 1
------------------------------
Download the project 'SP Consolidated Front Office' from
https://svn.oami.europa.eu/viewvc/emrepo/SOPRA/Performance/00-DELIVERIES/

#		STEP 2
------------------------------
Find a local storage for the project recently downloaded
Example:
  D:\dev\

This is how the project will appear:
  D:\dev\SP Consolidated Front Office\Performance Testing 
									 \Performance Testing \ Test Execution
									 \Performance Testing \ Test Execution \ Input Data
									 \Performance Testing \ Test Execution \ Input Data \ test
									 \Performance Testing \ Test Execution \ Input Data \ inte
									 \Performance Testing \ Test Execution \ Input Data \ preprod
									 \Performance Testing \ Test Execution \ Scripts


#		STEP 3
------------------------------					  
Open the 'servers.csv' file, regarding application servers, in the following folder:
.... \Performance Testing \ Test Execution \ Input Data 

Follow the nomenclature therein to include the servers for the environment.
server1_[environment],[name of the server 1]

For instance: if your environment is [test] or any other, add your servers in the following lines:

server1_test,[name of the server 1]
server2_test,[name of the server 2]
..
..
server1_preprod,[name of the server 1]
server2_preprod,[name of the server 2]

server1_inte,[name of the server 1]
server2_inte,[name of the server 2]
...
...

In case more servers are needed, please contact QC.

#--------------------------------------------------------------------------------#
#						NAME AND LOCATION OF CSV FILES							 #
#--------------------------------------------------------------------------------#

The name of CSV files will be the following:

.... \Performance Testing \ Test Execution \ Input Data \ [environment] \ [environment]_[Profile]-[name].csv


#			SCRIPT
------------------------------

The script to be opened in Jmeter can be found in the following folder:
.... \Performance Testing \ Test Execution \ Scripts

Once located, open it in Jmeter

#--------------------------------------------------------------------------------#			  
#						VARIABLES TO BE SET UP ON SCRIPT						 #
#--------------------------------------------------------------------------------#

#			Over the 'Test Plan' section
------------------------------------------------------------

   Name                     	value
environment				[test] or [preprod] or [inte]
server_[environment]	[server on inte environment for DSView]
Application      		Maximize HDB Usage
version					[version of your app in your environment]
ipDEV              	 	[XXX.XXX] (the two first numbers of your local IP)
folder_DEV				[Folder used in STEP 2, under '#Folder of the project]

# Over the 'User Defined Variables for Maximize HDB' section
------------------------------------------------------------

typetest					[name of the folder where the results will be stored]
firstLanguage				first language of the application to be file
secondLanguage				second language of the application to be file
representativeId			RepresentativeID used for the applicacion forms
applicantId					ApplicantID used for the applicacion forms
imgName						Name of the image when uploading
representativeIdeServices	RepresentativeId for Services
no_of_designs				Number of Designs in Profile 3
no_of_views					Number of Views per Design in Profile 3
image_prefix				prefix of the image
image_suffix				suffix of the image
locarnoClass				number of locarno used


(Data delivered for this variables in the script has been tested)


#--------------------------------------------------------------------------------#
#						PREVIOUS EXECUTION										 #
#--------------------------------------------------------------------------------#

N/A.

#--------------------------------------------------------------------------------#
#						DATA PREPARATION										 #
#--------------------------------------------------------------------------------#
Data set for [preprod] and [inte] environment is the same than [test] environment. Please amend data to your environment.

Create/update the data set for profiles in the [environment] folder under 'Performance Testing\Test Execution\Input Data\[environment]' following the same patern than in used environment

In order to watch the documents downloaded the following folder must be created:
D:\[path in step 2]\SP Consolidated Front Office\Performance Testing\Test Results\[version]\receipts

(Data delivered in the script has been tested for 'test' environment)
	
#--------------------------------------------------------------------------------#
#						EXECUTION OF THE TEST									 #
#--------------------------------------------------------------------------------#

Under 'TYPE OF TEST' section, active ONLY ONE of the following 'Config Element' depending on the test to be performed
   - LOAD TEST
   - STRESS TEST
   - ROB.REC.TEST
   - SCALABILITY TEST

Volumetric must be aligned with the volumes described in PTA for the test to be executed, please check.
   
  
#			Listeners DISABLED for the test
------------------------------------------------------------
 - View Results Tree
 - View Results Tree - errors - noLogs
 - Response Times Distribution (unless it is needed)

 
#			Listeners ENABLED for the test and leave a file with data:
------------------------------------------------------------
 - Aggregate Report
 - Summary Report - Successes
 - Summary Report - Errors
 - Summary Report Succ - Error
 - View Results Tree - errors*
 - PerfMon Metrics Collector - CPU**
 - PerfMon Metrics Collector - Memory**
 - PerfMon Metrics Collector - Network**
 - PerfMon Metrics Collector - Swap**
 - Simple Data Writer*
 - Response Times Over Time
 - Response Times Percentiles
 - Response Codes per Second
 - Transactions per Second
 - Active Threads Over Time
 
 Other request to be actived during the test:
 - ->actual_time
 - ->Debug PostProcessor
 -  ?Something went wrong
  
 *The folder that contain data is located in:
.... \ Performance Testing \ Test Results \ [version] \ [typetest]

 ** If you want to monitor servers using the JMeter PerfMon agent, you should install them in your environment, following the instruction from this URL:
 https://jmeter-plugins.org/wiki/PerfMonAgent/
 Otherwise, disable these listeners.
 
 ** Moreover you MUST check that the number of servers being monitored in the listeners (${__V(serverXX_${environment})})are equal to the number of servers in server.csv file. If needed, add or remove them in all listeners. Otherwise, errors could appear.
  
   
#--------------------------------------------------------------------------------#
#						COLLECTING RESULTS										 #
#--------------------------------------------------------------------------------#
Save to a file the results obtained in the following Listeners:
 - Aggregate Report
 - Summary Report Succ - Error
 - PerfMon Metrics Collector - CPU/Memory/Network/Swap (screenshots)
 