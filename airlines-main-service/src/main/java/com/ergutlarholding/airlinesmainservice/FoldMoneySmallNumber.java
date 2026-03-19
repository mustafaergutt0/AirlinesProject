package com.ergutlarholding.airlinesmainservice;

import java.util.Scanner;
import java.util.Locale;


// You are trader okey you have money in first but you dont know compound income
// this is basic machine that calculate your money hwo much time it reacht where
// and it gives ratio about your Lastmoney to inital money depends your how much trade it


public class FoldMoneySmallNumber {
    public static void main(String[] args) {
        ShowMyMoney();
    }

    static void ShowMyMoney() {
        Locale trLocale = new Locale("tr", "TR");
        Scanner sc = new Scanner(System.in).useLocale(trLocale);

        System.out.println("ERGUTLAR CASH CENTER");
        System.out.print("ENTER INITIAL AMOUNT: ");
        double currentMoney = sc.nextDouble(); // Ana paramız bu
        double Inıtalmoney=currentMoney;
        int count = 0;
        while (true) {
            System.out.println("\n-------------------------------------------");
            System.out.printf(trLocale, "CURRENT MONEY: %,.2f\n", currentMoney);
            System.out.print("PLEASE ENTER LOSS/GAIN % (e.g. -45 or 45): ");

            double percentage = sc.nextDouble();

            // Yeni parayı hesapla ve ana parayı güncelle

            double oldMoney = currentMoney;
            currentMoney = currentMoney + (percentage * (currentMoney / 100));
            count++;

            // Kaç katı olduğunu hesapla
            double ratio = currentMoney / Inıtalmoney;

            // PRINTF DÜZELTME:
            // %,.2f -> Para formatı
            // %d -> Tam sayı (count)
            // %.2f -> Kat oranı
            System.out.printf(trLocale,
                    "NEW BALANCE: %,.2f | Step: %d | Multiplier: %.2fx\n",
                    currentMoney, count, ratio);


        }
    }
}