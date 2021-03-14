To launch the application double click on run.bat or target/songs-1.0-SNAPSHOT.jar files.
This application provides grafic user interface (Swing library) 
to read/write songs data(Title, Author, Album, Category, Votes) form/to .xml or .csv files;
Supported categories (Rock, Pop, Electronic, Country, Reggae, Polka, Hip hop, Classic).
How to use---------------------------------------------------------------------------------
After first execution the embedded H2 database file (songs.mv.db) will be generated,
where will be stored all songs values.
The aplication interface provides next possibilites:
----------------------------------------------------------------------------------------------------
1. ADD SONGS.
After clicking button add you can choose multiple xml and csv files to save songs.
IF you select 3 files (let say xml csv png) you will have an error message, 
but those xml and csv files will be read.
If there are same songs (means songs with same title, author and album) in read files votes are added up.
If song don't have at least one atribute, it woun't be save to database and you will see
error message and description of that song.
All song are shown on the rigt panel.
---------------------------------------------------------------------------------------------------
2. TOP 3, TOP 10
Show top 3/top 10 snogs, according to the votes among all saved songs.
-------------------------------------------------------------------------------------------------
3. Categories schoose.
Show all song with choosen category among saved songs.
-------------------------------------------------------------------------------------------------
4. Add vote.
You can choose interval of shown songs by Ctrl+RMB or select all shown songs by Ctrl+A and click on 
"Add song" button. 1 vote will be added to each selected songs.
---------------------------------------------------------------------------------------------------
5. Report.
Choose necesarry songs and click "Report" button so see raport of selected songs. You can see selected 
songs in xml or csv format by clicking on tabs. By clicking on "Save button" those songs will be saved
appropriate format in file, that you choose or new file. The value of file will be overriden.
------------------------------------------------------------------------------------------------------
6. Clear all votes.
By clicking on "Clear all votes" button you will nullify votes of all saved (not shown) songs.
------------------------------------------------------------------------------------------------------
Stack of technologies:
- Java 11
- H2 database with Hibernate
- Maven - for build
- Swing - for GUI
- Jaxb, opencsv and commons-io - for xml and csv parsing
---------------------------------------------------------------------------------------------------
Created by Nazarii Ihnat



