LOAD FILE CONFIG:

Change the file path declared in AttendanceRecord.Java, NetwageCalculation.Java and EmployeeModelFromFile.java to the proper directory where the cloned project is found. Also change the resource folder path in pom.xml.

txt file path instruction:

To see the path refer to the properties option of the txt file to be used that can be found in the resources folder once cloned/manually extracted.
refer to the formatting used in the declaration of the file path in each java classes. Uses double forward slash instead of one. Include the name of txtfile with file extension.

Sample Path: D:\\Documents\\MotorPH\\src\\main\\resources\\AttendanceRecord.txt

pom.xml instruction:

Change the path of the resource folder in pom.xml, line 29, to the current location. Open the project folder then src > main > then right click resource folder, copy location path then replace the one in the pom.xml

Sample path: D:\Documents\MotorPH\src\main\resources
