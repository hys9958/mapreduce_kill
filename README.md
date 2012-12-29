mapreduce_kill
==============
### Note : MapReduce Job killing Manager. Hadoop web tool Integration. ###

## Screen shot ##
![Settings Window](https://raw.github.com/hys9958/mapreduce_kill/master/screen_shot.png)

## Compiling ##
- compiling requires : 
	- java JDK 1.6,
	- maven
- dependency
	- hadoop-core-x.jar,
	
## Install ##
1 hadoop-killjob-1.0.0.jar moves to $HADOOP_HOME/lib.

2 under items append to $HADOOP_HOME/webapps/job/WEB-INF/web.xml

    <servlet>
	    <servlet-name>org.apache.hadoop.mapred.killjobtracker_jsp</servlet-name>
	    <servlet-class>org.apache.hadoop.mapred.killjobtracker_jsp</servlet-class>
    </servlet>
    <servlet-mapping>
	    <servlet-name>org.apache.hadoop.mapred.killjobtracker_jsp</servlet-name>
	    <url-pattern>/killjob</url-pattern>
    </servlet-mapping>
    	
3 restart mapred.

## Usage ##
- http://hadoop:50030/kiljob

hanyounsu@gmail.com
