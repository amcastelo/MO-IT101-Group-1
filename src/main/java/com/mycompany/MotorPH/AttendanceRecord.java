package com.mycompany.MotorPH;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class AttendanceRecord {
    private String name;
    private String id;
    private Date date;
    private Date timeIn;
    private Date timeOut;
    private static String csvFile = "D:\\Documents\\MotorPH\\src\\main\\resources\\AttendanceRecord.txt";
    public static ArrayList<AttendanceRecord> attendanceRecords;

    public AttendanceRecord(String name, String id, Date date, Date timeIn, Date timeOut) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    static {
        attendanceRecords = loadAttendance();
    }

    public static ArrayList<AttendanceRecord> loadAttendance() {
        ArrayList<AttendanceRecord> attendanceRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getCsvFile()))) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            // Read and skip the header
            br.readLine();

            // Process attendance records
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                // Debug prints
                //System.out.println("Line: " + line);
                //System.out.println("Data length: " + data.length);

                if (data.length >= 6) {
                    String name = data[1] + " " + data[2].trim();
                    String id = data[0];
                    Date date = dateFormat.parse(data[3]);
                    Date timeIn = timeFormat.parse(data[4]);
                    Date timeOut = timeFormat.parse(data[5]);

                    attendanceRecords.add(new AttendanceRecord(name, id, date, timeIn, timeOut));
                } else {
                    System.out.println("Invalid data: " + line);
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return attendanceRecords;
    }
    
    //FOR DEBUGGING
    private static void attendanceCalculator() {
        for (AttendanceRecord entry : attendanceRecords) {
            long hoursWorked = calculateHoursWorked(entry.getTimeIn(), entry.getTimeOut());
            System.out.println("Name: " + entry.getName() + ", ID: " + entry.getId() +
                    ", Date: " + entry.getDate() + ", Hours worked: " + hoursWorked + " hours");
        }
    }

    
    private static long calculateHoursWorked(Date timeIn, Date timeOut) {
        long diffInMilliseconds = timeOut.getTime() - timeIn.getTime();
        return diffInMilliseconds / (60 * 60 * 1000);
    }

    //CALCULATES HOURS WORKED ON A SPECIFIC MONTH OF AN EMPLOYEE
    public static long calculateTotalHoursAndPrint(int year, int month, String targetEmployeeId) {
        long totalHours = 0;
        String employeeName = "";

        Calendar cal = Calendar.getInstance();
        for (AttendanceRecord entry : attendanceRecords) {
            if (entry.getId().equals(targetEmployeeId)) {
                cal.setTime(entry.getDate());
                int entryYear = cal.get(Calendar.YEAR);
                int entryMonth = cal.get(Calendar.MONTH) + 1;

                // Debug prints
                //System.out.println("Entry ID: " + entry.getId());
                //System.out.println("Entry Date: " + entry.getDate());
                //System.out.println("Entry Date: " + entry.getTimeIn());
                //System.out.println("Entry Date: " + entry.getTimeOut());
                //System.out.println("Entry Name: " + entry.getName());

                if (entryYear == year && entryMonth == month) {
                long hoursWorked = calculateHoursWorked(entry.getTimeIn(), entry.getTimeOut());
                //System.out.println("Hours worked in " + entryMonth + "/" + entryYear + ": " + hoursWorked + " hours" + "Date: " +entry.getDate());
                
                totalHours += hoursWorked;
                employeeName = entry.getName();
            } else {
                //System.out.println("No records found for employee ID: " + targetEmployeeId);
            }
        }
    }

        if (totalHours > 0) {
            /*System.out.println("""
                    Employee ID: %s
                    Name: %s
                    Total Hours: %s
                    """.formatted(targetEmployeeId, employeeName, totalHours));*/
        } else {
            totalHours = 160;
           /* System.out.println("""
                    Employee ID: %s
                    Name: %s
                    Total Hours: %s
                    """.formatted(targetEmployeeId, employeeName, totalHours));*/
        }
        return totalHours;
    }
    
    //PRINTS ALL HOURS WORKED PER MONTH OF AN EMPLOYEE (MAINLY USED TO DEBUG BUT CAN BE ADDED AS FUNCTION)
    public static void calculateTotalHoursAndPrint(String targetEmployeeId) {
    Map<String, Long> monthlyTotalHours = new LinkedHashMap<>(); // Using LinkedHashMap to maintain insertion order

    for (AttendanceRecord entry : attendanceRecords) {
        if (entry.getId().equals(targetEmployeeId)) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(entry.getDate());
            int entryYear = cal.get(Calendar.YEAR);
            int entryMonth = cal.get(Calendar.MONTH) + 1;

            // Generate a unique key for each month
            String monthKey = entryMonth + "/" + entryYear;

            // Calculate hours worked
            long hoursWorked = calculateHoursWorked(entry.getTimeIn(), entry.getTimeOut());

            // Update monthly total
            monthlyTotalHours.put(monthKey, monthlyTotalHours.getOrDefault(monthKey, 0L) + hoursWorked);
        }
    }

    // Print monthly totals
    for (Map.Entry<String, Long> entry : monthlyTotalHours.entrySet()) {
        System.out.println("Employee ID: " + targetEmployeeId +
                ", Month: " + entry.getKey() +
                ", Total Hours: " + entry.getValue() + " hours");
    }
}
    

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @return the timeIn
     */
    public Date getTimeIn() {
        return timeIn;
    }

    /**
     * @return the timeOut
     */
    public Date getTimeOut() {
        return timeOut;
    }

    /**
     * @return the csvFile
     */
    public static String getCsvFile() {
        return csvFile;
    }

    /**
     * @return the attendanceRecords
     */
    public static ArrayList<AttendanceRecord> getAttendanceRecords() {
        return attendanceRecords;
    }


}
