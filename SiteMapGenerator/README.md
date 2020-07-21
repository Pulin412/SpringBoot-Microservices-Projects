1. 	JSoup external dependency has been used to connect to the URL's.

2. 	Running time would vary as per the website URLs size.

3.	Input the URL as - https://abc.com / https://www.abc.com

4. 	User can avoid some of the links by providing the key words in application properties file. 
	For eg., if the user wants to avoid any links related to - "blog.babylonhealth.com/..." , then just add "blog" in the properties file -
	 
	skipKeyWords=support,blog
	
5.	User can avoid unwanted extensions by giving them in application properties file. 
	For eg., if the user wants to avoid any links which points to .pdf , then just add "pdf" in the properties file -
	 
	extensions=pdf,@,:80,jpg,zip 
	
----------------------------------------------------------------------------------------------------------------------------------------

- Use "mvn spring-boot:run" to run the application via CMD.