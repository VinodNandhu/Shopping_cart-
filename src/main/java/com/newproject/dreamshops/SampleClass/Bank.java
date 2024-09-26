package com.newproject.dreamshops.SampleClass;

public class Bank {
    public static void main(String[] args) {
        //        int [] marks = new int [3];
//        marks[0] = 90;
//        marks[1] = 80;
//        marks[2] = 100;
//        int total = 0;
//        int i = 0;
//        while (i < marks.length) {
//            System.out.println(i);
//            System.out.println(marks[i]);
//            total = total + marks[i];
//            i++;
//        }  System.out.println(total);
      Bank  emp1 = new Bank() ;
//      emp1.deposit (100,330984);
      int money = emp1.withdraw(90,2222);
        System.out.println("your withdraw cash is "+ money );
    }

    private int withdraw(int cash, int accountNumber) {

        return 1000;

    }


    private void deposit(int amount, int accNo) {
        System.out.println("Deposit  "+amount);
        System.out.println("A/C  "+accNo);
        //method
        //is set of instruction  for
    }
}
