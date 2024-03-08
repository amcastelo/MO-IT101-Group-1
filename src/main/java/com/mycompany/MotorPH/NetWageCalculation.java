    package com.mycompany.MotorPH;

    import static com.mycompany.MotorPH.MotorPHMain.attendanceRecords;
    import com.opencsv.CSVReader;
    import com.opencsv.CSVReaderBuilder;
    import com.opencsv.exceptions.CsvException;
    import java.io.FileReader;
    import java.io.IOException;
    import java.text.DecimalFormat;
    import java.util.ArrayList;
    import java.util.Calendar;
    import java.util.Date;
    import java.util.List;

    public class NetWageCalculation {  

        private String compensationRange;
        private double contribution;

        private static final String TXT_FILE_PATH = "D:\\Documents\\MotorPH\\src\\main\\resources\\SSSCont.txt";       
        private static final List<NetWageCalculation> deductionRecords;

        private static double sssDed, pagibigDed, philDed, tax, taxable;
        private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

        //CONSTRUCTOR
        public NetWageCalculation(String compensationRange, double contribution) {
            this.compensationRange = compensationRange;
            this.contribution = contribution;
        }

        //INITIALIZE
        static{
            deductionRecords = loadDeductions();
        }

        //LOADS THE SSS CONTRIBUTION FILE AND SAVES IT AS NEW OBJECT IN OBJECT ARRAY LIST
        private static List<NetWageCalculation> loadDeductions() {
        List<NetWageCalculation> deductionRecord = new ArrayList<>();

        try (CSVReader csvReader = new CSVReaderBuilder(new FileReader(TXT_FILE_PATH)).withSkipLines(1).build()) {
            List<String[]> records = csvReader.readAll();
            deductionRecord = parseDeductionRecords(records);
        } catch (IOException | CsvException e) {
            handleException(e);
        }

        return deductionRecord;
        }

        //PARSES THE SSS CONTRIBUTION RANGE FILE TO BE USED IN LOADING
        private static List<NetWageCalculation> parseDeductionRecords(List<String[]> records) {
            List<NetWageCalculation> deductionRecordss = new ArrayList<>();

            for (String[] record : records) {
                String compensationRange = record[0];
                double contribution = parseDoubleWithCommas(record[1]);

                NetWageCalculation deductionRecord = new NetWageCalculation(compensationRange, contribution);
                deductionRecordss.add(deductionRecord);
            }

            return deductionRecordss;
        }

        //PARSES DOUBLE/NUMBERS WITH COMMA FOR FORMATTING COMPATIBILITY
        private static double parseDoubleWithCommas(String input) {
            String cleanedInput = input.replaceAll(",", "");
            return Double.parseDouble(cleanedInput);
        }

        //PARSES SSS CONTRIBUTION RANGE .CSV FILE TO USE IN SSS CALCULATION
        private static double[] parseCompensationRange(String compensationRange) {           
            String[] rangeParts = compensationRange.split("-");

            if (rangeParts.length != 2) {
                throw new IllegalArgumentException("Invalid compensation range format: " + compensationRange);
            }

            double start = Double.parseDouble(rangeParts[0].trim().replaceAll("[^\\d.]", ""));
            double end = Double.parseDouble(rangeParts[1].trim().replaceAll("[^\\d.]", ""));

            return new double[]{start, end};
        }

        //PRINTS SSS CONTRIBUTION RANGE
        public static void printSSSDeductionRecords() {
            for (NetWageCalculation record : deductionRecords) {
                System.out.println("Compensation Range: " + record.getCompensationRange());
                System.out.println("Contribution: " + record.getContribution());
                System.out.println("------------");
            }
        }

        //CALCULATES SSS CONTRIBUTION
        private static void calculateSSSDeduction() {
            double gross = EmployeeSalaryGross.getGross();
            for (NetWageCalculation record : deductionRecords) {
                double[] range = parseCompensationRange(record.getCompensationRange());
                if (gross > range[0] && gross <= range[1]) {

                    sssDed = record.getContribution();
                    break;  // Assuming that only one range should match, you can modify as needed
                }
            }
        }

        //CALCULATES PHILHEALTH CONTRIBUTION
        private static void calculatePhilDeduction(){
            double gross = EmployeeSalaryGross.getGross();

            double PhilDed = (gross * .03)/2;

            String formattedPhilDed = decimalFormat.format(PhilDed);

            philDed = PhilDed;
        }   

        //CALCULATES PAGIBIG CONTRIBUTION
        private static void calculatePagibig() {
        double gross = EmployeeSalaryGross.getGross();
        double pagibig;

        if (gross > 1500.00) {
            pagibig = gross * 0.03;
        } else {
            pagibig = gross * 0.04;     
        }

        if (pagibig > 100) {
                pagibig = 100;
            }
        pagibigDed = pagibig;
        }

        //CALCULATES WITHHOLDING TAX
        private static void calculateTax(double totalDed) {
        taxable = EmployeeSalaryGross.getGross() - totalDed;

        if (taxable <= 20832) {
            tax = 0;
            //System.out.println("1");
        } else if (taxable > 20832 && taxable <= 33333) {
            tax = (taxable - 20832) * 0.20;
            //System.out.println("2");
        } else if (taxable > 33333 && taxable <= 66667) {
            tax = 2500 + (taxable - 33333) * 0.25;
            //System.out.println("3");
        } else if (taxable > 66667 && taxable <= 166667) {
            tax = 10833 + (taxable - 66667) * 0.30;
            //System.out.println("4");
        } else if (taxable > 166667 && taxable <= 666667) {
            tax = 40833.33 + (taxable - 166667) * 0.32;
            //System.out.println("5");
        } else {
            tax = 200833.33 + (taxable - 666667) * 0.35;
            //System.out.println("6");
        }
        }
        
    public static double deductLatePenalty(String targetEmployeeId, int targetMonth) {
    double totalLateDeduction = 0;

    for (AttendanceRecord attendanceRecord : attendanceRecords) {
        // Check if the record is for the target employee
        if (attendanceRecord.getId().equals(targetEmployeeId)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(attendanceRecord.getDate());
            int recordMonth = calendar.get(Calendar.MONTH) + 1; // Adding 1 because Calendar.MONTH is zero-based

            // Check if the record is in the target month
            if (recordMonth == targetMonth) {
                // Assuming late penalty starts from 8:10 AM (490 minutes) onwards
                final int lateThreshold = 490;

                Date timeIn = attendanceRecord.getTimeIn();
                calendar.setTime(timeIn);
                int lateTime = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);

                if (lateTime >= lateThreshold) {
                    // Calculate the per-minute equivalent of the hourly rate
                    double hourlyRate = EmployeeSalaryGross.getHourly();
                    double perMinuteRate = hourlyRate / 60.0;

                    // Calculate the deduction amount based on late time
                    double deduction = perMinuteRate * (lateTime - lateThreshold);

                    // Ensure deduction is non-negative
                    totalLateDeduction += Math.max(0, deduction);

                    // Print statements for debugging
                    //System.out.println("Employee ID: " + attendanceRecord.getId());
                   // System.out.println("Late Time: " + lateTime);
                    //System.out.println("Hourly Rate: " + hourlyRate);
                    //System.out.println("Per Minute Rate: " + perMinuteRate);
                    //System.out.println("Deduction: " + deduction);
                   // System.out.println("Total Late Deduction: " + totalLateDeduction);
                }
                // You can add more logic here if needed, such as printing details for each record
            }
        }
    }

    return totalLateDeduction;
}

        
        //CALCULATE OVERALL NET WAGE
        public static void calculateNetWage(String targetEmployeeId, int month) {
            double net, totalDed;
            EmployeeSalaryGross.calculateGross(targetEmployeeId, month);

            calculateSSSDeduction();
            calculatePhilDeduction();
            calculatePagibig();

            // Assuming you are using the correct method for late deductions based on your requirements
            double l1 = NetWageCalculation.deductLatePenalty(targetEmployeeId, month);

            totalDed = (sssDed + philDed + pagibigDed + l1);
            calculateTax(totalDed);
            net = taxable - tax;

            // Assuming l1 and l2 are double variables
            System.out.println("""
            ------------------------------------------
            Employee ID: %s
            Employee Name: %s
            ------------------------------------------
            Total Hours: %s               
            Gross Wage: $%s

            SSS Deduction: $%s
            Philhealth Deduction: $%s
            Pag-Ibig Deduction: $%s                       
            Late Deductions : $%.2f

            Total Deductions: $%s                                  

            Taxable Income: $%s

            Withholding Tax: $%s

            Net Wage: $%s
            ------------------------------------------
            """.formatted(
                targetEmployeeId,
                EmployeeSalaryGross.getEmpName(),
                EmployeeSalaryGross.getHours(),
                decimalFormat.format(EmployeeSalaryGross.getGross()),
                decimalFormat.format(sssDed),
                decimalFormat.format(philDed),
                decimalFormat.format(pagibigDed),
                l1,
                decimalFormat.format(totalDed),       
                decimalFormat.format(taxable),
                decimalFormat.format(tax),
                decimalFormat.format(net)
            ));
        }   

        //PRINTS STACK TRACE FOR HANDLEEXCEPTIONS
        private static void handleException(Exception e) {
            e.printStackTrace();    
        }

        //GETTER & SETTER HERE ONWARDS
        public String getCompensationRange() {
            return compensationRange;
        }

        public double getContribution() {
            return contribution;
        }
    }
