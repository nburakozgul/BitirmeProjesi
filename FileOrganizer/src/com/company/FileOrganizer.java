package com.company;

import org.omg.CORBA.INTERNAL;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * Created by burakozgul on 27.05.2017.
 */
public class FileOrganizer {
    private ArrayList<String> dateGold;
    private ArrayList<String> dateDolar;
    private ArrayList<String> datePetrol;

    private ArrayList<String> valueGold;
    private ArrayList<String> valueDolar;
    private ArrayList<String> valueEuro;
    private ArrayList<String> valuePetrol;

    private ArrayList<Integer> dolarClass;

    public static class DateUtil
    {
        public static Date addDays(Date date, int days)
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            return cal.getTime();
        }
    }


    private void organizeDateAndValueGold(String filename) throws IOException {
        dateGold = new ArrayList<>();
        valueGold = new ArrayList<>();

        PrintWriter writer = new PrintWriter("altın2.txt", "UTF-8");

        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            String line[] = br.readLine().split("\\s");

            while (line != null) {
                if (!line[1].contains(",")) {
                    if (Integer.parseInt(line[1]) > 3000) {

                        dateGold.add(line[0]);
                        valueGold.add(line[1]);
                    }
                }

                String temp = br.readLine();
                if (temp != null)
                    line = temp.split("\\s");
                else
                    break;
            }

            Date date1 ,date2;
            DateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);


            for (int i = 0 ; i < dateGold.size()-1 ; i++) {
                date1 = format.parse(dateGold.get(i));
                date2 = format.parse(dateGold.get(i+1));

                long distance = date2.getTime() - date1.getTime();
                long diffDay = distance / (24 * 60 * 60 * 1000);

                if (diffDay != 1) {
                    for(int j = 0 ; j < diffDay -1 ; j++) {
                        dateGold.add(i+j+1,format.format(DateUtil.addDays(date1, j+1)));
                        valueGold.add(i+j+1,valueGold.get(i));
                    }
                }
            }

            valueGold.remove(0);
            dateGold.remove(0);

            for (int i = 0 ; i < dateGold.size();i++) {
                writer.println(dateGold.get(i)+" "+valueGold.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            br.close();
        }
    }

    private void organizePetrol(String filename) throws IOException {
        datePetrol = new ArrayList<>();
        valuePetrol = new ArrayList<>();

        PrintWriter writer = new PrintWriter("petrol.txt", "UTF-8");

        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            String line[] = br.readLine().split("\\s");

            while (line != null) {
                datePetrol.add(line[1]);
                valuePetrol.add(line[2]);

                String temp = br.readLine();
                if (temp != null)
                    line = temp.split("\\s");
                else
                    break;
            }

            Date date1 ,date2;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


            for (int i = 0 ; i < datePetrol.size()-1 ; i++) {
                date1 = format.parse(datePetrol.get(i));
                date2 = format.parse(datePetrol.get(i+1));

                long distance = date2.getTime() - date1.getTime();
                long diffDay = distance / (24 * 60 * 60 * 1000);

                if (diffDay != 1) {
                    for(int j = 0 ; j < diffDay -1 ; j++) {
                        datePetrol.add(i+j+1,format.format(DateUtil.addDays(date1, j+1)));
                        valuePetrol.add(i+j+1,valuePetrol.get(i));
                    }
                }
            }



            for (int i = 0 ; i < datePetrol.size();i++) {
                writer.println(datePetrol.get(i)+" "+valuePetrol.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            br.close();
        }
    }

    private void determinateDolarClass(String filename) throws IOException {
        PrintWriter writer = new PrintWriter("dolar.txt", "UTF-8");
        dateDolar = new ArrayList<>();
        valueDolar = new ArrayList<>();
        valueEuro = new ArrayList<>();

        dolarClass = new ArrayList();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        try {
            String line[] = br.readLine().split(",");

            while (line != null) {
                line[1] = line[1].subSequence(1,line[1].length()-1).toString();
                line[2] = line[2].subSequence(1,line[2].length()-1).toString();

                dateDolar.add(line[0]);
                valueDolar.add(line[1]);
                valueEuro.add(line[2]);

                String temp = br.readLine();
                if (temp != null)
                    line = temp.split(",");
                else
                    break;
            }

            Date date1 ,date2;
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);


            for (int i = 0 ; i < dateDolar.size()-1 ; i++) {
                date1 = format.parse(dateDolar.get(i));
                date2 = format.parse(dateDolar.get(i+1));

                long distance = date2.getTime() - date1.getTime();
                long diffDay = distance / (24 * 60 * 60 * 1000);

                if (diffDay != 1) {
                    for(int j = 0 ; j < diffDay -1 ; j++) {
                        dateDolar.add(i+j+1,format.format(DateUtil.addDays(date1, j+1)));
                        valueDolar.add(i+j+1,valueDolar.get(i));
                    }
                }
            }


            double dolarStep = 0.0;
            double real = 0.0;

            for (int i = 0 ; i < valueDolar.size(); i++){
                if (dolarStep != 0.0) {
                     real = Double.parseDouble(valueDolar.get(i));

                    if (real - dolarStep >= 0.0)
                        dolarClass.add(1);
                    else if (real - dolarStep < 0.0)
                        dolarClass.add(0);


                } else {
                    dolarClass.add(1);
                }

                dolarStep = Double.parseDouble(valueDolar.get(i));

            }


            for (int i = 0 ; i < dateDolar.size();i++) {
                writer.println(dateDolar.get(i)+" "+valueDolar.get(i)+" "+dolarClass.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            br.close();
        }
    }

    public void createFile(String goldfile, String petrolFile, String dovizFile) throws IOException {
        PrintWriter writer = new PrintWriter("data.txt", "UTF-8");

        organizePetrol(petrolFile);
        organizeDateAndValueGold(goldfile);
        determinateDolarClass(dovizFile);

        System.out.println(dateDolar.size()+" "+dateGold.size()+" "+datePetrol.size());

        int step = 0 ;

        for (int i = 0 ; i < dateGold.size() ; i++) {

            if (i >= 30)
                writer.print(dolarClass.get(i));

            for(int j = 30 ; j >= 1; j--) {
                /*if (i - j >= 0 && i < 30) {
                    int index2 = 2 * (i - j) + 2;
                    int index = index2 - 1 ;

                    writer.print(" "+index+":"+Double.parseDouble(valuePetrol.get(i-j))+" "+index2+":"
                            +Double.parseDouble(valueGold.get(i-j)));
                }*/

                if (i >= 30) {
                    int index2 = 2 * (30 - j) + 2;
                    int index = index2 - 1 ;

                    writer.print(" "+index+":"+Double.parseDouble(valuePetrol.get(i-j))+" "+index2+":"
                            +Double.parseDouble(valueGold.get(i-j)));
                }

                /*if (i >= 30) {
                    int index2 = 2 * (30 - j) + 2 + step;
                    int index = index2 - 1 ;

                    writer.print(" "+index+":"+Double.parseDouble(valuePetrol.get(i-j))+" "+index2+":"
                            +Double.parseDouble(valueGold.get(i-j)));
                }*/
            }

            /*writer.println(dolarClass.get(i)+" 1:"+Double.parseDouble(valuePetrol.get(i))+" 2:"
                    +Double.parseDouble(valueGold.get(i)));*/

            if (i >= 30) {
                step++;
                writer.println();
            }


        }

        writer.close();
    }
}


