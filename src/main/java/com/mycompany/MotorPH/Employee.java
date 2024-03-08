    package com.mycompany.MotorPH;

    public class Employee {
        private String employeeNumber;
        private String lastName;
        private String firstName;
        private String birthday;
        private String address;
        private String phoneNumber;
        private String sssNumber;
        private String philhealthNumber;
        private String tinNumber;
        private String pagIbigNumber;
        private String status;
        private String position;
        private String immediateSupervisor;
        private String basicSalary;
        private String riceSubsidy;
        private String phoneAllowance;
        private String clothingAllowance;
        private String grossSemiMonthlyRate;
        protected double hourlyRate;

        //CONSTRUCTOR
        public Employee(String[] data) {
            this.employeeNumber = getValue(data, 0);
            this.lastName = getValue(data, 1);
            this.firstName = getValue(data, 2);
            this.birthday = getValue(data, 3);
            this.address = getValue(data, 4);
            this.phoneNumber = getValue(data, 5);
            this.sssNumber = getValue(data, 6);
            this.philhealthNumber = getValue(data, 7);
            this.tinNumber = getValue(data, 8);
            this.pagIbigNumber = getValue(data, 9);
            this.status = getValue(data, 10);
            this.position = getValue(data, 11);
            this.immediateSupervisor = getValue(data, 12);
            this.basicSalary = getValue(data, 13);
            this.riceSubsidy = getValue(data, 14);
            this.phoneAllowance = getValue(data, 15);
            this.clothingAllowance = getValue(data, 16);
            this.grossSemiMonthlyRate = getValue(data, 17);
            this.hourlyRate = getDoubleValue(data, 18);
        }

        public Employee(){

        }
        

        //OVERRIDES DEFAULT TOSTRING TO PRINT EMPLOYEE DATA WITH SPACING
        @Override
        public String toString() {
            String hourlyRateString = (Double.isNaN(hourlyRate)) ? "Hourly Rate" : Double.toString(hourlyRate);
            return String.format("%-15s%-15s%-15s%-15s%-80s%-15s%-15s%-15s%-20s%-15s%-15s%-35s%-25s%-15s%-15s%-20s%-20s%-25s%-15s",
                    employeeNumber, lastName, firstName, birthday, address, phoneNumber, sssNumber, philhealthNumber, tinNumber,
                    pagIbigNumber, status, position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance,
                    grossSemiMonthlyRate, hourlyRateString);
        }   

        //OVERLOADS TOSTRING TO PRINT IN ANOTHER FORMAT
        public String toString(boolean targetEmpTrue) {
        return """
                Employee ID: %s
                Name: %s %s
                Birthday: %s
                Address: %s
                PhoneNumber: %s
                SSSNumber: %s
                PhilHealth: %s
                TIN: %s
                PAG-IBIG: %s
                Status: %s
                Position: %s
                Supervisor: %s
                Basic Salary: %s
                Rice Subsidy: %s
                Phone allowance: %s
                Clothing Allowance: %s
                Gross Semi Monthly Rate: %s
                Hourly Rate: %s
                """.formatted(
                    employeeNumber,
                    lastName,
                    firstName,
                    birthday,
                    address,
                    phoneNumber,
                    sssNumber,
                    philhealthNumber,
                    tinNumber,
                    pagIbigNumber,
                    status,
                    position,
                    immediateSupervisor,
                    basicSalary,
                    riceSubsidy,
                    phoneAllowance,
                    clothingAllowance,
                    grossSemiMonthlyRate,
                    hourlyRate
                );
    }

        private double getDoubleValue(String[] data, int index) {
    String value = getValue(data, index);

    // Check if the value is the header
    if (value.equalsIgnoreCase("Hourly Rate")) {
        return Double.NaN;
    }

    try {
        return Double.parseDouble(value);
    } catch (NumberFormatException e) {
        System.err.println("Error parsing double value at index " + index + " for Employee ID " + getEmployeeNumber() + ": " + value);
        return 0.0; // or another default value
    }
}       

        //GETTERS & SETTERS HERE ONWARDS
        private String getValue(String[] data, int index) {
            return (data.length > index) ? data[index] : "";
        }  

        /**
         * @return the employeeNumber
         */
        public String getEmployeeNumber() {
            return employeeNumber;
        }

        /**
         * @return the lastName
         */
        public String getLastName() {
            return lastName;
        }

        /**
         * @return the firstName
         */
        public String getFirstName() {
            return firstName;
        }

        /**
         * @return the birthday
         */
        public String getBirthday() {
            return birthday;
        }

        /**
         * @return the address
         */
        public String getAddress() {
            return address;
        }

        /**
         * @return the phoneNumber
         */
        public String getPhoneNumber() {
            return phoneNumber;
        }

        /**
         * @return the sssNumber
         */
        public String getSssNumber() {
            return sssNumber;
        }

        /**
         * @return the philhealthNumber
         */
        public String getPhilhealthNumber() {
            return philhealthNumber;
        }

        /**
         * @return the tinNumber
         */
        public String getTinNumber() {
            return tinNumber;
        }

        /**
         * @return the pagIbigNumber
         */
        public String getPagIbigNumber() {
            return pagIbigNumber;
        }

        /**
         * @return the status
         */
        public String getStatus() {
            return status;
        }

        /**
         * @return the position
         */
        public String getPosition() {
            return position;
        }

        /**
         * @return the immediateSupervisor
         */
        public String getImmediateSupervisor() {
            return immediateSupervisor;
        }

        /**
         * @return the basicSalary
         */
        public String getBasicSalary() {
            return basicSalary;
        }

        /**
         * @return the riceSubsidy
         */
        public String getRiceSubsidy() {
            return riceSubsidy;
        }

        /**
         * @return the phoneAllowance
         */
        public String getPhoneAllowance() {
            return phoneAllowance;
        }

        /**
         * @return the clothingAllowance
         */
        public String getClothingAllowance() {
            return clothingAllowance;
        }

        /**
         * @return the grossSemiMonthlyRate
         */
        public String getGrossSemiMonthlyRate() {
            return grossSemiMonthlyRate;
        }

        /**
         * @return the hourlyRate
         */

        public double getHourlyRate() {
            return hourlyRate;
        }

        /**
         * @param lastName the lastName to set
         */
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        /**
         * @param firstName the firstName to set
         */
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        /**
         * @param hourlyRate the hourlyRate to set
         */
        public void setHourlyRate(double hourlyRate) {
            this.hourlyRate = hourlyRate;
        }



    }
