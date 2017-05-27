package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        FileOrganizer fileOrganizer = new FileOrganizer();
        //fileOrganizer.organizeDateAndValueGold("altın kuru.txt");
        //fileOrganizer.determinateDolarClass("dolar kuru.txt");
        //fileOrganizer.organizePetrol("pompa fiyatı.txt");
        fileOrganizer.createFile("altın kuru.txt","pompa fiyatı.txt","dolar kuru.txt");
    }


}
