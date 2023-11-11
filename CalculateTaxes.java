/*Forum Patel
* Oct 25, 2023
* CSE 7 Lab 8
* Descr: The purpose of this lab is to give you practice creating and using methods to create a cohesive program related to calculating US Income taxes. 
*/

import java.util.Scanner;

public class CalculateTaxes {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in); // ONE SCANNER ONLY.

        // Prompt the user for input
        System.out.println("Enter your wages");
        double wages = getDouble(s);
        System.out.println("Enter your interest earned");
        double interest = getDouble(s);
        System.out.println("Enter your unemployment compensation");
        double unemployComp = getDouble(s);
        System.out.println("Enter your taxes previously withheld");
        double withheld = getDouble(s);
        System.out.println("Enter your tax status (0=dependent, 1=single, and 2=married)");
        int status = getInt(s);

        double agi = computeAGI(wages, interest,unemployComp);
        System.out.printf("AGI: $%,d\n", Math.round(agi)); // NEED TO ROUND OR THE VALUE IS NOT CORRECT.

        int deduction = getDeduction(status);
        System.out.printf("Deduction: $%,d\n", deduction); 

        double taxable = getTaxable(agi, deduction);
        System.out.printf("Taxable Income: $%,d\n", Math.round(taxable));

        int taxes = calcTax(taxable, status);
        System.out.printf("Federal Taxes: $%,d\n", taxes);

        int taxOwed = calcTaxDue(taxes, withheld);
        System.out.printf("Tax Due: $%,d\n", taxOwed);

        s.close();
    }

    public static int calcTaxDue(int tax, double withheld){ 
        // Calculate tax owed by subtracting taxes previously withheld
        return (int) Math.round (tax - withheld); 
    }

    public static int calcTax(double taxableInc, int status){
        // Calculate federal taxes based on taxable income and status
        double tax = 0.0;
        if (status == 0 || status == 1){ // from the first table of taxes for Dependent or Single Filers
            if  (taxableInc <= 10000){ 
                tax = .10 * taxableInc;
            } else if (taxableInc > 10000 && taxableInc <= 40000){
                tax = 1000 + .12 * (taxableInc - 10000);
            } else if (taxableInc <= 85000){
                tax = 4600 + .22 * (taxableInc - 40000);
            } else {
                tax = 14500 + .24 * (taxableInc - 85000);
            }
        } else { // from the second table of taxes for Married Filers
            if (taxableInc <= 20000){
                tax = .10 * taxableInc;
            } else if (taxableInc <= 80000){
                tax = 2000 + .12 * (taxableInc - 20000);
            } else {
                tax = 9200 + .22 * (taxableInc - 80000);
            }
        }
        return (int) Math.round(tax);
    }

    public static double getTaxable(double agi, int deduct){
        // Calculate taxable income by subtracting deductions
        double taxable = agi - deduct;
        if (taxable < 0){
            taxable = 0; // Ensuring taxable income is non-negative
        }
        return taxable;
    }


    public static int getDeduction (int status){
        int deduction = 6000;

        // Determine the deduction amount based on taxpayer status
        switch (status) {
            case 0: // dependent
                deduction = 6000;
                break;
            case 1: // single
                deduction = 12000;
                break;
            case 2: // married
                deduction = 24000;
                break;
            default:
                deduction = 6000; // Default deduction for any other status - but there should not be as input is validated.
        }
        return deduction;
    }

    public static double computeAGI (double wages, double interest, double unemployment){
        // Calculate Adjusted Gross Income (AGI) by summing up income sources
        double totalSum = wages + interest + unemployment;
        return totalSum;
    }

    public static int getInt(Scanner keys){
        int value = -1;
        while (true) {
            if (keys.hasNextInt()) {
                value = keys.nextInt();
                if (value >= 0 && value <= 2) {
                    // Valid input within the accepted range, break out of the loop
                    break;
                } else {
                    System.out.println("Please enter a valid status (0=dependent, 1=single, 2=married).");
                }
            } else {
                System.out.println("Please enter a valid integer (0, 1, or 2).");
                keys.next(); // Consume invalid input to reprompt the user
            }
        }
        return value;
    }
    
     public static double getDouble(Scanner keys){
        double value = 0.0;
        while (true) {
            if (keys.hasNextDouble()) {
                value = keys.nextDouble();
                if (value >= 0) {
                    // Valid input, break out of the loop
                    break;
                } else {
                    System.out.println("Please enter a non-negative value.");
                }
            } else {
                System.out.println("Please enter a valid positive number.");
                keys.next(); // Consume invalid input to reprompt the user
            }
        }
        return value;
    }

}