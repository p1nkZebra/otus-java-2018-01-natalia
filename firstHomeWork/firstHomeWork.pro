#java -jar proguard\proguard-base-6.0.jar @firstHomeWork.pro

-injars       target/firstHomeWork.jar
-outjars      target/firstHomeWork-out.jar

-printmapping pgmapout.map
-dontwarn

-keep public class ru.otus.homeWork.Main {public static void main(java.lang.String[]);}